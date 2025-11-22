package cn.sysu.sse.recruitment.job_platform_api.server.controller.student;

import cn.sysu.sse.recruitment.job_platform_api.common.result.ApiResponse;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.*;
import cn.sysu.sse.recruitment.job_platform_api.server.service.StudentDashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * 学生主页控制器
 */
@RestController
public class StudentDashboardController {

	private static final Logger logger = LoggerFactory.getLogger(StudentDashboardController.class);

	@Autowired
	private StudentDashboardService studentDashboardService;

	/**
	 * 获取当前用户名
	 * @param authentication 当前登录用户认证信息
	 * @return 用户信息
	 */
	@GetMapping("/student/me/")
	public ApiResponse<StudentMeVO> getStudentMe(Authentication authentication) {
		logger.info("收到获取当前用户信息请求");
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		StudentMeVO result = studentDashboardService.getStudentMe(studentUserId);
		return ApiResponse.of(200, "获取用户信息成功", result);
	}

	/**
	 * 获取求职日历数据
	 * @param month 要查询的月份 (YYYY-MM)，若不传，默认当前月
	 * @param authentication 当前登录用户认证信息
	 * @return 日历数据
	 */
	@GetMapping("/student/calendar")
	public ApiResponse<CalendarVO> getCalendar(
			@RequestParam(required = false) String month,
			Authentication authentication) {
		logger.info("收到获取日历数据请求，月份：{}", month);
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		CalendarVO result = studentDashboardService.getCalendar(month);
		return ApiResponse.of(200, "获取日历数据成功", result);
	}

	/**
	 * 获取近期求职活动
	 * @param limit 返回条目上限
	 * @param authentication 当前登录用户认证信息
	 * @return 近期活动列表
	 */
	@GetMapping("/events/upcoming")
	public ApiResponse<UpcomingEventsVO> getUpcomingEvents(
			@RequestParam(required = false, defaultValue = "5") Integer limit,
			Authentication authentication) {
		logger.info("收到获取近期活动请求，限制数量：{}", limit);
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		UpcomingEventsVO result = studentDashboardService.getUpcomingEvents(limit);
		return ApiResponse.of(200, "获取近期活动成功", result);
	}

	/**
	 * 获取岗位热度排行榜
	 * @param limit 返回条目上限
	 * @param authentication 当前登录用户认证信息
	 * @return 热度排行榜
	 */
	@GetMapping("/jobs/ranked")
	public ApiResponse<RankedJobsVO> getRankedJobs(
			@RequestParam(required = false, defaultValue = "5") Integer limit,
			Authentication authentication) {
		logger.info("收到获取岗位热度排行请求，限制数量：{}", limit);
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		RankedJobsVO result = studentDashboardService.getRankedJobs(limit);
		return ApiResponse.of(200, "获取热度排行成功", result);
	}

	/**
	 * 获取近期招聘信息列表
	 * @param jobTypeFilter 招聘类型筛选（企业招聘、事业单位招聘、实习招聘）
	 * @param limit 返回条目上限
	 * @param authentication 当前登录用户认证信息
	 * @return 近期招聘列表
	 */
	@GetMapping("/jobs/recent")
	public ApiResponse<RecentJobsVO> getRecentJobs(
			@RequestParam(name = "job_type_filter", required = false) String jobTypeFilter,
			@RequestParam(required = false, defaultValue = "10") Integer limit,
			Authentication authentication) {
		logger.info("收到获取近期招聘信息请求，类型筛选：{}，限制数量：{}", jobTypeFilter, limit);
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		RecentJobsVO result = studentDashboardService.getRecentJobs(jobTypeFilter, limit);
		return ApiResponse.of(200, "获取近期招聘列表成功", result);
	}

	/**
	 * 根据投递记录ID获取详情（自动从token识别学生身份）
	 * @param id 投递记录ID
	 * @param authentication 当前登录用户认证信息
	 * @return 投递详情
	 */
	@GetMapping("/student/applications/{id}")
	public ApiResponse<ApplicationDetailVO> getApplicationDetail(
			@PathVariable("id") Integer id,
			Authentication authentication) {
		logger.info("收到获取投递详情请求，投递ID：{}", id);
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		ApplicationDetailVO result = studentDashboardService.getApplicationDetail(id, studentUserId);
		return ApiResponse.of(200, "获取投递详情成功", result);
	}

	/**
	 * 从Authentication中获取学生用户ID
	 * @param authentication 认证信息
	 * @return 学生用户ID，如果未登录或不是学生则返回null
	 */
	private Integer getStudentUserId(Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		
		try {
			// CustomUserDetails的getUsername()返回的是用户ID的字符串形式
			if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails) {
				org.springframework.security.core.userdetails.UserDetails userDetails = 
						(org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();
				String userIdStr = userDetails.getUsername();
				return Integer.parseInt(userIdStr);
			}
			// 备用方案：从name中获取
			String userIdStr = authentication.getName();
			return Integer.parseInt(userIdStr);
		} catch (Exception e) {
			logger.warn("获取用户ID失败", e);
			return null;
		}
	}
}

