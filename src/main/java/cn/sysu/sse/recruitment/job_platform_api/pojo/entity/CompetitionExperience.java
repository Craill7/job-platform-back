package cn.sysu.sse.recruitment.job_platform_api.pojo.entity;

import lombok.Data;

@Data
public class CompetitionExperience {
	private Long id;
	private Integer studentUserId;
	private String competitionName;
	private String role;
	private String award;
	private String date; // YYYY-MM格式
}

