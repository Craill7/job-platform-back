package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * 企业详情 VO（学生端）
 */
@Data
public class CompanyDetailVO {
	@JsonProperty("company_id")
	private Integer companyId;

	@JsonProperty("company_name")
	private String companyName;

	@JsonProperty("company_industry")
	private String companyIndustry;

	@JsonProperty("company_nature")
	private String companyNature;

	@JsonProperty("company_scale")
	private String companyScale;

	@JsonProperty("company_address")
	private String companyAddress;

	@JsonProperty("company_logo_url")
	private String companyLogoUrl;

	@JsonProperty("contact_person_name")
	private String contactPersonName;

	@JsonProperty("contact_person_phone")
	private String contactPersonPhone;

	private String description;

	@JsonProperty("statistics")
	private StatisticsVO statistics;

	@JsonProperty("other_jobs")
	private List<OtherJobVO> otherJobs;

	@Data
	public static class StatisticsVO {
		@JsonProperty("active_positions")
		private Integer activePositions;

		@JsonProperty("resume_process_rate")
		private String resumeProcessRate; // 格式：70%

		@JsonProperty("recent_activity")
		private String recentActivity; // 格式：2天前
	}

	@Data
	public static class OtherJobVO {
		@JsonProperty("job_id")
		private Integer jobId;

		@JsonProperty("job_title")
		private String jobTitle;

		@JsonProperty("posted_at")
		private LocalDate postedAt;
	}
}

