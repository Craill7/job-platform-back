package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ApplicationSubmitVO {
	private Integer applicationId;
	private Integer jobId;
	private Integer studentUserId;
	private String resumeId;
	private Integer status;
	private LocalDateTime submittedAt;
	private LocalDateTime updatedAt;
}

