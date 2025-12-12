package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

/**
 * 学生档案更新 DTO
 */
@Data
public class StudentProfileUpdateDTO {

    @Valid
    @NotNull
    @JsonProperty("basic_info")
    private BasicInfoDTO basicInfo;

    // 修改：改为教育经历列表
    @Valid
    @JsonProperty("primary_education")
    private List<EducationDTO> educationExperiences;

    @Valid
    @NotNull
    @JsonProperty("expected_job")
    private ExpectedJobDTO expectedJob;

    @JsonProperty("personal_tag_ids")
    private List<Integer> personalTagIds;

    @Data
    public static class BasicInfoDTO {
        private String fullName;
        private String gender;
        private String dateOfBirth;
        private String jobSeekingStatus;
        private String email;
        private String phoneNumber;
    }

    @Data
    public static class EducationDTO {
        private Long id; // 新增：如果有ID则是更新，无ID则是新增
        private String schoolName;
        private String degreeLevel;
        private String major;
        private String startDate;
        private String endDate;
        private String majorRank;
    }

    @Data
    public static class ExpectedJobDTO {
        private String expectedPosition;
        private String expectedSalary;
    }
}