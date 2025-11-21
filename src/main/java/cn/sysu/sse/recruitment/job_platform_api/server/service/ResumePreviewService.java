package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.ResumePreviewVO;

public interface ResumePreviewService {
    /**
     * 获取学生简历预览数据
     * @param studentUserId 学生用户ID
     * @return 聚合后的简历VO
     */
    ResumePreviewVO getStudentResume(Integer studentUserId);
}
