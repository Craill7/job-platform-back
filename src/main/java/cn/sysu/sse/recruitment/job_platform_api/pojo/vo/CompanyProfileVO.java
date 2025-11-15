package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 企业信息回显 VO
 */
@Data
public class CompanyProfileVO {
	/**
	 * 在招岗位数
	 */
	private Long openJobsCount;

	/**
	 * 简历处理率
	 */
	private Double resumeProcessRate;

	/**
	 * 最近登录时间
	 */
	private LocalDateTime lastLoginAt;

	private String companyName;
	private String description;
	private String logoUrl;
	private String nature;
	private String industry;
	private String companyScale;
	private String contactPersonName;
	private String contactPersonPhone;
	private String companyAddress;
	private List<ExternalLink> externalLinks;

	@Data
	public static class ExternalLink {
		private Integer id;
		private String linkName;
		private String linkUrl;
	}
}
