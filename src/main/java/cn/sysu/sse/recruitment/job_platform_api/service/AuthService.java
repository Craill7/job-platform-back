package cn.sysu.sse.recruitment.job_platform_api.service;

import cn.sysu.sse.recruitment.job_platform_api.domain.entity.User;
import cn.sysu.sse.recruitment.job_platform_api.domain.enums.UserRole;
import cn.sysu.sse.recruitment.job_platform_api.domain.enums.UserStatus;
import cn.sysu.sse.recruitment.job_platform_api.domain.response.LoginResponse;
import cn.sysu.sse.recruitment.job_platform_api.domain.response.ResetPasswordResponse;
import cn.sysu.sse.recruitment.job_platform_api.repository.UserRepository;
import cn.sysu.sse.recruitment.job_platform_api.security.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 认证服务
 */
@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenService jwtTokenService;

    /**
     * 用户登录
     * @param email 用户邮箱
     * @param password 用户密码
     * @return 登录结果，包含JWT token和用户信息
     */
    public LoginResponse login(String email, String password) {
        logger.info("开始处理登录请求，邮箱：{}", email);
        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isEmpty()) {
            logger.warn("登录失败，邮箱未注册：{}", email);
            return LoginResponse.fail("邮箱未注册");
        }

        User user = opt.get();
        if (user.getStatus() == UserStatus.PENDING) {
            logger.warn("登录失败，账号审核中：{}", email);
            return LoginResponse.fail("账号审核中");
        }
        if (user.getStatus() == UserStatus.DISABLED) {
            logger.warn("登录失败，账号已被禁用：{}", email);
            return LoginResponse.fail("账号已被禁用");
        }

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            logger.warn("登录失败，密码错误：{}", email);
            return LoginResponse.fail("邮箱或密码错误");
        }

        user.setLastLoginAt(LocalDateTime.now());
        userRepository.update(user);

        // 生成 JWT，subject=email，附加 id 与 role claim
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("role", roleToString(user.getRole()));
        String token = jwtTokenService.generateToken(user.getEmail(), claims);

        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo();
        userInfo.setId(user.getId());
        userInfo.setEmail(user.getEmail());
        userInfo.setRole(roleToString(user.getRole()));
        userInfo.setStatus(statusToString(user.getStatus()));

        logger.info("登录成功，邮箱：{}，用户ID：{}", email, user.getId());
        return LoginResponse.success("登录成功", token, userInfo);
    }

    /**
     * 将角色枚举转换为字符串
     * @param role 用户角色枚举
     * @return 角色字符串
     */
    private String roleToString(UserRole role) {
        return role == UserRole.HR ? "hr" : "student";
    }

    /**
     * 将状态枚举转换为字符串
     * @param status 用户状态枚举
     * @return 状态字符串
     */
    private String statusToString(UserStatus status) {
        return switch (status) {
            case ACTIVE -> "active";
            case PENDING -> "pending";
            case DISABLED -> "disabled";
        };
    }

    /**
     * 找回密码
     * @param email 用户邮箱
     * @param newPassword 新密码
     * @param verificationCode 验证码
     * @return 重置结果
     */
    public ResetPasswordResponse resetPassword(String email, String newPassword, String verificationCode) {
        logger.info("开始处理密码重置请求，邮箱：{}", email);
        // TODO: TASK013 - 对接验证码校验服务
        // 暂时占位校验逻辑，验证码服务实现后需要替换
        if (verificationCode == null || verificationCode.trim().isEmpty()) {
            logger.warn("密码重置失败，验证码为空：{}", email);
            return ResetPasswordResponse.fail(400, "验证码不能为空");
        }
        
        // 查找用户
        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isEmpty()) {
            logger.warn("密码重置失败，邮箱不存在：{}", email);
            return ResetPasswordResponse.fail(404, "邮箱不存在");
        }

        // TODO: TASK013 - 实际验证码校验逻辑
        // 当前占位：简单检查验证码长度
        if (verificationCode.length() != 6) {
            logger.warn("密码重置失败，验证码错误：{}", email);
            return ResetPasswordResponse.fail(400, "验证码错误");
        }

        User user = opt.get();
        // 加密新密码并更新
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.update(user);

        logger.info("密码重置成功，邮箱：{}", email);
        return ResetPasswordResponse.success("密码重置成功");
    }

}