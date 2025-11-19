package cn.sysu.sse.recruitment.job_platform_api.server.controller.student;

import cn.sysu.sse.recruitment.job_platform_api.common.result.ApiResponse;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.CompanyDetailVO;
import cn.sysu.sse.recruitment.job_platform_api.server.service.CompanyCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 企业中心控制器（学生端）
 */
@RestController
@RequestMapping("/company-center")
public class CompanyCenterController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyCenterController.class);

	@Autowired
	private CompanyCenterService companyCenterService;

	/**
	 * 获取企业详情
	 * @param companyId 企业ID
	 * @return 企业详情
	 */
	@GetMapping("/detail/{company_id}")
	public ApiResponse<CompanyDetailVO> getCompanyDetail(
			@PathVariable("company_id") Integer companyId) {
		logger.info("收到获取企业详情请求，企业ID：{}", companyId);

		CompanyDetailVO result = companyCenterService.getCompanyDetail(companyId);

		return ApiResponse.success(result);
	}
}

