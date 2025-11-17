package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HrJobUpdateResponseVO {

	@JsonProperty("updated_job")
	private UpdatedJob updatedJob;

	@Data
	public static class UpdatedJob {
		@JsonProperty("job_id")
		private Integer jobId;

		@JsonProperty("title")
		private String title;

		@JsonProperty("status")
		private String status;
	}
}
