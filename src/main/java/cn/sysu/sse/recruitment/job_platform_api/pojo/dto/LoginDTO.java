package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * 登录请求
 */
@Data
public class LoginDTO {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
