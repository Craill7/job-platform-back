package cn.sysu.sse.recruitment.job_platform_api.pojo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Event {
	private Long id;
	private Integer adminUserId;
	private String eventTitle;
	private String eventSummary;
	private LocalDateTime eventStartTime;
	private LocalDateTime eventEndTime; 
	private String eventLocation;
	private String eventType;
	private String targetAudience;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}

