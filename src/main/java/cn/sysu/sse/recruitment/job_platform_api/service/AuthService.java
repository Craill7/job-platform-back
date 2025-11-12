package cn.sysu.sse.recruitment.job_platform_api.service;

import cn.sysu.sse.recruitment.job_platform_api.domain.User;
import cn.sysu.sse.recruitment.job_platform_api.domain.enums.UserRole;
import cn.sysu.sse.recruitment.job_platform_api.domain.enums.UserStatus;
import cn.sysu.sse.recruitment.job_platform_api.repository.UserRepository;
import cn.sysu.sse.recruitment.job_platform_api.security.JwtTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenService jwtTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }

    public LoginResult login(String email, String password) {
        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isEmpty()) {
            return LoginResult.fail("邮箱未注册");
        }

        User user = opt.get();
        if (user.getStatus() == UserStatus.DISABLED) {
            return LoginResult.fail("账号已被禁用");
        }

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            return LoginResult.fail("邮箱或密码错误");
        }

        user.setLastLoginAt(LocalDateTime.now());
        userRepository.update(user);

        // 生成 JWT，subject=email，附加 id 与 role claim
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("role", roleToString(user.getRole()));
        String token = jwtTokenService.generateToken(user.getEmail(), claims);

        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setEmail(user.getEmail());
        userInfo.setRole(roleToString(user.getRole()));
        userInfo.setStatus(statusToString(user.getStatus()));

        return LoginResult.success("登录成功", token, userInfo);
    }

    private String roleToString(UserRole role) {
        return role == UserRole.HR ? "hr" : "student";
    }

    private String statusToString(UserStatus status) {
        return switch (status) {
            case ACTIVE -> "active";
            case PENDING -> "pending";
            case DISABLED -> "disabled";
        };
    }

    /**
     * 重置密码
     * @param email 用户邮箱
     * @param newPassword 新密码
     * @param verificationCode 验证码
     * @return 重置结果
     */
    public ResetPasswordResult resetPassword(String email, String newPassword, String verificationCode) {
        // TODO: TASK013 - 对接验证码校验服务
        // 暂时占位校验逻辑，验证码服务实现后需要替换
        if (verificationCode == null || verificationCode.trim().isEmpty()) {
            return ResetPasswordResult.fail(400, "验证码不能为空");
        }
        
        // 查找用户
        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isEmpty()) {
            return ResetPasswordResult.fail(404, "邮箱不存在");
        }

        // TODO: TASK013 - 实际验证码校验逻辑
        // 当前占位：简单检查验证码长度
        if (verificationCode.length() != 6) {
            return ResetPasswordResult.fail(400, "验证码错误");
        }

        User user = opt.get();
        // 加密新密码并更新
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.update(user);

        return ResetPasswordResult.success("密码重置成功");
    }

    public static class UserInfo {
        private Integer id;
        private String email;
        private String role;
        private String status;

        // Getters and setters
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class LoginResult {
        private Integer code;
        private String message;
        private String token;
        private UserInfo userInfo;

        private LoginResult() {}

        public static LoginResult success(String message, String token, UserInfo userInfo) {
            LoginResult result = new LoginResult();
            result.code = 200;
            result.message = message;
            result.token = token;
            result.userInfo = userInfo;
            return result;
        }

        public static LoginResult fail(String message) {
            LoginResult result = new LoginResult();
            result.code = 401;
            result.message = message;
            return result;
        }

        // Getters
        public Integer getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public String getToken() {
            return token;
        }

        public UserInfo getUserInfo() {
            return userInfo;
        }
    }

    public static class ResetPasswordResult {
        private Integer code;
        private String message;

        private ResetPasswordResult() {}

        public static ResetPasswordResult success(String message) {
            ResetPasswordResult result = new ResetPasswordResult();
            result.code = 200;
            result.message = message;
            return result;
        }

        public static ResetPasswordResult fail(int code, String message) {
            ResetPasswordResult result = new ResetPasswordResult();
            result.code = code;
            result.message = message;
            return result;
        }

        // Getters
        public Integer getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}