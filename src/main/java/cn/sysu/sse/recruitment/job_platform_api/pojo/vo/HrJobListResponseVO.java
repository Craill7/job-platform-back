package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import cn.sysu.sse.recruitment.job_platform_api.common.result.Pagination;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HrJobListResponseVO {
	@JsonProperty("job_list")
	private List<JobSummary> jobList;
	@JsonProperty("pagination")
	private Pagination pagination;

	@Data
	public static class JobSummary {
		@JsonProperty("job_id")
		private Integer jobId;
		private String title;
		@JsonProperty("work_nature")
		private String workNature;
		private String status;
		@JsonProperty("updated_at")
		private LocalDateTime updatedAt;
		@JsonProperty("received_num")
		private Long receivedNum;
		@JsonProperty("no_review_num")
		private Long noReviewNum;
	}
}
