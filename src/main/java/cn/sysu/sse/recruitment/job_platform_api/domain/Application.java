package cn.sysu.sse.recruitment.job_platform_api.domain;

import cn.sysu.sse.recruitment.job_platform_api.domain.enums.ApplicationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Application {
	private Integer id;
	private Integer jobId;
	private Integer studentUserId;
	private Long resumeId;
	private ApplicationStatus status;
	private LocalDateTime submittedAt;
	private LocalDateTime updatedAt;
}


