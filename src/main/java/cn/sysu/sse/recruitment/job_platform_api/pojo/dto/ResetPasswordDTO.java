package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * 重置密码请求
 */
@Data
public class ResetPasswordDTO {
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @NotBlank(message = "新密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String verificationCode;

}
