package cn.sysu.sse.recruitment.job_platform_api.server.controller.company;

import cn.sysu.sse.recruitment.job_platform_api.common.result.ApiResponse;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.CompanyProfileUpdateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.CompanyOptionsVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.CompanyProfileVO;
import cn.sysu.sse.recruitment.job_platform_api.server.service.CompanyOptionService;
import cn.sysu.sse.recruitment.job_platform_api.server.service.CompanyProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * 企业信息回显 Controller
 */
@RestController
@RequestMapping("/hr/company")
@Validated
public class CompanyProfileController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyProfileController.class);

	@Autowired
	private CompanyProfileService companyProfileService;
	@Autowired
	private CompanyOptionService companyOptionService;

	/**
	 * 企业信息回显
	 * @param authentication 当前登录认证信息
	 * @return 企业信息
	 */
	@GetMapping("/profile")
	public ApiResponse<CompanyProfileVO> getCompanyProfile(Authentication authentication) {
		Integer userId = getHrUserId(authentication);
		if (userId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		logger.info("收到企业信息回显请求，用户ID：{}", userId);
		CompanyProfileVO data = companyProfileService.getCompanyProfile(userId);
		return ApiResponse.success(data);
	}

	/**
	 * 获取企业信息选项
	 * @return 企业字典选项
	 */
	@GetMapping("/options")
	public ApiResponse<CompanyOptionsVO> getCompanyOptions() {
		CompanyOptionsVO data = companyOptionService.getCompanyOptions();
		return ApiResponse.success(data);
	}

	/**
	 * 更新企业信息
	 */
	@PutMapping("/profile")
	public ApiResponse<Void> updateCompanyProfile(@Valid @RequestBody CompanyProfileUpdateDTO dto,
			Authentication authentication) {
		Integer userId = getHrUserId(authentication);
		if (userId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		logger.info("收到企业信息更新请求，用户ID：{}", userId);
		companyProfileService.updateCompanyProfile(userId, dto);
		return ApiResponse.of(200, "企业信息更新成功", null);
	}

	private Integer getHrUserId(Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		try {
			return Integer.parseInt(authentication.getName());
		} catch (NumberFormatException ex) {
			logger.warn("解析用户ID失败", ex);
			return null;
		}
	}
}
