package cn.sysu.sse.recruitment.job_platform_api.domain.response;

import lombok.Data;

/**
 * 修改密码响应
 */
@Data
public class ChangePasswordResponse {
    private Integer code;
    private String message;

    private ChangePasswordResponse() {}

    /**
     * 创建成功修改密码结果
     * @param message 成功消息
     * @return 修改结果
     */
    public static ChangePasswordResponse success(String message) {
        ChangePasswordResponse result = new ChangePasswordResponse();
        result.code = 200;
        result.message = message;
        return result;
    }

    /**
     * 创建失败修改密码结果
     * @param code 错误代码
     * @param message 失败消息
     * @return 修改结果
     */
    public static ChangePasswordResponse fail(int code, String message) {
        ChangePasswordResponse result = new ChangePasswordResponse();
        result.code = code;
        result.message = message;
        return result;
    }
}
