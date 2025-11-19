package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 投递详情响应VO
 */
@Data
public class ApplicationDetailVO {
	/**
	 * 投递记录ID
	 */
	private Integer id;
	
	/**
	 * 投递状态（中文）
	 */
	private String status;
	
	/**
	 * 状态详情描述
	 */
	@JsonProperty("status_detail")
	private String statusDetail;
	
	/**
	 * 投递时间
	 */
	@JsonProperty("submitted_at")
	private LocalDateTime submittedAt;
	
	/**
	 * 状态最后更新时间
	 */
	@JsonProperty("updated_at")
	private LocalDateTime updatedAt;
	
	/**
	 * 岗位信息
	 */
	private JobInfo job;
	
	/**
	 * 公司信息
	 */
	private CompanyInfo company;
	
	/**
	 * 岗位信息内部类
	 */
	@Data
	public static class JobInfo {
		private Integer id;
		private String title;
	}
	
	/**
	 * 公司信息内部类
	 */
	@Data
	public static class CompanyInfo {
		private String name;
	}
}

