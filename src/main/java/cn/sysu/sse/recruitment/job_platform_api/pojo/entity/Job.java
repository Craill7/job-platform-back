package cn.sysu.sse.recruitment.job_platform_api.pojo.entity;

import cn.sysu.sse.recruitment.job_platform_api.common.enums.JobStatus;
import cn.sysu.sse.recruitment.job_platform_api.common.enums.WorkNature;
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
	private String techRequirements;
	private Integer minSalary;
	private Integer maxSalary;
	private Integer provinceId;
	private Integer cityId;
	private String addressDetail;
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

	public String getLocation() {
		return this.addressDetail;
	}

	public void setLocation(String location) {
		this.addressDetail = location;
	}
}
