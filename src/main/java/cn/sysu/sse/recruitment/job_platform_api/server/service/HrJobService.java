package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrApplicationStatusUpdateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobCreateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobListQueryDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobUpdateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.*;

public interface HrJobService {
	HrJobListResponseVO listCompanyJobs(Integer userId, HrJobListQueryDTO queryDTO);

	HrJobCreateResponseVO createJob(Integer userId, HrJobCreateDTO dto);

	/**
	 * 获取岗位详情
	 * @param userId HR用户ID
	 * @param jobId 岗位ID
	 * @return 岗位详情
	 */
	HrJobDetailResponseVO getJobDetail(Integer userId, Integer jobId);

	/**
	 * 删除草稿岗位
	 * @param userId HR用户ID
	 * @param jobId 岗位ID
	 */
	void deleteDraftJob(Integer userId, Integer jobId);

	/**
	 * 更新岗位信息
	 * @param userId HR用户ID
	 * @param jobId 岗位ID
	 * @param dto 更新内容
	 * @return 更新结果
	 */
	HrJobUpdateResponseVO updateJob(Integer userId, Integer jobId, HrJobUpdateDTO dto);

	/**
	 * 下线岗位
	 * @param userId HR用户ID
	 * @param jobId 岗位ID
	 * @return 岗位状态
	 */
	HrJobStatusResponseVO closeJob(Integer userId, Integer jobId);

	/**
	 * 获取岗位下的人才列表
	 */
	HrCandidateListResponseVO listCandidatesByJob(Integer userId,
	                                            Integer jobId,
	                                            String nameKeyword,
	                                            Integer status,
	                                            Integer page,
	                                            Integer pageSize);

	/**
	 * 获取候选人简历详情
	 * @param userId HR用户ID
	 * @param applicationId 投递记录ID
	 * @return 简历详情
	 */
	HrApplicationResumeDetailVO getApplicationResumeDetail(Integer userId, Integer applicationId);

	/**
	 * 更新候选人投递状态
	 * @param userId HR 用户 ID
	 * @param applicationId 投递记录 ID
	 * @param dto 状态更新请求
	 * @return 更新后的状态
	 */
	HrApplicationStatusResponseVO updateApplicationStatus(Integer userId, Integer applicationId, HrApplicationStatusUpdateDTO dto);

	/**
	 * 获取学生简历预览数据
	 * @param studentUserId 学生用户ID
	 * @return 聚合后的简历VO
	 */
	HrStudentResumeVO getStudentResume(Integer studentUserId);
}
