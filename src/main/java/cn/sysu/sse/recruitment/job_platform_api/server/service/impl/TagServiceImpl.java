package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.TagCreateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Tag;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.TagCategory;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.TagCreateResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.TagListResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.TagCategoryMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.TagMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.service.TagService;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

	private static final Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);

	@Autowired
	private TagCategoryMapper tagCategoryMapper;
	@Autowired
	private TagMapper tagMapper;

	@Override
	public TagListResponseVO getAllGroupedTags() {
		List<TagCategory> categories = tagCategoryMapper.listAll();
		List<Tag> tags = tagMapper.listAll();

		Map<Integer, List<Tag>> tagGroup = tags.stream().collect(Collectors.groupingBy(Tag::getCategoryId));

		List<TagListResponseVO.CategoryGroup> grouped = new ArrayList<>();
		for (TagCategory category : categories) {
			TagListResponseVO.CategoryGroup group = new TagListResponseVO.CategoryGroup();
			group.setCategoryId(category.getId());
			group.setCategoryName(category.getName());
			List<Tag> list = tagGroup.getOrDefault(category.getId(), List.of());
			List<TagListResponseVO.TagItem> tagItems = list.stream().map(tag -> {
				TagListResponseVO.TagItem item = new TagListResponseVO.TagItem();
				item.setTagId(tag.getId());
				item.setTagName(tag.getName());
				return item;
			}).collect(Collectors.toList());
			group.setTags(tagItems);
			grouped.add(group);
		}

		TagListResponseVO vo = new TagListResponseVO();
		vo.setGroupedTags(grouped);
		return vo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TagCreateResponseVO createTag(Integer userId, TagCreateDTO dto) {
		logger.info("创建自定义标签，userId={}，dto={}", userId, dto);
		if (userId == null) {
			throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户未登录");
		}
		TagCategory category = tagCategoryMapper.findById(dto.getCategoryId());
		if (category == null) {
			throw new BusinessException(ErrorCode.BAD_REQUEST, "分类不存在");
		}
		String cleanedName = sanitizeTagName(dto.getName());
		if (!StringUtils.hasText(cleanedName)) {
			throw new BusinessException(ErrorCode.BAD_REQUEST, "标签名称无效");
		}
		Tag existing = tagMapper.findByName(cleanedName);
		if (existing != null) {
			throw new BusinessException(ErrorCode.CONFLICT, "标签已存在");
		}

		Tag tag = new Tag();
		tag.setName(cleanedName);
		tag.setCategoryId(category.getId());
		tag.setCreatedBy(userId);
		try {
			int inserted = tagMapper.insert(tag);
			if (inserted == 0 || tag.getId() == null) {
				throw new BusinessException(ErrorCode.INTERNAL_ERROR, "创建标签失败");
			}
		} catch (DuplicateKeyException ex) {
			logger.warn("标签名称已存在，userId={}, name={}", userId, cleanedName);
			throw new BusinessException(ErrorCode.CONFLICT, "标签已存在");
		}

		TagCreateResponseVO response = new TagCreateResponseVO();
		response.setTagId(tag.getId());
		response.setTagName(tag.getName());
		response.setCategoryId(tag.getCategoryId());
		return response;
	}

	private String sanitizeTagName(String name) {
		if (name == null) {
			return null;
		}
		String text = Jsoup.clean(name, Safelist.none()).trim();
		return Objects.equals("", text) ? null : text;
	}
}
