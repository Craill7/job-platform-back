package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ResumeFileVO {
	private Integer id;
	private String fileName;
	private String fileUrl;
	private Integer fileSize;
	private Integer templateId;
	private String usage;
	private LocalDateTime uploadedAt;
}

