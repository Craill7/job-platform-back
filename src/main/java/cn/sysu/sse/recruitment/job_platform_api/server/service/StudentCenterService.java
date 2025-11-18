
package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.StudentResumePreviewVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.StudentWelcomeCardVO;

public interface StudentCenterService {

    /**
     * 获取学生欢迎信息
     */
    StudentWelcomeCardVO getWelcomeInfo(Integer userId);

    /**
     * 获取简历预览信息
     */
    StudentResumePreviewVO getResumePreview(Integer userId);
}