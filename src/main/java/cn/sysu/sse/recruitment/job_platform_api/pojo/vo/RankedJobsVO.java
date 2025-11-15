package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankedJobsVO {
	private List<RankedJobItemVO> rankedJobs;
}

