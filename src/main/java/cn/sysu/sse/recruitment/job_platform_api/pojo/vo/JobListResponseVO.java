package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class JobListResponseVO {
	private Long total;
	private Integer page;

	@JsonProperty("page_size")
	private Integer pageSize;

	private List<JobListItemVO> jobs;
}