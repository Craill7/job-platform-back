package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import cn.sysu.sse.recruitment.job_platform_api.server.handler.YearMonthDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
public class OrganizationExperienceDTO {
	@NotBlank(message = "组织名称不能为空")
	private String organizationName;
	
	private String role;
	
	@JsonDeserialize(using = YearMonthDeserializer.class)
	private LocalDate startDate;
	
	@JsonDeserialize(using = YearMonthDeserializer.class)
	private LocalDate endDate;
	
	private String description;
}

