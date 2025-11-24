package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 注册请求
 */
@Data
public class RegisterDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String verificationCode;
    @NotBlank
    private String name;
    @NotBlank
    private String role; // "student" 或 "hr"
}
