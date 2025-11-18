
package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;
import java.util.List;

/**
 * 学生个人中心-欢迎信息 VO
 */
@Data
public class StudentWelcomeCardVO {
    private String fullName;
    private String schoolName;
    private String phoneNumber;
    private String email;
    private String lastLoginAt; // ISO 8601 格式字符串
    private List<PersonalTag> personalTags;

    @Data
    public static class PersonalTag {
        private Integer tagId;
        private String name;
    }
}