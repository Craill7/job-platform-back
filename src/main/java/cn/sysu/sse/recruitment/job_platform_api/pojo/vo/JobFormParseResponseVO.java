package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 前端回显的岗位表单智能解析结果。
 */
@Data
public class JobFormParseResponseVO {

    @JsonProperty("job_details")
    private JobDetailsVO jobDetails;

    @Data
    public static class JobDetailsVO {

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

        @JsonProperty("province_id")
        private Integer provinceId;

        @JsonProperty("province_name")
        private String provinceName;

        @JsonProperty("city_id")
        private Integer cityId;

        @JsonProperty("city_name")
        private String cityName;

        @JsonProperty("address_detail")
        private String addressDetail;

        @JsonProperty("work_nature")
        private Integer workNature;

        private String department;

        private Integer headcount;

        @JsonProperty("type")
        private Integer jobCategoryId;

        @JsonProperty("required_degree")
        private Integer requiredDegree;

        @JsonProperty("required_start_date")
        private String requiredStartDate;

        private String deadline;

    }
}
