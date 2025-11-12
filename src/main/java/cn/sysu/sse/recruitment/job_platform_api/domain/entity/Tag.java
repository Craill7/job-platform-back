package cn.sysu.sse.recruitment.job_platform_api.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Tag {
	private Integer id;
	private String name;
	private LocalDateTime createdAt;
	private Integer categoryId;
}


