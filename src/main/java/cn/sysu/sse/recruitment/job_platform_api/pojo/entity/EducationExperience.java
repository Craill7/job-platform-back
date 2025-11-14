package cn.sysu.sse.recruitment.job_platform_api.pojo.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EducationExperience {
	private Long id;
	private Integer studentUserId;
	private String schoolName;
	private Integer degreeLevel; // 0=本科, 1=硕士, 2=博士
	private String major;
	private LocalDate startDate;
	private LocalDate endDate;
	private String majorRank;
}

