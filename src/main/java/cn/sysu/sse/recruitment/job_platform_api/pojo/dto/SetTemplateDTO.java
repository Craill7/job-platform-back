package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SetTemplateDTO {
	@NotNull(message = "模板ID不能为空")
	private Integer templateId;
}

