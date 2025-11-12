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

	@Data
	public static class LoginRequest {
		@NotBlank
		@Email
		private String email;

		@NotBlank
		private String password;
	}
}