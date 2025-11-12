package cn.sysu.sse.recruitment.job_platform_api.controller;

import cn.sysu.sse.recruitment.job_platform_api.service.AuthService;
import cn.sysu.sse.recruitment.job_platform_api.domain.request.LoginRequest;
import cn.sysu.sse.recruitment.job_platform_api.domain.request.ResetPasswordRequest;
import cn.sysu.sse.recruitment.job_platform_api.domain.request.ChangePasswordRequest;
import cn.sysu.sse.recruitment.job_platform_api.domain.response.LoginResponse;
import cn.sysu.sse.recruitment.job_platform_api.domain.response.ResetPasswordResponse;
import cn.sysu.sse.recruitment.job_platform_api.domain.response.ChangePasswordResponse;
import cn.sysu.sse.recruitment.job_platform_api.common.api.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
	public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
		logger.info("收到登录请求，邮箱：{}", req.getEmail());
		LoginResponse result = authService.login(req.getEmail(), req.getPassword());
		logger.info("登录请求处理完成，结果代码：{}", result.getCode());
		
		// 根据业务结果码决定是成功还是失败
		if (result.getCode() == 200) {
			return ApiResponse.success(result);
		} else {
			return ApiResponse.error(result.getCode(), result.getMessage());
		}
	}

	/**
	 * 找回密码
	 * @param req 重置密码请求参数，包含邮箱、新密码和验证码
	 * @return 找回结果
	 */
	@PutMapping("/reset")
	public ApiResponse<ResetPasswordResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest req) {
		logger.info("收到密码找回请求，邮箱：{}", req.getEmail());
		ResetPasswordResponse result = authService.resetPassword(req.getEmail(), req.getPassword(), req.getVerificationCode());
		logger.info("密码找回请求处理完成，结果代码：{}", result.getCode());
		
		// 根据业务结果码决定是成功还是失败
		if (result.getCode() == 200) {
			return ApiResponse.success(result);
		} else {
			return ApiResponse.error(result.getCode(), result.getMessage());
		}
	}

	/**
	 * 修改密码
	 * @param req 修改密码请求参数，包含原密码和新密码
	 * @param authentication 当前登录用户认证信息
	 * @return 修改结果
	 */
	@PutMapping("/change-password")
	public ApiResponse<ChangePasswordResponse> changePassword(@Valid @RequestBody ChangePasswordRequest req, Authentication authentication) {
		String userId = authentication.getName(); // 实际返回的是用户ID
		logger.info("收到密码修改请求，用户ID：{}", userId);
		ChangePasswordResponse result = authService.changePasswordByUserId(Integer.parseInt(userId), req.getOldPassword(), req.getNewPassword());
		logger.info("密码修改请求处理完成，结果代码：{}", result.getCode());
		
		// 根据业务结果码决定是成功还是失败
		if (result.getCode() == 200) {
			return ApiResponse.success(result);
		} else {
			return ApiResponse.error(result.getCode(), result.getMessage());
		}
	}
}
