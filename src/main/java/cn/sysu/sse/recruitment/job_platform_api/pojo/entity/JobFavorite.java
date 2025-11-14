package cn.sysu.sse.recruitment.job_platform_api.pojo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobFavorite {
	private Long id;
	private Integer studentUserId;
	private Integer jobId;
	private LocalDateTime savedAt;
}

