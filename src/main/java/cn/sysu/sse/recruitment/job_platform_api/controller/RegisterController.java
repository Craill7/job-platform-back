package cn.sysu.sse.recruitment.job_platform_api.controller;

import cn.sysu.sse.recruitment.job_platform_api.service.RegisterService;
import cn.sysu.sse.recruitment.job_platform_api.domain.response.RegisterResponse;
import cn.sysu.sse.recruitment.job_platform_api.domain.request.RegisterRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class RegisterController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	private RegisterService registerService;

	/**
	 * 用户注册
	 * @param req 注册请求参数，包含邮箱、密码、验证码和角色
	 * @return 注册结果
	 */
	@PostMapping("/register")
	public RegisterResponse register(@Valid @RequestBody RegisterRequest req) {
		logger.info("收到注册请求，邮箱：{}，角色：{}", req.getEmail(), req.getRole());
		RegisterResponse result = registerService.register(req.getEmail(), req.getPassword(), req.getVerificationCode(), req.getRole());
		logger.info("注册请求处理完成，结果代码：{}", result.getCode());
		return result;
	}
}