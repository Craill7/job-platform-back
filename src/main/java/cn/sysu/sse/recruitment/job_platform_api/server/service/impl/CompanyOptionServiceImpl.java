package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.CompanyOptionsVO;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.CompanyDictionaryMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.service.CompanyOptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 企业信息选项查询实现
 */
@Service
public class CompanyOptionServiceImpl implements CompanyOptionService {

	private static final Logger logger = LoggerFactory.getLogger(CompanyOptionServiceImpl.class);

	@Autowired
	private CompanyDictionaryMapper companyDictionaryMapper;

	@Override
	public CompanyOptionsVO getCompanyOptions() {
		logger.info("获取企业信息选项数据");
		CompanyOptionsVO vo = new CompanyOptionsVO();
		vo.setIndustries(companyDictionaryMapper.listIndustries());
		vo.setNatures(companyDictionaryMapper.listCompanyNatures());
		vo.setScales(companyDictionaryMapper.listCompanyScales());
		return vo;
	}
}
