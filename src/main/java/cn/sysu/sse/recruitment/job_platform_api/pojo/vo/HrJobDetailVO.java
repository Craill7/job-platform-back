package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * HR 岗位详情响应 VO
 */
@Data
public class HrJobDetailVO {
    private Integer jobId;
    private String title;
    private Integer status;
    private String description;
    private String techRequirements;
    private String bonusPoints;
    private Integer minSalary;
    private Integer maxSalary;
    private Integer provinceId;
    private Integer cityId;
    private String addressDetail;
    private String workAddress;
    private Integer workNature;
    private String department;
    private Integer headcount;
    private Integer type;
    private Integer requiredDegree;
    private LocalDate requiredStartDate;
    private LocalDate deadline;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<JobTagVO> tags;

    /**
     * 岗位标签 VO
     */
    @Data
    public static class JobTagVO {
        private Integer tagId;
        private String tagName;
    }
}
