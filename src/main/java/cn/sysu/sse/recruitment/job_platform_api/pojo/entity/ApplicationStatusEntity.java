package cn.sysu.sse.recruitment.job_platform_api.pojo.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 投递状态字典实体
 */
@Data
public class ApplicationStatusEntity {
	private Integer id;
	private Integer code;
	private String name;
	private String detail;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}

