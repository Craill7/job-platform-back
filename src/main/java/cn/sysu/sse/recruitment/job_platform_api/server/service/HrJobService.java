package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobCreateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobListQueryDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobUpdateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobCreateResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobDetailResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobListResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobStatusResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobUpdateResponseVO;

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
}
