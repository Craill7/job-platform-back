package cn.sysu.sse.recruitment.job_platform_api.server.controller.auth;

import cn.sysu.sse.recruitment.job_platform_api.server.service.RegisterService;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.RegisterVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.RegisterDTO;
import cn.sysu.sse.recruitment.job_platform_api.common.result.ApiResponse;
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
	public ApiResponse<Void> register(@Valid @RequestBody RegisterDTO req) {
		logger.info("收到注册请求，邮箱：{}，角色：{}", req.getEmail(), req.getRole());
		RegisterVO result = registerService.register(req.getEmail(), req.getPassword(), req.getVerificationCode(), req.getRole());
		logger.info("注册请求处理完成，结果代码：{}", result.getCode());
		
		// 根据业务结果码决定是成功还是失败
		if (result.getCode() == 201 || result.getCode() == 202) {
			return ApiResponse.of(result.getCode(), result.getMessage(), null);
		} else {
			return ApiResponse.error(result.getCode(), result.getMessage());
		}
	}
}
