package cn.sysu.sse.recruitment.job_platform_api.server.controller.student;

import cn.sysu.sse.recruitment.job_platform_api.common.result.ApiResponse;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.ChangePasswordVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.StudentResumePreviewVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.StudentWelcomeCardVO;
import cn.sysu.sse.recruitment.job_platform_api.server.service.AuthService;
import cn.sysu.sse.recruitment.job_platform_api.server.service.StudentCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * 学生个人中心控制器
 */
@RestController
@RequestMapping("/api/student/me")
public class StudentCenterController {

    private static final Logger logger = LoggerFactory.getLogger(StudentCenterController.class);

    @Autowired
    private StudentCenterService studentCenterService;

    @Autowired
    private AuthService authService;

    /**
     * 获取学生欢迎信息
     */
    @GetMapping("/welcome")
    public ApiResponse<StudentWelcomeCardVO> getWelcomeInfo(Authentication authentication) {
        Integer userId = getUserId(authentication);
        if (userId == null) return ApiResponse.error(401, "未登录");

        logger.info("获取学生欢迎信息，userId={}", userId);
        StudentWelcomeCardVO vo = studentCenterService.getWelcomeInfo(userId);
        return ApiResponse.success(vo);
    }

    /**
     * 获取学生简历预览
     */
    @GetMapping("/resume-preview")
    public ApiResponse<StudentResumePreviewVO> getResumePreview(Authentication authentication) {
        Integer userId = getUserId(authentication);
        if (userId == null) return ApiResponse.error(401, "未登录");

        logger.info("获取简历预览信息，userId={}", userId);
        StudentResumePreviewVO vo = studentCenterService.getResumePreview(userId);
        return ApiResponse.success(vo);
    }

    /**
     * 修改账号密码
     * OpenAPI 定义中使用 query 参数传递 old_password 和 new_password
     */
    @PutMapping("/change-password")
    public ApiResponse<ChangePasswordVO> changePassword(
            @RequestParam("old_password") String oldPassword,
            @RequestParam("new_password") String newPassword,
            Authentication authentication) {

        Integer userId = getUserId(authentication);
        if (userId == null) return ApiResponse.error(401, "未登录");

        logger.info("学生修改密码，userId={}", userId);
        // 复用 AuthService 的逻辑
        ChangePasswordVO result = authService.changePasswordByUserId(userId, oldPassword, newPassword);

        if (result.getCode() == 200) {
            return ApiResponse.success(result);
        } else {
            return ApiResponse.error(result.getCode(), result.getMessage());
        }
    }

    // 辅助方法：获取用户ID
    private Integer getUserId(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) return null;
        try {
            return Integer.parseInt(authentication.getName());
        } catch (Exception e) {
            return null;
        }
    }
}