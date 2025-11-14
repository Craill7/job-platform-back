package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;
import java.util.List;

@Data
public class ResumeDraftVO {
	private ProfileVO profile;
	private EducationVO education;
	private String skillsSummary;
	private List<WorkExperienceVO> workExperiences;
	private List<ProjectExperienceVO> projects;
	private List<OrganizationExperienceVO> organizations;
	private List<CompetitionExperienceVO> competitions;
	
	@Data
	public static class ProfileVO {
		private String fullName;
		private String dateOfBirth; // YYYY-MM格式
		private String email;
		private String gender; // male/female
		private String jobSeekingStatus; // internship/fulltime等
		private String phoneNumber;
		private String avatarUrl;
	}
	
	@Data
	public static class EducationVO {
		private String degree; // bachelor/master/doctor
		private String schoolName;
		private String major;
		private String majorRank;
		private String startDate; // YYYY-MM格式
		private String endDate; // YYYY-MM格式
	}
	
	@Data
	public static class WorkExperienceVO {
		private Long id;
		private String companyName;
		private String positionTitle;
		private String startDate; // YYYY-MM格式
		private String endDate; // YYYY-MM格式
		private String description;
	}
	
	@Data
	public static class ProjectExperienceVO {
		private Long id;
		private String projectName;
		private String role;
		private String projectLink;
		private String startDate; // YYYY-MM格式
		private String endDate; // YYYY-MM格式
		private String description;
	}
	
	@Data
	public static class OrganizationExperienceVO {
		private Long id;
		private String organizationName;
		private String role;
		private String startDate; // YYYY-MM格式
		private String endDate; // YYYY-MM格式
		private String description;
	}
	
	@Data
	public static class CompetitionExperienceVO {
		private Long id;
		private String competitionName;
		private String role;
		private String award;
		private String date; // YYYY-MM格式
	}
}

