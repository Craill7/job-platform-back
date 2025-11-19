package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import cn.sysu.sse.recruitment.job_platform_api.common.enums.ApplicationStatus;
import cn.sysu.sse.recruitment.job_platform_api.common.enums.WorkNature;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 已投递岗位查询结果 DTO
 */
@Data
public class DeliveryJobDTO {
	// Application 字段
	private Integer applicationId;
	private ApplicationStatus status;
	private LocalDateTime submittedAt;
	
	// Job 字段
	private Integer jobId;
	private String jobTitle;
	private String department;
	private Integer minSalary;
	private Integer maxSalary;
	private Integer provinceId;
	private Integer cityId;
	private String addressDetail;
	private WorkNature workNature;
	
	// Company 字段
	private String companyName;
	private String logoUrl;
}

