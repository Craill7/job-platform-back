package cn.sysu.sse.recruitment.job_platform_api.domain;

import cn.sysu.sse.recruitment.job_platform_api.domain.enums.UserRole;
import cn.sysu.sse.recruitment.job_platform_api.domain.enums.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
	private Integer id;
	private String email;
	private String passwordHash;
	private UserRole role;
	private UserStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime lastLoginAt;
}


