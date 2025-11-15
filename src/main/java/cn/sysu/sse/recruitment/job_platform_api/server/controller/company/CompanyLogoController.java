package cn.sysu.sse.recruitment.job_platform_api.server.controller.company;

import cn.sysu.sse.recruitment.job_platform_api.common.result.ApiResponse;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.CompanyLogoUploadVO;
import cn.sysu.sse.recruitment.job_platform_api.server.service.CompanyLogoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 企业Logo上传 Controller
 */
@RestController
@RequestMapping("/upload")
public class CompanyLogoController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyLogoController.class);

	@Autowired
	private CompanyLogoService companyLogoService;

	/**
	 * 上传企业Logo
	 * @param file 上传的图片文件
	 * @param authentication 当前登录信息
	 * @return Logo访问地址
	 */
	@PostMapping("/company-logo")
	public ApiResponse<CompanyLogoUploadVO> uploadCompanyLogo(@RequestParam("file") MultipartFile file,
			Authentication authentication) {
		Integer userId = getHrUserId(authentication);
		if (userId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		logger.info("收到企业Logo上传请求，用户ID：{}，文件名：{}", userId, file != null ? file.getOriginalFilename() : "");
		CompanyLogoUploadVO vo = companyLogoService.uploadCompanyLogo(userId, file);
		return ApiResponse.of(200, "Logo上传成功", vo);
	}

	private Integer getHrUserId(Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		try {
			String userIdStr = authentication.getName();
			return Integer.parseInt(userIdStr);
		} catch (Exception e) {
			logger.warn("解析用户ID失败", e);
			return null;
		}
	}
}
