package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.LoginVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.ResetPasswordVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.ChangePasswordVO;

/**
 * 认证服务接口
 */
public interface AuthService {

    /**
     * 用户登录
     * @param email 用户邮箱
     * @param password 用户密码
     * @return 登录结果，包含JWT token和用户信息
     */
    LoginVO login(String email, String password);

    /**
     * 找回密码
     * @param email 用户邮箱
     * @param newPassword 新密码
     * @param verificationCode 验证码
     * @return 重置结果
     */
    ResetPasswordVO resetPassword(String email, String newPassword, String verificationCode);

    /**
     * 修改密码（通过用户ID）
     * @param userId 当前登录用户ID
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return 修改结果
     */
    ChangePasswordVO changePasswordByUserId(Integer userId, String oldPassword, String newPassword);
}
