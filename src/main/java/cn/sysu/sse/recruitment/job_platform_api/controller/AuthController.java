package cn.sysu.sse.recruitment.job_platform_api.controller;

import cn.sysu.sse.recruitment.job_platform_api.service.AuthService;
import cn.sysu.sse.recruitment.job_platform_api.domain.request.LoginRequest;
import cn.sysu.sse.recruitment.job_platform_api.domain.request.ResetPasswordRequest;
import cn.sysu.sse.recruitment.job_platform_api.domain.response.LoginResponse;
import cn.sysu.sse.recruitment.job_platform_api.domain.response.ResetPasswordResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthService authService;

	/**
	 * 用户登录
	 * @param req 登录请求参数，包含邮箱和密码
	 * @return 登录结果，包含JWT token和用户信息
	 */
	@PostMapping("/login")
	public LoginResponse login(@Valid @RequestBody LoginRequest req) {
		logger.info("收到登录请求，邮箱：{}", req.getEmail());
		LoginResponse result = authService.login(req.getEmail(), req.getPassword());
		logger.info("登录请求处理完成，结果代码：{}", result.getCode());
		return result;
	}

	/**
	 * 找回密码
	 * @param req 重置密码请求参数，包含邮箱、新密码和验证码
	 * @return 找回结果
	 */
	@PutMapping("/reset")
	public ResetPasswordResponse resetPassword(@Valid @RequestBody ResetPasswordRequest req) {
		logger.info("收到密码找回请求，邮箱：{}", req.getEmail());
		ResetPasswordResponse result = authService.resetPassword(req.getEmail(), req.getPassword(), req.getVerificationCode());
		logger.info("密码找回请求处理完成，结果代码：{}", result.getCode());
		return result;
	}
}
