package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * HR 端投递详情（简历）响应 VO。
 */
@Data
public class HrApplicationResumeDetailVO {

    private Integer id;

    private String status;

    @JsonProperty("resume_url")
    private String resumeUrl;
}
