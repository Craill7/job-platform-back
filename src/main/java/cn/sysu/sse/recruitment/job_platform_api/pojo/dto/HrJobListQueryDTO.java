package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import lombok.Data;

@Data
public class HrJobListQueryDTO {
	private Integer page = 1;
	private Integer pageSize = 10;
	private String titleKeyword;
	private String workNature;
	private String status;
}
