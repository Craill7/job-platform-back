package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobCreateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobListQueryDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobCreateResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobDetailResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobListResponseVO;

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
}
