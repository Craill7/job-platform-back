package cn.sysu.sse.recruitment.job_platform_api.server.controller.hr;

import cn.sysu.sse.recruitment.job_platform_api.common.result.ApiResponse;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobListQueryDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobListResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.server.service.HrJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hr")
public class HrJobController {

	private static final Logger logger = LoggerFactory.getLogger(HrJobController.class);

	@Autowired
	private HrJobService hrJobService;

	@GetMapping("/jobs")
	public ApiResponse<HrJobListResponseVO> listCompanyJobs(@ModelAttribute HrJobListQueryDTO queryDTO,
			Authentication authentication) {
		Integer userId = getHrUserId(authentication);
		if (userId == null) {
			logger.warn("未登录用户尝试访问企业岗位列表");
			return ApiResponse.error(401, "用户未登录");
		}
		logger.info("收到企业岗位列表请求，用户ID={}，参数={}", userId, queryDTO);
		HrJobListResponseVO data = hrJobService.listCompanyJobs(userId, queryDTO);
		return ApiResponse.success(data);
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
