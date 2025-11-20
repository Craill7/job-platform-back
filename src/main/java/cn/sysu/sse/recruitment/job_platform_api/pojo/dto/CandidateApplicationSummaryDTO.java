package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import cn.sysu.sse.recruitment.job_platform_api.common.enums.ApplicationStatus;
import lombok.Data;

/**
 * 岗位人才列表查询结果 DTO。
 */
@Data
public class CandidateApplicationSummaryDTO {
	private Integer applicationId;
	private String candidateName;
	private String avatarUrl;
	private Integer startYear;
	private Integer degreeLevel;
	private ApplicationStatus status;
}
