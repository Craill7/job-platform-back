package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobWithStatsDTO {
	private Integer jobId;
	private String title;
	private Integer workNature;
	private Integer status;
	private LocalDateTime updatedAt;
	private Long receivedNum;
	private Long noReviewNum;
}
