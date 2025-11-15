package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.CompanyOptionsVO;

/**
 * 企业信息选项服务
 */
public interface CompanyOptionService {
	/**
	 * 获取企业选项数据
	 * @return 企业信息选项
	 */
	CompanyOptionsVO getCompanyOptions();
}
