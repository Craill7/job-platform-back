package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.CompanyProfileUpdateDTO;
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

	/**
	 * 更新企业信息
	 * @param userId 当前登录 HR 用户ID
	 * @param dto    更新内容
	 */
	void updateCompanyProfile(Integer userId, CompanyProfileUpdateDTO dto);
}
