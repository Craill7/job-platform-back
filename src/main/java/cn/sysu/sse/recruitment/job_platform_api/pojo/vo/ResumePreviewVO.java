package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class ResumePreviewVO {
    /**
     * 头像链接
     */
    private String avatarUrl;

    /**
     * 基本信息模块
     */
    private BasicInfo basicInfo;

    /**
     * 主要教育经历模块
     */
    @JsonProperty("primary_education")
    private List<EducationBlock> educationExperiences;

    /**
     * 期望职位模块
     */
    private ExpectedJob expectedJob;

    /**
     * 个人标签列表
     */
    private List<PersonalTag> personalTags;

    // --- 内部类定义 ---

    @Data
    public static class BasicInfo {
        private String fullName;
        private String gender; // "男"/"女"
        private Integer age;   // 根据生日计算
        private String degreeLevel;
        private String jobSeekingStatus;
    }

    @Data
    public static class EducationBlock {
        private Long id;
        private String schoolName;
        private String degreeLevel; // "本科"/"硕士"
        private String major;
        private String startDate;   // YYYY-MM
        private String endDate;     // YYYY-MM
        private String majorRank;
    }

    @Data
    public static class ExpectedJob {
        private String expectedPosition;
        private Integer expectedMinSalary;
        private Integer expectedMaxSalary;
    }

    @Data
    public static class PersonalTag {
        private Integer tagId;
        private String name;
    }
}