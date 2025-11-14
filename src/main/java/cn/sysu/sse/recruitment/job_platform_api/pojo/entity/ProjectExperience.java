package cn.sysu.sse.recruitment.job_platform_api.pojo.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ProjectExperience {
	private Long id;
	private Integer studentUserId;
	private String projectName;
	private String role;
	private String projectLink;
	private LocalDate startDate;
	private LocalDate endDate;
	private String description;
}

