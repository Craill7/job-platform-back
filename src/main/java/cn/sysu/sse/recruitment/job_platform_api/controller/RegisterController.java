package cn.sysu.sse.recruitment.job_platform_api.controller;

import cn.sysu.sse.recruitment.job_platform_api.service.RegisterService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class RegisterController {

	private final RegisterService registerService;

	public RegisterController(RegisterService registerService) {
		this.registerService = registerService;
	}

	@PostMapping("/register")
	public RegisterService.SimpleResponse register(@Valid @RequestBody RegisterRequest req) {
		return registerService.register(req.getEmail(), req.getPassword(), req.getVerificationCode(), req.getRole());
	}

	@Data
	public static class RegisterRequest {
		@NotBlank
		private String email;
		@NotBlank
		private String password;
		@NotBlank
		private String verificationCode;
		@NotBlank
		private String role; // "student" 或 "hr"
	}
}