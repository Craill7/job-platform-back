package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * 企业信息选项 VO
 */
@Data
public class CompanyOptionsVO {
	private List<String> industries;
	private List<String> natures;
	private List<String> scales;
}
