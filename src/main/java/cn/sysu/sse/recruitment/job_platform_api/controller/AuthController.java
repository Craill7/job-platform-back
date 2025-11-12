package cn.sysu.sse.recruitment.job_platform_api.controller;

import cn.sysu.sse.recruitment.job_platform_api.service.AuthService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public AuthService.LoginResult login(@Valid @RequestBody LoginRequest req) {
		return authService.login(req.getEmail(), req.getPassword());
	}

	@PutMapping("/reset")
	public AuthService.ResetPasswordResult resetPassword(@Valid @RequestBody ResetPasswordRequest req) {
		return authService.resetPassword(req.getEmail(), req.getPassword(), req.getVerificationCode());
	}

	@Data
	public static class LoginRequest {
		@NotBlank
		@Email
		private String email;

		@NotBlank
		private String password;
	}

	@Data
	public static class ResetPasswordRequest {
		@NotBlank(message = "邮箱不能为空")
		@Email(message = "邮箱格式不正确")
		private String email;

		@NotBlank(message = "新密码不能为空")
		private String password;

		@NotBlank(message = "验证码不能为空")
		private String verificationCode;
	}
}