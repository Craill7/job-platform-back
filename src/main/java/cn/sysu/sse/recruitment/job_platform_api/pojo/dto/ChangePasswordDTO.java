package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 修改密码请求
 */
@Data
public class ChangePasswordDTO {
    @NotBlank(message = "原密码不能为空")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
