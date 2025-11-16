package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;

@Data
public class TagCreateResponseVO {
	private Integer tagId;
	private String tagName;
	private Integer categoryId;
}
