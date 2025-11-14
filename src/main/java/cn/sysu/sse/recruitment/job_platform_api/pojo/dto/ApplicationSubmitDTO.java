package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicationSubmitDTO {
	@NotNull(message = "岗位ID不能为空")
	private Integer jobId;
	
	@NotNull(message = "简历ID不能为空")
	private String resumeId;
}

