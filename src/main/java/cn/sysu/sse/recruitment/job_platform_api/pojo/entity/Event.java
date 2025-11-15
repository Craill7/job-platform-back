package cn.sysu.sse.recruitment.job_platform_api.pojo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Event {
	private Long id;
	private Integer adminUserId;
	private String eventTitle;
	private String eventDescription;
	private LocalDateTime eventStartTime;
	private LocalDateTime eventEndTime; 
	private String eventLocation;
	private String eventType;
	private Integer targetAudience; // 0=all, 1=students, 2=companies
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}

