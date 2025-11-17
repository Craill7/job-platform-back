package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobCreateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobListQueryDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobCreateResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobListResponseVO;

public interface HrJobService {
	HrJobListResponseVO listCompanyJobs(Integer userId, HrJobListQueryDTO queryDTO);

	HrJobCreateResponseVO createJob(Integer userId, HrJobCreateDTO dto);
}
