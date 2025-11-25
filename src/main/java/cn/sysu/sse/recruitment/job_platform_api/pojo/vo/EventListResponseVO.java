package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import cn.sysu.sse.recruitment.job_platform_api.common.result.Pagination;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class EventListResponseVO {
    private List<EventItem> events;
    private Pagination pagination;

    @Data
    public static class EventItem {
        @JsonProperty("event_id")
        private Long eventId;

        @JsonProperty("event_title")
        private String eventTitle;

        @JsonProperty("event_start_time")
        private String eventStartTime;

        @JsonProperty("event_location")
        private String eventLocation;


        @JsonProperty("event_summary")
        private String eventSummary;
    }
}