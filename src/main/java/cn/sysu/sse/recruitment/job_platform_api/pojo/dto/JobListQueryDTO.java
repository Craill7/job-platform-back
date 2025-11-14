package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import lombok.Data;

@Data
public class JobListQueryDTO {
	private String keyword; // 搜索关键词（职位名称或公司名称）
	private String location; // 工作地区
	private String type; // 职能类别
	private String tag; // 需求能力（技能标签），逗号分隔
	private String workNature; // 工作性质（校招/实习等）
	private Integer page = 1; // 页码
	private Integer pageSize = 10; // 每页数量
}

