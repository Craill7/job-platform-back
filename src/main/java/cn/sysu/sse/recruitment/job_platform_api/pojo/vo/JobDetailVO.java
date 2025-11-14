package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class JobDetailVO {
	private Integer jobId;
	private String title;
	private String salaryRange;
	private String location;
	private String workNature;
	private String requiredDegree;
	private LocalDate requiredStartDate;
	private List<String> requiredSkills;
	private Integer headcount;
	private LocalDate postedAt;
	private String positionDescription;
	private String positionRequirements;
	private List<String> bonusPoints;
	private String workAddress;
	private CompanyInfoVO companyInfo;
	private Boolean isFavorited;
	
	@Data
	public static class CompanyInfoVO {
		private String companyLogoUrl;
		private String companyName;
		private String companyIndustry;
		private String companyScale;
		private String contactPersonName;
		private String contactPersonPhone;
		private String companyWebsiteUrl;
		private List<OtherJobVO> otherJobs;
	}
	
	@Data
	public static class OtherJobVO {
		private Integer jobId;
		private String title;
	}
}

