package cn.sysu.sse.recruitment.job_platform_api.domain.response;

import lombok.Data;

/**
 * 重置密码响应
 */
@Data
public class ResetPasswordResponse {
    private Integer code;
    private String message;

    private ResetPasswordResponse() {}

    /**
     * 创建成功重置密码结果
     * @param message 成功消息
     * @return 重置结果
     */
    public static ResetPasswordResponse success(String message) {
        ResetPasswordResponse result = new ResetPasswordResponse();
        result.code = 200;
        result.message = message;
        return result;
    }

    /**
     * 创建失败重置密码结果
     * @param code 错误代码
     * @param message 失败消息
     * @return 重置结果
     */
    public static ResetPasswordResponse fail(int code, String message) {
        ResetPasswordResponse result = new ResetPasswordResponse();
        result.code = code;
        result.message = message;
        return result;
    }
}
