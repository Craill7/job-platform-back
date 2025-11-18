package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.StudentProfileUpdateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.StudentProfileVO;
import org.springframework.web.multipart.MultipartFile;

public interface StudentProfileService {
    /**
     * 获取学生个人档案聚合信息
     */
    StudentProfileVO getMyProfile(Integer userId);

    /**
     * 更新学生基本档案
     */
    void updateMyBaseProfile(Integer userId, StudentProfileUpdateDTO dto);

    /**
     * 上传学生头像
     */
    String uploadAvatar(Integer userId, MultipartFile file);
}