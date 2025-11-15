package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.CompanyDictionaryDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.CompanyProfileUpdateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Company;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.CompanyExternalLink;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.User;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.CompanyProfileVO;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.ApplicationMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.CompanyExternalLinkMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.CompanyDictionaryMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.CompanyMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.JobMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.UserMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.service.CompanyProfileService;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyProfileServiceImpl implements CompanyProfileService {

	private static final Logger logger = LoggerFactory.getLogger(CompanyProfileServiceImpl.class);

	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private JobMapper jobMapper;
	@Autowired
	private ApplicationMapper applicationMapper;
	@Autowired
	private CompanyExternalLinkMapper companyExternalLinkMapper;
	@Autowired
	private CompanyDictionaryMapper companyDictionaryMapper;
	@Autowired
	private UserMapper userMapper;

	/**
	 * 获取企业信息
	 * @param userId 当前登录 HR 用户ID
	 * @return 企业资料
	 */
	@Override
	public CompanyProfileVO getCompanyProfile(Integer userId) {
		logger.info("获取企业资料，userId={}", userId);
		if (userId == null) {
			throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户未登录");
		}

		Company company = companyMapper.findByUserId(userId)
				.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "未找到企业信息"));

		long openJobsCount = jobMapper.countApprovedJobsByCompany(company.getCompanyId());
		long totalApplications = applicationMapper.countByCompany(company.getCompanyId());
		long processedApplications = applicationMapper.countProcessedByCompany(company.getCompanyId());
		double resumeProcessRate = totalApplications == 0 ? 0D : BigDecimal
				.valueOf((double) processedApplications / totalApplications)
				.setScale(4, RoundingMode.HALF_UP)
				.doubleValue();

		Optional<User> userOpt = userMapper.findById(userId);

		CompanyProfileVO vo = new CompanyProfileVO();
		vo.setOpenJobsCount(openJobsCount);
		vo.setResumeProcessRate(resumeProcessRate);
		vo.setLastLoginAt(userOpt.map(User::getLastLoginAt).orElse(null));
		vo.setCompanyName(company.getCompanyName());
		vo.setDescription(company.getDescription());
		vo.setLogoUrl(company.getLogoUrl());
		CompanyDictionaryDTO dictionaryDTO = companyDictionaryMapper.findByCompanyId(company.getCompanyId()).orElse(null);
		if (dictionaryDTO != null) {
			vo.setIndustry(dictionaryDTO.getIndustryName());
			vo.setNature(dictionaryDTO.getNatureName());
			vo.setCompanyScale(dictionaryDTO.getCompanyScale());
		} else {
			// 兜底：若关联数据缺失，返回原始ID字符串，方便前端识别
			vo.setNature(formatDictionaryValue(company.getNatureId()));
			vo.setIndustry(formatDictionaryValue(company.getIndustryId()));
			vo.setCompanyScale(formatDictionaryValue(company.getCompanyScaleId()));
		}
		vo.setContactPersonName(company.getContactPersonName());
		vo.setContactPersonPhone(company.getContactPersonPhone());
		vo.setCompanyAddress(company.getCompanyAddress());

		List<CompanyExternalLink> links = companyExternalLinkMapper.listByCompanyId(company.getCompanyId());
		vo.setExternalLinks(links.stream().map(this::convertLink).collect(Collectors.toList()));
		return vo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateCompanyProfile(Integer userId, CompanyProfileUpdateDTO dto) {
		logger.info("更新企业资料，userId={}", userId);
		if (userId == null) {
			throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户未登录");
		}

		Company company = companyMapper.findByUserId(userId)
				.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "未找到企业信息"));

		Integer industryId = companyDictionaryMapper.findIndustryIdByName(dto.getIndustry())
				.orElseThrow(() -> new BusinessException(ErrorCode.BAD_REQUEST, "行业不存在"));
		Integer natureId = companyDictionaryMapper.findNatureIdByName(dto.getNature())
				.orElseThrow(() -> new BusinessException(ErrorCode.BAD_REQUEST, "企业性质不存在"));
		Integer scaleId = companyDictionaryMapper.findCompanyScaleIdByValue(dto.getCompanyScale())
				.orElseThrow(() -> new BusinessException(ErrorCode.BAD_REQUEST, "企业规模不存在"));

		company.setDescription(cleanHtml(dto.getDescription()));
		company.setCompanyAddress(cleanText(dto.getCompanyAddress()));
		company.setIndustryId(industryId);
		company.setNatureId(natureId);
		company.setCompanyScaleId(scaleId);
		company.setContactPersonName(cleanText(dto.getContactPersonName()));
		company.setContactPersonPhone(dto.getContactPersonPhone());
		company.setUserId(userId);

		int updated = companyMapper.updateProfile(company);
		if (updated == 0) {
			throw new BusinessException(ErrorCode.INTERNAL_ERROR, "更新企业信息失败");
		}

		companyExternalLinkMapper.deleteByCompanyId(company.getCompanyId());
		if (dto.getExternalLinks() != null && !dto.getExternalLinks().isEmpty()) {
			for (CompanyProfileUpdateDTO.ExternalLinkDTO linkDTO : dto.getExternalLinks()) {
				CompanyExternalLink link = new CompanyExternalLink();
				link.setCompanyId(company.getCompanyId());
				link.setLinkName(cleanText(linkDTO.getLinkName()));
				link.setLinkUrl(linkDTO.getLinkUrl().trim());
				companyExternalLinkMapper.insert(link);
			}
		}
	}

	private CompanyProfileVO.ExternalLink convertLink(CompanyExternalLink link) {
		CompanyProfileVO.ExternalLink vo = new CompanyProfileVO.ExternalLink();
		vo.setId(link.getId());
		vo.setLinkName(link.getLinkName());
		vo.setLinkUrl(link.getLinkUrl());
		return vo;
	}

	private String formatDictionaryValue(Integer value) {
		return value == null ? null : String.valueOf(value);
	}

	private String cleanHtml(String html) {
		return Jsoup.clean(html, Safelist.basic());
	}

	private String cleanText(String text) {
		return Jsoup.clean(text, Safelist.none());
	}
}
