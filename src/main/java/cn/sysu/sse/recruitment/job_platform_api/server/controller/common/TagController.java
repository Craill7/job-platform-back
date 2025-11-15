package cn.sysu.sse.recruitment.job_platform_api.server.controller.common;

import cn.sysu.sse.recruitment.job_platform_api.common.result.ApiResponse;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.TagListResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.server.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tags")
public class TagController {

	@Autowired
	private TagService tagService;

	@GetMapping
	public ApiResponse<TagListResponseVO> getAllTags() {
		TagListResponseVO data = tagService.getAllGroupedTags();
		return ApiResponse.of(200, "获取标签成功", data);
	}
}
