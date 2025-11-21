package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class JobDetailVO {
	@JsonProperty("job_id")
	private Integer jobId;

	private String title;

	@JsonProperty("salary_range")
	private String salaryRange;

	private String address;

	@JsonProperty("address_detail")
	private String addressDetail;

	@JsonProperty("work_nature")
	private String workNature;

	@JsonProperty("required_degree")
	private String requiredDegree;

	@JsonProperty("required_start_date")
	private LocalDate requiredStartDate;

	private List<String> requiredSkills;

	private Integer headcount;

	@JsonProperty("posted_at")
	private LocalDate postedAt;

	@JsonProperty("position_description")
	private String positionDescription;

	@JsonProperty("position_requirements")
	private String positionRequirements;

	@JsonProperty("bonus_points")
	private List<String> bonusPoints;

	@JsonProperty("times")
	private Integer times;

	private String type;

	@JsonProperty("is_favorited")
	private Boolean isFavorited;

	@JsonProperty("company_info")
	private CompanyInfoVO companyInfo;

	@Data
	public static class CompanyInfoVO {
		@JsonProperty("company_id")
		private Integer companyId;

		@JsonProperty("company_logo_url")
		private String companyLogoUrl;

		@JsonProperty("company_name")
		private String companyName;

		@JsonProperty("company_industry")
		private String companyIndustry;

		@JsonProperty("company_nature")
		private String companyNature;

		@JsonProperty("company_scale")
		private String companyScale;

		@JsonProperty("contact_person_name")
		private String contactPersonName;

		@JsonProperty("contact_person_phone")
		private String contactPersonPhone;

		@JsonProperty("company_website_url")
		private String companyWebsiteUrl;

		@JsonProperty("company_links")
		private List<CompanyLinkVO> companyLinks;
	}

	@Data
	public static class CompanyLinkVO {
		@JsonProperty("link_name")
		private String linkName;

		@JsonProperty("link_url")
		private String linkUrl;
	}
}