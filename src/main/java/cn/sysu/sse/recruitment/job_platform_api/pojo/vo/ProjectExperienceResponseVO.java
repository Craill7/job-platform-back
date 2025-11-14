package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ProjectExperienceResponseVO {
	private Long id;
	private String projectName;
	private String role;
	private String projectLink;
	private LocalDate startDate;
	private LocalDate endDate;
	private String description;
}

