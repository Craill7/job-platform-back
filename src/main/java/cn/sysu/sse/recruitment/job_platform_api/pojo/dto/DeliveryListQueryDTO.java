package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import lombok.Data;

/**
 * 已投递岗位列表查询 DTO
 */
@Data
public class DeliveryListQueryDTO {
	/**
	 * 岗位名称（模糊搜索）
	 */
	private String jobTitle;
	
	/**
	 * 公司名称（模糊搜索）
	 */
	private String companyName;
	
	/**
	 * 页码（默认 1）
	 */
	private Integer page = 1;
	
	/**
	 * 每页大小（默认 10）
	 */
	private Integer pageSize = 10;
}

