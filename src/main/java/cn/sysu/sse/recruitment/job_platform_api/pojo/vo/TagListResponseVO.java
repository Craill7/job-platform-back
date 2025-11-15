package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class TagListResponseVO {
	private List<CategoryGroup> groupedTags;

	@Data
	public static class CategoryGroup {
		private Integer categoryId;
		private String categoryName;
		private List<TagItem> tags;
	}

	@Data
	public static class TagItem {
		private Integer tagId;
		private String tagName;
	}
}
