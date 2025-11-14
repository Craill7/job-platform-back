package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompetitionExperienceDTO {
	@NotBlank(message = "竞赛名称不能为空")
	private String competitionName;
	
	private String role;
	private String award;
	private String date; // YYYY-MM格式
}

