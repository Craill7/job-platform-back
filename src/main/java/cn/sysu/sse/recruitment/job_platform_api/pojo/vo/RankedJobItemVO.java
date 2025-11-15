package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankedJobItemVO {
	private Integer rank;
	private Integer jobId;
	private String jobTitle;
	private String companyName;
	private String location;
}

