package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.server.service.AuthService;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.User;
import cn.sysu.sse.recruitment.job_platform_api.common.enums.UserRole;
import cn.sysu.sse.recruitment.job_platform_api.common.enums.UserStatus;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.LoginVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.ResetPasswordVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.ChangePasswordVO;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.UserMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.security.JwtTokenService;
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
 * 认证服务实现
 */
@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
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
    @Override
    public LoginVO login(String email, String password) {
        logger.info("开始处理登录请求，邮箱：{}", email);
        Optional<User> opt = userMapper.findByEmail(email);
        if (opt.isEmpty()) {
            logger.warn("登录失败，邮箱未注册：{}", email);
            return LoginVO.fail("邮箱未注册");
        }

        User user = opt.get();
        if (user.getStatus() == UserStatus.PENDING) {
            logger.warn("登录失败，账号审核中：{}", email);
            return LoginVO.fail("账号审核中");
        }
        if (user.getStatus() == UserStatus.DISABLED) {
            logger.warn("登录失败，账号已被禁用：{}", email);
            return LoginVO.fail("账号已被禁用");
        }

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            logger.warn("登录失败，密码错误：{}", email);
            return LoginVO.fail("邮箱或密码错误");
        }

        user.setLastLoginAt(LocalDateTime.now());
        userMapper.update(user);

        // 生成 JWT，subject=email，附加 id 与 role claim
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("role", roleToString(user.getRole()));
        String token = jwtTokenService.generateToken(user.getEmail(), claims);

        LoginVO.UserInfo userInfo = new LoginVO.UserInfo();
        userInfo.setId(user.getId());
        userInfo.setEmail(user.getEmail());
        userInfo.setRole(roleToString(user.getRole()));
        userInfo.setStatus(statusToString(user.getStatus()));

        logger.info("登录成功，邮箱：{}，用户ID：{}", email, user.getId());
        return LoginVO.success("登录成功", token, userInfo);
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
    @Override
    public ResetPasswordVO resetPassword(String email, String newPassword, String verificationCode) {
        logger.info("开始处理密码重置请求，邮箱：{}", email);
        // TODO: TASK013 - 对接验证码校验服务
        // 暂时占位校验逻辑，验证码服务实现后需要替换
        if (verificationCode == null || verificationCode.trim().isEmpty()) {
            logger.warn("密码重置失败，验证码为空：{}", email);
            return ResetPasswordVO.fail(400, "验证码不能为空");
        }
        
        // 查找用户
        Optional<User> opt = userMapper.findByEmail(email);
        if (opt.isEmpty()) {
            logger.warn("密码重置失败，邮箱不存在：{}", email);
            return ResetPasswordVO.fail(404, "邮箱不存在");
        }

        // TODO: TASK013 - 实际验证码校验逻辑
        // 当前占位：简单检查验证码长度
        if (verificationCode.length() != 6) {
            logger.warn("密码重置失败，验证码错误：{}", email);
            return ResetPasswordVO.fail(400, "验证码错误");
        }

        User user = opt.get();
        // 加密新密码并更新
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.update(user);

        logger.info("密码重置成功，邮箱：{}", email);
        return ResetPasswordVO.success("密码重置成功");
    }

    /**
     * 修改密码（通过用户ID）
     * @param userId 当前登录用户ID
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return 修改结果
     */
    @Override
    public ChangePasswordVO changePasswordByUserId(Integer userId, String oldPassword, String newPassword) {
        logger.info("开始处理密码修改请求，用户ID：{}", userId);
        
        // 查找用户
        Optional<User> opt = userMapper.findById(userId);
        if (opt.isEmpty()) {
            logger.warn("密码修改失败，用户不存在：{}", userId);
            return ChangePasswordVO.fail(400, "用户不存在");
        }

        User user = opt.get();
        
        // 验证原密码
        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            logger.warn("密码修改失败，原密码错误，用户ID：{}", userId);
            return ChangePasswordVO.fail(400, "原密码错误");
        }

        // 加密新密码并更新
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.update(user);

        logger.info("密码修改成功，用户ID：{}，邮箱：{}", userId, user.getEmail());
        return ChangePasswordVO.success("密码修改成功");
    }
}
