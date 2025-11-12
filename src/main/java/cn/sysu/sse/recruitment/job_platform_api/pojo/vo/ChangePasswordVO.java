package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;

/**
 * 修改密码响应
 */
@Data
public class ChangePasswordVO {
    private Integer code;
    private String message;

    private ChangePasswordVO() {}

    /**
     * 创建成功修改密码结果
     * @param message 成功消息
     * @return 修改结果
     */
    public static ChangePasswordVO success(String message) {
        ChangePasswordVO result = new ChangePasswordVO();
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
    public static ChangePasswordVO fail(int code, String message) {
        ChangePasswordVO result = new ChangePasswordVO();
        result.code = code;
        result.message = message;
        return result;
    }
}
