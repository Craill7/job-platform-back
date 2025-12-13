
package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;

/**
 * 学生个人中心-简历预览 VO
 */
@Data
public class StudentResumePreviewVO {
    private String fullName;
    private Integer age;
    private String graduationYear; // 例如 "27届应届生"
    private String degreeLevel;    // 顶部的学历展示
    private String expectedPosition;
    private String expectedSalary;
    private PrimaryEducation primaryEducation;

    @Data
    public static class PrimaryEducation {
        private Long id;
        private String schoolName;
        private String degreeLevel;
        private String major;
    }
}