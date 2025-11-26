package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 已投递岗位列表项 VO
 */
@Data
public class DeliveryListItemVO {
	private String title;
	private String department;
	
	@JsonProperty("job_id")
	private Integer jobId;

	@JsonProperty("application_id")
	private Integer applicationId;

	@JsonProperty("company_name")
	private String companyName;
	
	@JsonProperty("salary_range")
	private String salaryRange;
	
	private String address;
	
	@JsonProperty("work_nature")
	private String workNature;
	
	@JsonProperty("logo_url")
	private String logoUrl;
	
	@JsonProperty("submitted_at")
	private LocalDateTime submittedAt;
	
	private String status;

}

