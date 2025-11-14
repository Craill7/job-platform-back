package cn.sysu.sse.recruitment.job_platform_api.pojo.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class WorkExperience {
	private Long id;
	private Integer studentUserId;
	private String companyName;
	private String positionTitle;
	private LocalDate startDate;
	private LocalDate endDate;
	private String description;
}

