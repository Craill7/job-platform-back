package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.*;

/**
 * 学生主页服务接口
 */
public interface StudentDashboardService {
	
	/**
	 * 获取当前学生用户信息
	 * @param studentUserId 学生用户ID
	 * @return 学生信息
	 */
	StudentMeVO getStudentMe(Integer studentUserId);
	
	/**
	 * 获取求职日历数据
	 * @param month 月份（YYYY-MM格式），如果为null则使用当前月
	 * @return 日历数据
	 */
	CalendarVO getCalendar(String month);
	
	/**
	 * 获取近期求职活动
	 * @param limit 返回数量限制
	 * @return 近期活动列表
	 */
	UpcomingEventsVO getUpcomingEvents(Integer limit);
	
	/**
	 * 获取岗位热度排行榜
	 * @param limit 返回数量限制
	 * @return 热度排行榜
	 */
	RankedJobsVO getRankedJobs(Integer limit);
	
	/**
	 * 获取近期招聘信息列表
	 * @param jobTypeFilter 招聘类型筛选（企业招聘、事业单位招聘、实习招聘）
	 * @param limit 返回数量限制
	 * @return 近期招聘列表
	 */
	RecentJobsVO getRecentJobs(String jobTypeFilter, Integer limit);
	
	/**
	 * 根据投递记录ID获取详情
	 * @param applicationId 投递记录ID
	 * @param studentUserId 学生用户ID
	 * @return 投递详情
	 */
	ApplicationDetailVO getApplicationDetail(Integer applicationId, Integer studentUserId);
}

