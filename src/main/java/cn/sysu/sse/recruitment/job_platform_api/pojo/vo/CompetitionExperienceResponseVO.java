package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;

@Data
public class CompetitionExperienceResponseVO {
	private Long id;
	private String competitionName;
	private String role;
	private String award;
	private String date; // YYYY-MM格式
}

