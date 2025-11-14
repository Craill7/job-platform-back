package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class OrganizationExperienceResponseVO {
	private Long id;
	private String organizationName;
	private String role;
	private LocalDate startDate;
	private LocalDate endDate;
	private String description;
}

