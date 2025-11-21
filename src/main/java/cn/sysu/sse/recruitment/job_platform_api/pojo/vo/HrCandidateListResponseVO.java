package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import cn.sysu.sse.recruitment.job_platform_api.common.result.Pagination;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 岗位下人才列表响应 VO。
 */
@Data
public class HrCandidateListResponseVO {

    @JsonProperty("candidate_list")
    private List<CandidateSummaryVO> candidateList;

    private Pagination pagination;

    @Data
    public static class CandidateSummaryVO {
        @JsonProperty("application_id")
        private Integer applicationId;

        @JsonProperty("candidate_name")
        private String candidateName;

        @JsonProperty("avatar_url")
        private String avatarUrl;

        private String grade;

        private String degree;

        @JsonProperty("resume_status")
        private String resumeStatus;
    }
}
