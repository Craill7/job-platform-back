package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.ApplicationSubmitDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.JobListQueryDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.*;

import java.util.List;

/**
 * 求职中心服务接口
 */
public interface PositionCenterService {
	
	/**
	 * 获取岗位列表（支持筛选和分页）
	 * @param queryDTO 查询参数
	 * @param studentUserId 当前登录学生ID（可为null）
	 * @return 岗位列表响应
	 */
	JobListResponseVO getJobList(JobListQueryDTO queryDTO, Integer studentUserId);
	
	/**
	 * 获取用户收藏的岗位列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @param studentUserId 当前登录学生ID
	 * @return 岗位列表响应
	 */
	JobListResponseVO getFavoriteJobs(Integer page, Integer pageSize, Integer studentUserId);
	
	/**
	 * 获取职位详情
	 * @param jobId 职位ID
	 * @param studentUserId 当前登录学生ID（可为null）
	 * @return 职位详情
	 */
	JobDetailVO getJobDetail(Integer jobId, Integer studentUserId);
	
	/**
	 * 收藏岗位
	 * @param jobId 岗位ID
	 * @param studentUserId 当前登录学生ID
	 */
	void favoriteJob(Integer jobId, Integer studentUserId);
	
	/**
	 * 取消收藏岗位
	 * @param jobId 岗位ID
	 * @param studentUserId 当前登录学生ID
	 */
	void unfavoriteJob(Integer jobId, Integer studentUserId);
	
	/**
	 * 获取收藏状态
	 * @param jobId 岗位ID
	 * @param studentUserId 当前登录学生ID
	 * @return 收藏状态
	 */
	FavoriteStatusVO getFavoriteStatus(Integer jobId, Integer studentUserId);
	
	/**
	 * 岗位投递
	 * @param submitDTO 投递请求
	 * @param studentUserId 当前登录学生ID
	 * @return 投递结果
	 */
	ApplicationSubmitVO submitApplication(ApplicationSubmitDTO submitDTO, Integer studentUserId);
	
	/**
	 * 获取简历文件列表
	 * 注意：此方法应复用resume-center的逻辑，这里提供基本实现
	 * @param studentUserId 当前登录学生ID
	 * @return 简历文件列表
	 */
	List<ResumeFileVO> getResumeFiles(Integer studentUserId);
}

