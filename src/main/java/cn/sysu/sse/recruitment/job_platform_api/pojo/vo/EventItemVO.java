package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventItemVO {
	private Integer eventId;
	private String eventTitle;
	private String eventStartTime;
	private String eventLocation;
}

