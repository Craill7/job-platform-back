package cn.sysu.sse.recruitment.job_platform_api.pojo.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 企业外部链接实体
 */
@Data
public class CompanyExternalLink {
	/**
	 * 链接记录ID
	 */
	private Integer id;

	/**
	 * 企业ID
	 */
	private Integer companyId;

	/**
	 * 链接名称
	 */
	private String linkName;

	/**
	 * 链接地址
	 */
	private String linkUrl;

	/**
	 * 创建时间
	 */
	private LocalDateTime createdAt;
}
