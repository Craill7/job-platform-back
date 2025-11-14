package cn.sysu.sse.recruitment.job_platform_api.pojo.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class OrganizationExperience {
	private Long id;
	private Integer studentUserId;
	private String organizationName;
	private String role;
	private LocalDate startDate;
	private LocalDate endDate;
	private String description;
}

