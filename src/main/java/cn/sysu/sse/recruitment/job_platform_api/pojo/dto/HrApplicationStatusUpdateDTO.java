package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * HR 人才状态更新请求 DTO。
 */
@Data
public class HrApplicationStatusUpdateDTO {

    @NotNull(message = "status 不能为空")
    @JsonProperty("status")
    private String status;
}
