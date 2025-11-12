package cn.sysu.sse.recruitment.job_platform_api.domain.response;

import lombok.Data;

/**
 * 登录响应
 */
@Data
public class LoginResponse {
    private Integer code;
    private String message;
    private String token;
    private UserInfo userInfo;

    private LoginResponse() {}

    /**
     * 创建成功登录结果
     * @param message 成功消息
     * @param token JWT token
     * @param userInfo 用户信息
     * @return 登录结果
     */
    public static LoginResponse success(String message, String token, UserInfo userInfo) {
        LoginResponse result = new LoginResponse();
        result.code = 200;
        result.message = message;
        result.token = token;
        result.userInfo = userInfo;
        return result;
    }

    /**
     * 创建失败登录结果
     * @param message 失败消息
     * @return 登录结果
     */
    public static LoginResponse fail(String message) {
        LoginResponse result = new LoginResponse();
        result.code = 401;
        result.message = message;
        return result;
    }

    @Data
    public static class UserInfo {
        private Integer id;
        private String email;
        private String role;
        private String status;
    }
}
