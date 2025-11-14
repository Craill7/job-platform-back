package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;

@Data
public class JobListItemVO {
	private Integer jobId;
	private String title;
	private String companyName;
	private String salaryRange;
	private String location;
	private String workNature;
	private String type;
	private String department;
	private Integer headcount;
	private Boolean isFavorited;
	private String logoUrl;
}

