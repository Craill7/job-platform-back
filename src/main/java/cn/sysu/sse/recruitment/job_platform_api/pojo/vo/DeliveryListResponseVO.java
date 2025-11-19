package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 已投递岗位列表响应 VO
 */
@Data
public class DeliveryListResponseVO {
	private Long total;
	private Integer page;
	
	@JsonProperty("page_size")
	private Integer pageSize;
	
	private List<DeliveryListItemVO> jobs;
}

