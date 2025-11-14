package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class WorkExperienceResponseVO {
	private Long id;
	private String companyName;
	private String positionTitle;
	private LocalDate startDate;
	private LocalDate endDate;
	private String description;
}

