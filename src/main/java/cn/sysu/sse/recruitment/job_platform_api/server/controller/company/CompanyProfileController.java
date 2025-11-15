package cn.sysu.sse.recruitment.job_platform_api.server.controller.company;

import cn.sysu.sse.recruitment.job_platform_api.common.result.ApiResponse;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.CompanyProfileVO;
import cn.sysu.sse.recruitment.job_platform_api.server.service.CompanyProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 企业信息回显 Controller
 */
@RestController
@RequestMapping("/hr/company")
public class CompanyProfileController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyProfileController.class);

	@Autowired
	private CompanyProfileService companyProfileService;

	/**
	 * 企业信息回显
	 * @param authentication 当前登录认证信息
	 * @return 企业信息
	 */
	@GetMapping("/profile")
	public ApiResponse<CompanyProfileVO> getCompanyProfile(Authentication authentication) {
		Integer userId = Integer.parseInt(authentication.getName());
		logger.info("收到企业信息回显请求，用户ID：{}", userId);
		CompanyProfileVO data = companyProfileService.getCompanyProfile(userId);
		return ApiResponse.success(data);
	}
}
