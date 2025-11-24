package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.RegisterVO;

/**
 * 注册服务接口
 */
public interface RegisterService {

    /**
     * 用户注册
     * @param email 用户邮箱
     * @param password 用户密码
     * @param verificationCode 验证码
     * @param roleStr 角色字符串
     * @return 注册结果
     */
    RegisterVO register(String email, String password, String verificationCode, String roleStr, String name);
}
