package cn.sysu.sse.recruitment.job_platform_api.pojo.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Student {
	private Integer userId;
	private String studentId;
	private String avatarUrl;
	private String fullName;
	private String phoneNumber;
	private Integer gender; // 0=男, 1=女
	private LocalDate dateOfBirth;
	private Integer jobSeekingStatus; // 0=在校-暂不考虑, 1=在校-寻求实习, 2=应届-寻求实习, 3=应届-寻求校招
	private String expectedPosition;
	private Integer expectedMinSalary;
	private Integer expectedMaxSalary;
	private String skillsSummary;
	private Long currentTemplateId;
}

