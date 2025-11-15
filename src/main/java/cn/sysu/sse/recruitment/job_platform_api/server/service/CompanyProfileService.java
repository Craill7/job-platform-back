package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.CompanyProfileVO;

/**
 * 企业资料服务
 */
public interface CompanyProfileService {
	/**
	 * 获取企业信息
	 * @param userId 当前登录 HR 用户ID
	 * @return 企业资料
	 */
	CompanyProfileVO getCompanyProfile(Integer userId);
}
