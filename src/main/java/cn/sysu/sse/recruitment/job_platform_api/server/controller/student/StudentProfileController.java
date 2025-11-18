package cn.sysu.sse.recruitment.job_platform_api.server.controller.student;

import cn.sysu.sse.recruitment.job_platform_api.common.result.ApiResponse;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.StudentProfileUpdateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.StudentProfileVO;
import cn.sysu.sse.recruitment.job_platform_api.server.service.StudentProfileService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 学生个人信息编辑控制器
 */
@RestController
public class StudentProfileController {

    private static final Logger logger = LoggerFactory.getLogger(StudentProfileController.class);

    @Autowired
    private StudentProfileService studentProfileService;

    /**
     * 获取我的个人档案（用于编辑页回显）
     */
    @GetMapping("/api/student/me/edit-profile")
    public ApiResponse<StudentProfileVO> getMyProfile(Authentication authentication) {
        Integer userId = getUserId(authentication);
        if (userId == null) return ApiResponse.error(401, "未登录");

        logger.info("获取个人档案回显，userId={}", userId);
        return ApiResponse.success(studentProfileService.getMyProfile(userId));
    }

    /**
     * 更新个人档案（基本信息+教育+期望+标签）
     */
    @PutMapping("/profile-center/profiles/me/base")
    public ApiResponse<Void> updateMyBaseProfile(
            @RequestBody @Valid StudentProfileUpdateDTO dto,
            Authentication authentication) {
        Integer userId = getUserId(authentication);
        if (userId == null) return ApiResponse.error(401, "未登录");

        logger.info("更新个人档案，userId={}", userId);
        studentProfileService.updateMyBaseProfile(userId, dto);
        return ApiResponse.success(null);
    }

    /**
     * 上传新头像
     */
    @PostMapping("/api/student/me/avatar")
    public ApiResponse<Map<String, String>> uploadAvatar(
            @RequestParam("avatar_file") MultipartFile file,
            Authentication authentication) {
        Integer userId = getUserId(authentication);
        if (userId == null) return ApiResponse.error(401, "未登录");

        logger.info("上传头像，userId={}", userId);
        String url = studentProfileService.uploadAvatar(userId, file);
        return ApiResponse.success(Map.of("new_avatar_url", url));
    }

    private Integer getUserId(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) return null;
        try {
            return Integer.parseInt(authentication.getName());
        } catch (Exception e) {
            return null;
        }
    }
}