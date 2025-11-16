package cn.sysu.sse.recruitment.job_platform_api.server.controller.common;

import cn.sysu.sse.recruitment.job_platform_api.common.result.ApiResponse;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.TagCreateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.TagCreateResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.TagListResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.server.service.TagService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tags")
public class TagController {

	private static final Logger logger = LoggerFactory.getLogger(TagController.class);

	@Autowired
	private TagService tagService;

	@GetMapping
	public ApiResponse<TagListResponseVO> getAllTags() {
		TagListResponseVO data = tagService.getAllGroupedTags();
		return ApiResponse.of(200, "获取标签成功", data);
	}

	@PostMapping
	public ApiResponse<TagCreateResponseVO> createCustomTag(@Valid @RequestBody TagCreateDTO dto,
			Authentication authentication) {
		Integer userId = getCurrentUserId(authentication);
		if (userId == null) {
			logger.warn("未登录用户尝试创建标签");
			return ApiResponse.error(401, "用户未登录");
		}
		TagCreateResponseVO data = tagService.createTag(userId, dto);
		return ApiResponse.of(201, "自定义标签创建成功", data);
	}

	private Integer getCurrentUserId(Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		try {
			return Integer.valueOf(authentication.getName());
		} catch (NumberFormatException ex) {
			logger.warn("解析用户ID失败", ex);
			return null;
		}
	}
}
