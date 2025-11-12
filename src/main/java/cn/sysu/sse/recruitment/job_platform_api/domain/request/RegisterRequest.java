package cn.sysu.sse.recruitment.job_platform_api.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 注册请求
 */
@Data
public class RegisterRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String verificationCode;
    @NotBlank
    private String role; // "student" 或 "hr"
}
