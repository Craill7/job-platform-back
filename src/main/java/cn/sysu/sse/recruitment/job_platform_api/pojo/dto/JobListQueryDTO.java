package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import lombok.Data;

/**
 * 求职中心岗位列表查询参数
 * 兼容历史参数（keyword/location/tag），同时支持最新OpenAPI中定义的字段
 */
@Data
public class JobListQueryDTO {
	private String keyword; // 兼容历史：综合关键词搜索
	private String location; // 兼容历史：工作地区
	private String tag; // 兼容历史：技能标签（逗号分隔）

	private String title; // 最新：职位名称精确筛选
	private String companyName; // 最新：公司名称

	/**
	 * 最新接口中 province/city 原始入参值（可能是名称或ID）
	 * 若为数字，会在控制层解析后填充 provinceId/cityId 字段
	 */
	private String province;
	private String city;
	private Integer provinceId;
	private Integer cityId;

	private Integer minSalary;
	private Integer maxSalary;
	private String type; // 职能类别
	private String workNature; // 工作性质（校招/实习等）
	private Integer page = 1; // 页码
	private Integer pageSize = 10; // 每页数量
}

