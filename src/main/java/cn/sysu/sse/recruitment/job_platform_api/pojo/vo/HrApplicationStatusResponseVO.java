package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * HR 人才状态更新响应 VO。
 */
@Data
public class HrApplicationStatusResponseVO {

    @JsonProperty("application_id")
    private Integer applicationId;

    @JsonProperty("status_code")
    private Integer statusCode;

    private String status;
}
