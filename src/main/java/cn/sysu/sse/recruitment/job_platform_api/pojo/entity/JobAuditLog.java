package cn.sysu.sse.recruitment.job_platform_api.pojo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobAuditLog {
    private Long id;
    private Integer jobId;
    private Long operatorId;
    private String operatorContact;
    private Integer auditStatus;
    private String remark;
    private LocalDateTime createdAt;
}
