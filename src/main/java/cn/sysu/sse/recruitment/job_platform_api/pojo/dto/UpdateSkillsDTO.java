package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateSkillsDTO {
	@NotBlank(message = "技能描述不能为空")
	private String skillsSummary;
}

