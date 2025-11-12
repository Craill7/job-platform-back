package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;

/**
 * 注册响应
 */
@Data
public class RegisterVO {
    private Integer code;
    private String message;

    /**
     * 创建响应结果
     * @param code 响应代码
     * @param message 响应消息
     * @return 响应结果
     */
    public static RegisterVO of(Integer code, String message) {
        RegisterVO r = new RegisterVO();
        r.code = code;
        r.message = message;
        return r;
    }
}
