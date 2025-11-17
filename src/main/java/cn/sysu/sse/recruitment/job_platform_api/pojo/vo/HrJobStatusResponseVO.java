package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HrJobStatusResponseVO {

    @JsonProperty("job_id")
    private Integer jobId;

    private String status;
}
