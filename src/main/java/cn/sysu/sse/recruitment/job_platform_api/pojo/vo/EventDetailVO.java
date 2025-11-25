package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EventDetailVO {
    @JsonProperty("event_id")
    private Long eventId;

    @JsonProperty("event_title")
    private String eventTitle;

    @JsonProperty("event_start_time")
    private String eventStartTime; // 格式化后的时间字符串

    @JsonProperty("event_end_time")
    private String eventEndTime;

    @JsonProperty("event_location")
    private String eventLocation;

    @JsonProperty("event_type")
    private String eventType;

    @JsonProperty("event_target_audience")
    private String eventTargetAudience; // 转换后的中文描述


    @JsonProperty("event_summary")
    private String eventSummary;
}