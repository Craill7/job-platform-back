package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;

/**
 * 重置密码响应
 */
@Data
public class ResetPasswordVO {
    private Integer code;
    private String message;

    private ResetPasswordVO() {}

    /**
     * 创建成功重置密码结果
     * @param message 成功消息
     * @return 重置结果
     */
    public static ResetPasswordVO success(String message) {
        ResetPasswordVO result = new ResetPasswordVO();
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
    public static ResetPasswordVO fail(int code, String message) {
        ResetPasswordVO result = new ResetPasswordVO();
        result.code = code;
        result.message = message;
        return result;
    }
}
