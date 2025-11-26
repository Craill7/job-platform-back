package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 承载大模型返回的原始 JSON 数据。所有字段均保持文本/基础类型，避免直接与业务耦合。
 */
@Data
public class LlmJobExtractionDTO {

    private String title;

    private String description;

    @JsonProperty("tech_requirements")
    private String techRequirements;

    @JsonProperty("bonus_points")
    private String bonusPoints;

    @JsonProperty("min_salary")
    private Integer minSalary;

    @JsonProperty("max_salary")
    private Integer maxSalary;

    @JsonProperty("province_name")
    private String provinceName;

    @JsonProperty("city_name")
    private String cityName;

    @JsonProperty("address_detail")
    private String addressDetail;

    @JsonProperty("work_nature_text")
    private String workNatureText;

    private String department;

    private Integer headcount;

    @JsonProperty("category_keyword")
    private String categoryKeyword;

    @JsonProperty("degree_text")
    private String degreeText;

    @JsonProperty("required_start_date")
    private String requiredStartDate;

    private String deadline;
}
