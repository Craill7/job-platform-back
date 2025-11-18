package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JobListItemVO {
	@JsonProperty("job_id")
	private Integer jobId;

	private String title;

	@JsonProperty("company_name")
	private String companyName;

	@JsonProperty("salary_range")
	private String salaryRange;

	@JsonProperty("address")
	private String address;

	@JsonProperty("work_nature")
	private String workNature;

	@JsonProperty("type")
	private String type;

	private String department;

	@JsonProperty("headcount")
	private Integer headcount;

	@JsonProperty("is_favorited")
	private Boolean isFavorited;

	@JsonProperty("logo_url")
	private String logoUrl;
}
