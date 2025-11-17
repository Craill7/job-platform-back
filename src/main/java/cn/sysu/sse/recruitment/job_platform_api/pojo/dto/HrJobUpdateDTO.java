package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class HrJobUpdateDTO {

	@Size(max = 255, message = "岗位标题长度不能超过255个字符")
	@JsonProperty("title")
	private String title;

	@Size(max = 100, message = "所属部门长度不能超过100个字符")
	@JsonProperty("department")
	private String department;

	@JsonProperty("work_nature")
	private String workNature;

	@JsonProperty("type")
	private Integer type;

	@Min(value = 1, message = "招聘人数需大于0")
	@JsonProperty("headcount")
	private Integer headcount;

	@JsonProperty("min_salary")
	@Min(value = 0, message = "最低薪资不能为负数")
	private Integer minSalary;

	@JsonProperty("max_salary")
	@Min(value = 0, message = "最高薪资不能为负数")
	private Integer maxSalary;

	@JsonProperty("salary_range")
	private String salaryRange;

	@JsonProperty("province_id")
	private Integer provinceId;

	@JsonProperty("city_id")
	private Integer cityId;

	@Size(max = 255, message = "详细地址长度不能超过255个字符")
	@JsonProperty("address_detail")
	private String addressDetail;

	@JsonProperty("location")
	private String legacyLocation;

	@JsonProperty("required_degree")
	private Integer requiredDegree;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty("required_start_date")
	private LocalDate requiredStartDate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty("deadline")
	private LocalDate deadline;

	@JsonProperty("description")
	private String description;

	@JsonProperty("tech_requirements")
	private String techRequirements;

	@JsonProperty("bonus_points")
	private String bonusPoints;

	@JsonProperty("status")
	private String status;

	@JsonProperty("tags")
	private List<Integer> tags;

	@JsonProperty("work_address")
	private String workAddress;
}
