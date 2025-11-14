package cn.sysu.sse.recruitment.job_platform_api.pojo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Company {
	private Integer companyId;
	private Integer userId;
	private String companyName;
	private String description;
	private String logoUrl;
	private Integer industryId;
	private Integer natureId;
	private Integer companyScaleId;
	private String companyAddress;
	private String contactPersonName;
	private String contactPersonPhone;
	private LocalDateTime createdAt;
}

