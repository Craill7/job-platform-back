package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HrJobCreateResponseVO {

	@JsonProperty("new_job")
	private NewJob newJob;

	@Data
	public static class NewJob {

		@JsonProperty("job_id")
		private Integer jobId;

		private String title;

		private String status;
	}
}
