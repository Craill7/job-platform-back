package cn.sysu.sse.recruitment.job_platform_api.pojo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Resume {
	private Long id;
	private Integer studentUserId;
	private String fileName;
	private String fileUrl;
	private Long fileSize;
	private String usageType;
	private LocalDateTime uploadedAt;
	private Integer templateId;
}

