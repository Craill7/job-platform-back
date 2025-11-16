package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.TagCreateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.TagCreateResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.TagListResponseVO;

public interface TagService {
	TagListResponseVO getAllGroupedTags();

	TagCreateResponseVO createTag(Integer userId, TagCreateDTO dto);
}
