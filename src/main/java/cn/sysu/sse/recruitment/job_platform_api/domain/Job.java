package cn.sysu.sse.recruitment.job_platform_api.domain;

import cn.sysu.sse.recruitment.job_platform_api.domain.enums.JobStatus;
import cn.sysu.sse.recruitment.job_platform_api.domain.enums.WorkNature;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Job {
	private Integer id;
	private Integer companyId;
	private Integer postedByUserId;
	private String title;
	private String description;
	private Integer minSalary;
	private Integer maxSalary;
	private String location;
	private WorkNature workNature;
	private LocalDate deadline;
	private JobStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Integer type;
	private String department;
	private Integer headcount;
	private Integer requiredDegree;
	private LocalDate requiredStartDate;
	private String bonusPoints;
	private String workAddress;
}


