package cn.sysu.sse.recruitment.job_platform_api.service;

import cn.sysu.sse.recruitment.job_platform_api.domain.User;
import cn.sysu.sse.recruitment.job_platform_api.domain.enums.UserRole;
import cn.sysu.sse.recruitment.job_platform_api.domain.enums.UserStatus;
import cn.sysu.sse.recruitment.job_platform_api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public SimpleResponse register(String email, String password, String verificationCode, String roleStr) {
        // 验证码校验（占位实现：验证码为123456）
        if (!"123456".equals(verificationCode)) {
            return SimpleResponse.of(400, "验证码错误");
        }

        roleStr = roleStr == null ? "" : roleStr.trim().toLowerCase();
        if (!"student".equals(roleStr) && !"hr".equals(roleStr)) {
            return SimpleResponse.of(400, "参数错误：role 仅支持 student/hr");
        }
        UserRole role = "hr".equals(roleStr) ? UserRole.HR : UserRole.STUDENT;

        // 对于企业用户(hr)，需要验证邮箱格式
        if (role == UserRole.HR) {
            if (!isValidEmail(email)) {
                return SimpleResponse.of(400, "邮箱格式不正确");
            }
        }
        // 学生邮箱自动拼接后缀（若未包含 @）
        String processedEmail = email.trim();
        if (role == UserRole.STUDENT && !processedEmail.contains("@")) {
            processedEmail = processedEmail + "@mail2.sysu.edu.cn";
        }

        // 邮箱唯一性
        Optional<User> existing = userRepository.findByEmail(processedEmail);
        if (existing.isPresent()) {
            return SimpleResponse.of(400, "邮箱已被注册");
        }

        User user = new User();
        user.setEmail(processedEmail);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole(role);
        user.setStatus(role == UserRole.STUDENT ? UserStatus.ACTIVE : UserStatus.PENDING);
        userRepository.insert(user);

        if (role == UserRole.STUDENT) {
            return SimpleResponse.of(201, "学生账户注册成功");
        } else {
            return SimpleResponse.of(202, "企业账户注册成功，请等待管理员审核");
        }
    }

    /**
     * 简单的邮箱格式验证
     * @param email 邮箱地址
     * @return 是否为有效的邮箱格式
     */
    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    public static class SimpleResponse {
        private Integer code;
        private String message;

        public static SimpleResponse of(Integer code, String message) {
            SimpleResponse r = new SimpleResponse();
            r.code = code;
            r.message = message;
            return r;
        }

        // Getters and setters
        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}