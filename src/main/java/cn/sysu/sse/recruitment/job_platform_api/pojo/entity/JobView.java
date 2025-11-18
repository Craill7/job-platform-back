package cn.sysu.sse.recruitment.job_platform_api.pojo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobView {
	private Long id;
	private Integer jobId;
	private Integer viewerUserId;
	private LocalDateTime viewedAt;
	private String clientIp;
	private String userAgent;
}

