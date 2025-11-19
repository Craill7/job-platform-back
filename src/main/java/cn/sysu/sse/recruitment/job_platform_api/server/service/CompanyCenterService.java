package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.CompanyDetailVO;

/**
 * 企业中心服务（学生端）
 */
public interface CompanyCenterService {
	/**
	 * 获取企业详情
	 * @param companyId 企业ID
	 * @return 企业详情
	 */
	CompanyDetailVO getCompanyDetail(Integer companyId);
}

