package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.CompanyDictionaryDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Company;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.CompanyExternalLink;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Job;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.User;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.CompanyDetailVO;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.ApplicationMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.CompanyDictionaryMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.CompanyExternalLinkMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.CompanyMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.JobMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.UserMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.service.CompanyCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 企业中心服务实现（学生端）
 */
@Service
public class CompanyCenterServiceImpl implements CompanyCenterService {

	private static final Logger logger = LoggerFactory.getLogger(CompanyCenterServiceImpl.class);

	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private CompanyDictionaryMapper companyDictionaryMapper;

	@Autowired
	private CompanyExternalLinkMapper companyExternalLinkMapper;

	@Autowired
	private JobMapper jobMapper;

	@Autowired
	private ApplicationMapper applicationMapper;

	@Autowired
	private UserMapper userMapper;

	@Override
	public CompanyDetailVO getCompanyDetail(Integer companyId) {
		logger.info("获取企业详情，企业ID：{}", companyId);

		// 查询企业基本信息
		Optional<Company> companyOpt = companyMapper.findById(companyId);
		if (companyOpt.isEmpty()) {
			throw new BusinessException(ErrorCode.NOT_FOUND, "企业不存在");
		}
		Company company = companyOpt.get();

		// 构建响应VO
		CompanyDetailVO vo = new CompanyDetailVO();
		vo.setCompanyId(company.getCompanyId());
		vo.setCompanyName(company.getCompanyName());
		vo.setCompanyAddress(company.getCompanyAddress());
		vo.setCompanyLogoUrl(company.getLogoUrl());
		vo.setContactPersonName(company.getContactPersonName());
		vo.setContactPersonPhone(company.getContactPersonPhone());
		vo.setDescription(company.getDescription());

		// 查询企业字典信息（行业、性质、规模）
		Optional<CompanyDictionaryDTO> dictOpt = companyDictionaryMapper.findByCompanyId(companyId);
		if (dictOpt.isPresent()) {
			CompanyDictionaryDTO dict = dictOpt.get();
			vo.setCompanyIndustry(dict.getIndustryName());
			vo.setCompanyNature(dict.getNatureName());
			vo.setCompanyScale(dict.getCompanyScale());
		}

		// 查询企业官网链接
		List<CompanyExternalLink> externalLinks = companyExternalLinkMapper.listByCompanyId(companyId);


		// 构建统计信息
		CompanyDetailVO.StatisticsVO statistics = new CompanyDetailVO.StatisticsVO();

		// 在招岗位数量
		long activePositions = jobMapper.countApprovedJobsByCompany(companyId);
		statistics.setActivePositions(Math.toIntExact(activePositions));

		// 简历处理率 = 已处理投递简历数量 / 总投递数
		long totalApplications = applicationMapper.countByCompany(companyId);
		long processedApplications = applicationMapper.countProcessedByCompany(companyId);
		String resumeProcessRate = calculateResumeProcessRate(processedApplications, totalApplications);
		statistics.setResumeProcessRate(resumeProcessRate);

		// 最近活跃时间（根据企业用户的最近登录时间）
		String recentActivity = calculateRecentActivity(company.getUserId());
		statistics.setRecentActivity(recentActivity);

		vo.setStatistics(statistics);

		// 查询企业的其他岗位（最多返回前几个）
		List<Job> otherJobs = jobMapper.findOtherJobsByCompany(companyId, null, 10);
		List<CompanyDetailVO.OtherJobVO> otherJobList = otherJobs.stream()
				.map(job -> {
					CompanyDetailVO.OtherJobVO otherJobVO = new CompanyDetailVO.OtherJobVO();
					otherJobVO.setJobId(job.getId());
					otherJobVO.setJobTitle(job.getTitle());
					otherJobVO.setPostedAt(job.getCreatedAt() != null ? job.getCreatedAt().toLocalDate() : null);
					return otherJobVO;
				})
				.collect(Collectors.toList());
		vo.setOtherJobs(otherJobList);

		return vo;
	}

	/**
	 * 计算简历处理率
	 * @param processedCount 已处理数量
	 * @param totalCount 总数量
	 * @return 处理率字符串，格式：70%
	 */
	private String calculateResumeProcessRate(long processedCount, long totalCount) {
		if (totalCount == 0) {
			return "0%";
		}
		BigDecimal rate = BigDecimal.valueOf((double) processedCount / totalCount)
				.multiply(BigDecimal.valueOf(100))
				.setScale(0, RoundingMode.HALF_UP);
		return rate.intValue() + "%";
	}

	/**
	 * 计算最近活跃时间
	 * @param userId 企业用户ID
	 * @return 相对时间字符串，格式：2天前、3小时前、刚刚等
	 */
	private String calculateRecentActivity(Integer userId) {
		if (userId == null) {
			return "未知";
		}

		Optional<User> userOpt = userMapper.findById(userId);
		if (userOpt.isEmpty() || userOpt.get().getLastLoginAt() == null) {
			return "未知";
		}

		LocalDateTime lastLoginAt = userOpt.get().getLastLoginAt();
		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.between(lastLoginAt, now);

		long days = duration.toDays();
		if (days > 0) {
			return days + "天前";
		}

		long hours = duration.toHours();
		if (hours > 0) {
			return hours + "小时前";
		}

		long minutes = duration.toMinutes();
		if (minutes > 0) {
			return minutes + "分钟前";
		}

		return "刚刚";
	}
}

