package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Tag;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.TagCategory;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.TagListResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.TagCategoryMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.TagMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

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
}
