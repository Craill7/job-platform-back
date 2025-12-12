package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

/**
 * 学生个人档案详情 VO (用于编辑页回显)
 */
@Data
public class StudentProfileVO {
    private String avatarUrl;
    private BasicInfo basicInfo;

    @JsonProperty("primary_education")
    private List<Education> educationExperiences;

    private ExpectedJob expectedJob;
    private List<PersonalTag> personalTags;

    @Data
    public static class BasicInfo {
        private String fullName;
        private String gender; // "男" 或 "女"
        private String dateOfBirth; // YYYY-MM-DD
        private String jobSeekingStatus;
        private String email;
        private String phoneNumber;
        private String studentId;
    }

    @Data
    public static class Education {
        private Long id; // 新增：教育经历ID，用于更新区分
        private String schoolName;
        private String degreeLevel; // "本科"/"硕士"...
        private String major;
        private String startDate; // YYYY-MM
        private String endDate;   // YYYY-MM
        private String majorRank;
    }

    @Data
    public static class ExpectedJob {
        private String expectedPosition;
        @JsonProperty("expected_min_salary")
        private Integer expectedMinSalary;

        @JsonProperty("expected_max_salary")
        private Integer expectedMaxSalary;
    }

    @Data
    public static class PersonalTag {
        private Integer tagId;
        private String name;
    }
}