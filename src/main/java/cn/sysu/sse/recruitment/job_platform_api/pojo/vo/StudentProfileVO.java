package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;
import java.util.List;

/**
 * 学生个人档案详情 VO (用于编辑页回显)
 */
@Data
public class StudentProfileVO {
    private String avatarUrl;
    private BasicInfo basicInfo;
    private PrimaryEducation primaryEducation;
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
    }

    @Data
    public static class PrimaryEducation {
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
        private String expectedSalary;
    }

    @Data
    public static class PersonalTag {
        private Integer tagId;
        private String name;
    }
}