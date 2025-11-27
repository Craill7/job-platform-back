package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.common.enums.ApplicationStatus;
import cn.sysu.sse.recruitment.job_platform_api.common.enums.JobStatus;
import cn.sysu.sse.recruitment.job_platform_api.common.enums.WorkNature;
import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.ApplicationSubmitDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.CompanyDictionaryDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.JobListQueryDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.*;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.*;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.*;
import cn.sysu.sse.recruitment.job_platform_api.server.service.PositionCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 求职中心服务实现
 */
@Service
public class PositionCenterServiceImpl implements PositionCenterService {

	private static final Logger logger = LoggerFactory.getLogger(PositionCenterServiceImpl.class);
	private static final Set<String> MUNICIPALITIES = Set.of("北京市", "上海市", "天津市", "重庆市");

	@Autowired
	private JobMapper jobMapper;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private TagMapper tagMapper;
	
	@Autowired
	private JobFavoriteMapper jobFavoriteMapper;
	
	@Autowired
	private ApplicationMapper applicationMapper;
	
	@Autowired
	private ResumeMapper resumeMapper;

	@Autowired
	private LocationMapper locationMapper;

	@Autowired
	private CompanyDictionaryMapper companyDictionaryMapper;

	@Autowired
	private JobCategoryMapper jobCategoryMapper;

	@Autowired
	private JobViewMapper jobViewMapper;

	@Autowired
	private CompanyExternalLinkMapper companyExternalLinkMapper;

	@Override
	public JobListResponseVO getJobList(JobListQueryDTO queryDTO, Integer studentUserId) {
		logger.info("获取岗位列表，查询参数：{}，学生ID：{}", queryDTO, studentUserId);
		
		// 处理标签查询
		List<Integer> tagIds = null;
		if (StringUtils.hasText(queryDTO.getTag())) {
			List<String> tagNames = Arrays.stream(queryDTO.getTag().split(","))
					.map(String::trim)
					.filter(StringUtils::hasText)
					.collect(Collectors.toList());
			if (!tagNames.isEmpty()) {
				List<Tag> tags = tagMapper.findByNameIn(tagNames);
				tagIds = tags.stream().map(Tag::getId).collect(Collectors.toList());
			}
		}
		
		// 处理工作性质
		String workNature = null;
		if (StringUtils.hasText(queryDTO.getWorkNature())) {
			// 将中文转换为枚举值
			if ("校招".equals(queryDTO.getWorkNature()) || "实习".equals(queryDTO.getWorkNature())) {
				workNature = "校招".equals(queryDTO.getWorkNature()) ? "2" : "1";
			} else {
				workNature = queryDTO.getWorkNature();
			}
		}
		
		// 处理岗位类别：将类别名称转换为ID
		if (StringUtils.hasText(queryDTO.getType())) {
			String typeParam = queryDTO.getType().trim();
			// 先尝试解析为数字ID
			try {
				Integer typeId = Integer.parseInt(typeParam);
				// 如果是数字，直接使用（MyBatis会自动转换）
				queryDTO.setType(String.valueOf(typeId));
			} catch (NumberFormatException e) {
				// 如果不是数字，按类别名称查找
				JobCategory category = jobCategoryMapper.findByName(typeParam);
				if (category != null) {
					// 将类别名称替换为ID，以便后续查询使用
					queryDTO.setType(String.valueOf(category.getId()));
				} else {
					// 如果找不到对应的类别，清空type参数，避免查询错误
					logger.warn("未找到类别名称：{}", typeParam);
					queryDTO.setType(null);
				}
			}
		}
		
		// 根据名称解析省市ID
		resolveLocationFilters(queryDTO);

		// 分页参数
		int page = queryDTO.getPage() != null && queryDTO.getPage() > 0 ? queryDTO.getPage() : 1;
		int pageSize = queryDTO.getPageSize() != null && queryDTO.getPageSize() > 0 ? queryDTO.getPageSize() : 10;
		int offset = (page - 1) * pageSize;
		
		// 查询岗位列表
		List<Job> jobs = jobMapper.searchJobs(
				queryDTO,
				tagIds,
				workNature,
				offset,
				pageSize
		);
		
		// 查询总数
		long total = jobMapper.countSearchJobs(
				queryDTO,
				tagIds,
				workNature
		);
		
		// 预加载省市名称
		Map<Integer, Province> provinceMap = loadProvinces(jobs);
		Map<Integer, City> cityMap = loadCities(jobs);

		Map<Integer, JobCategory> categoryMap = loadJobCategories(jobs);

		// 获取用户收藏的岗位ID列表
		Set<Integer> favoriteJobIds = new HashSet<>();
		if (studentUserId != null) {
			List<Integer> favIds = jobFavoriteMapper.findJobIdsByStudent(studentUserId);
			favoriteJobIds.addAll(favIds);
		}
		
		// 转换为VO
		List<JobListItemVO> jobList = jobs.stream().map(job -> {
			JobListItemVO vo = new JobListItemVO();
			vo.setJobId(job.getId());
			vo.setTitle(job.getTitle());
			
			// 查询公司信息
			Optional<Company> companyOpt = companyMapper.findById(job.getCompanyId());
			if (companyOpt.isPresent()) {
				Company company = companyOpt.get();
				vo.setCompanyName(company.getCompanyName());
				vo.setLogoUrl(company.getLogoUrl());
			}
			
			// 薪资范围
			if (job.getMinSalary() != null && job.getMaxSalary() != null) {
				vo.setSalaryRange(job.getMinSalary() + "-" + job.getMaxSalary());
			} else if (job.getMinSalary() != null) {
				vo.setSalaryRange(job.getMinSalary() + "及以上");
			} else {
				vo.setSalaryRange("面议");
			}
			
			vo.setAddress(buildDisplayAddress(job, provinceMap, cityMap));
			vo.setWorkNature(job.getWorkNature() != null ? 
					(job.getWorkNature() == WorkNature.INTERNHIP ? "实习" : "校招") : null);
			vo.setDepartment(job.getDepartment());
			vo.setType(resolveJobCategoryName(job, categoryMap));
			vo.setHeadcount(job.getHeadcount());
			vo.setIsFavorited(favoriteJobIds.contains(job.getId()));
			
			return vo;
		}).collect(Collectors.toList());
		
		JobListResponseVO response = new JobListResponseVO();
		response.setTotal(total);
		response.setPage(page);
		response.setPageSize(pageSize);
		response.setJobs(jobList);
		
		return response;
	}

	@Override
	public JobListResponseVO getFavoriteJobs(Integer page, Integer pageSize, Integer studentUserId) {
		logger.info("获取收藏岗位列表，页码：{}，每页数量：{}，学生ID：{}", page, pageSize, studentUserId);
		
		if (studentUserId == null) {
			throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户未登录");
		}
		
		int p = page != null && page > 0 ? page : 1;
		int ps = pageSize != null && pageSize > 0 ? pageSize : 10;
		int offset = (p - 1) * ps;
		
		List<Job> jobs = jobMapper.findFavoriteJobsByStudent(studentUserId, offset, ps);
		long total = jobMapper.countFavoriteJobsByStudent(studentUserId);
		
		// 预加载省市名称
		Map<Integer, Province> provinceMap = loadProvinces(jobs);
		Map<Integer, City> cityMap = loadCities(jobs);

		Map<Integer, JobCategory> categoryMap = loadJobCategories(jobs);

		// 转换为VO（所有都是收藏的）
		List<JobListItemVO> jobList = jobs.stream().map(job -> {
			JobListItemVO vo = new JobListItemVO();
			vo.setJobId(job.getId());
			vo.setTitle(job.getTitle());
			
			Optional<Company> companyOpt = companyMapper.findById(job.getCompanyId());
			if (companyOpt.isPresent()) {
				Company company = companyOpt.get();
				vo.setCompanyName(company.getCompanyName());
				vo.setLogoUrl(company.getLogoUrl());
			}
			
			if (job.getMinSalary() != null && job.getMaxSalary() != null) {
				vo.setSalaryRange(job.getMinSalary() + "-" + job.getMaxSalary());
			} else if (job.getMinSalary() != null) {
				vo.setSalaryRange(job.getMinSalary() + "及以上");
			} else {
				vo.setSalaryRange("面议");
			}
			
			vo.setAddress(buildDisplayAddress(job, provinceMap, cityMap));
			vo.setWorkNature(job.getWorkNature() != null ? 
					(job.getWorkNature() == WorkNature.INTERNHIP ? "实习" : "校招") : null);
			vo.setDepartment(job.getDepartment());
			vo.setType(resolveJobCategoryName(job, categoryMap));
			vo.setHeadcount(job.getHeadcount());
			vo.setIsFavorited(true); // 收藏列表中的都是已收藏
			
			return vo;
		}).collect(Collectors.toList());
		
		JobListResponseVO response = new JobListResponseVO();
		response.setTotal(total);
		response.setPage(p);
		response.setPageSize(ps);
		response.setJobs(jobList);
		
		return response;
	}

	@Override
	public JobDetailVO getJobDetail(Integer jobId, Integer studentUserId, HttpServletRequest request) {
		logger.info("获取职位详情，职位ID：{}，学生ID：{}", jobId, studentUserId);
		
		Optional<Job> jobOpt = jobMapper.findById(jobId);
		if (jobOpt.isEmpty()) {
			throw new BusinessException(ErrorCode.NOT_FOUND, "岗位不存在");
		}
		
		Job job = jobOpt.get();
		if (job.getStatus() != JobStatus.APPROVED) {
			throw new BusinessException(ErrorCode.FORBIDDEN, "岗位未发布或已关闭");
		}
		recordJobView(jobId, studentUserId, request);

		JobDetailVO vo = new JobDetailVO();
		vo.setJobId(job.getId());
		vo.setTitle(job.getTitle());
		
		// 薪资范围
		if (job.getMinSalary() != null && job.getMaxSalary() != null) {
			vo.setSalaryRange(job.getMinSalary() + "-" + job.getMaxSalary());
		} else if (job.getMinSalary() != null) {
			vo.setSalaryRange(job.getMinSalary() + "及以上");
		} else {
			vo.setSalaryRange("面议");
		}
		Map<Integer, Province> provinceMap = loadProvinces(Collections.singleton(job));
		Map<Integer, City> cityMap = loadCities(Collections.singleton(job));
		Map<Integer, JobCategory> categoryMap = loadJobCategories(Collections.singleton(job));
		vo.setAddress(buildDisplayAddress(job, provinceMap, cityMap));
		vo.setAddressDetail(resolveAddressDetail(job));
		vo.setWorkNature(job.getWorkNature() != null ?
				(job.getWorkNature() == WorkNature.INTERNHIP ? "实习" : "校招") : null);
		
		// 学历要求
		if (job.getRequiredDegree() != null) {
			String[] degrees = {"本科及以上", "硕士及以上", "博士及以上"};
			if (job.getRequiredDegree() >= 0 && job.getRequiredDegree() < degrees.length) {
				vo.setRequiredDegree(degrees[job.getRequiredDegree()]);
			}
		}
		
		vo.setRequiredStartDate(job.getRequiredStartDate());
		
		// 查询技能标签
		List<Tag> tags = tagMapper.findTagsByJobId(jobId);
		List<String> requiredSkills = tags.stream().map(Tag::getName).collect(Collectors.toList());
		vo.setRequiredSkills(requiredSkills);
		
		vo.setHeadcount(job.getHeadcount());
		vo.setType(resolveJobCategoryName(job, categoryMap));
		vo.setTimes(Math.toIntExact(jobViewMapper.countByJob(jobId)));
		vo.setPostedAt(job.getCreatedAt() != null ? job.getCreatedAt().toLocalDate() : null);
		vo.setPositionDescription(job.getDescription());
		vo.setPositionRequirements(job.getTechRequirements());
		
		// 解析加分项
		if (StringUtils.hasText(job.getBonusPoints())) {
			try {
				// 假设存储为JSON数组字符串，这里简化处理
				String bonusPointsStr = job.getBonusPoints();
				if (bonusPointsStr.startsWith("[") && bonusPointsStr.endsWith("]")) {
					bonusPointsStr = bonusPointsStr.substring(1, bonusPointsStr.length() - 1);
					List<String> bonusPoints = Arrays.stream(bonusPointsStr.split(","))
							.map(s -> s.trim().replace("\"", ""))
							.filter(StringUtils::hasText)
							.collect(Collectors.toList());
					vo.setBonusPoints(bonusPoints);
				} else {
					// 如果不是JSON格式，按换行符分割
					vo.setBonusPoints(Arrays.asList(bonusPointsStr.split("\n")));
				}
			} catch (Exception e) {
				logger.warn("解析加分项失败：{}", job.getBonusPoints(), e);
				vo.setBonusPoints(Collections.emptyList());
			}
		} else {
			vo.setBonusPoints(Collections.emptyList());
		}
		
		// 查询公司信息
		Optional<Company> companyOpt = companyMapper.findById(job.getCompanyId());
		if (companyOpt.isPresent()) {
			Company company = companyOpt.get();
			JobDetailVO.CompanyInfoVO companyInfo = new JobDetailVO.CompanyInfoVO();
			companyInfo.setCompanyId(company.getCompanyId());
			companyInfo.setCompanyLogoUrl(company.getLogoUrl());
			companyInfo.setCompanyName(company.getCompanyName());
			companyInfo.setContactPersonName(company.getContactPersonName());
			companyInfo.setContactPersonPhone(company.getContactPersonPhone());

			companyDictionaryMapper.findByCompanyId(company.getCompanyId()).ifPresent(dict -> {
				companyInfo.setCompanyIndustry(dict.getIndustryName());
				companyInfo.setCompanyNature(dict.getNatureName());
				companyInfo.setCompanyScale(dict.getCompanyScale());
			});

			// 查询企业所有外部链接
			List<CompanyExternalLink> externalLinks = companyExternalLinkMapper.listByCompanyId(company.getCompanyId());
			
			// 查找企业官网链接（用于向后兼容）
			Optional<CompanyExternalLink> websiteLink = externalLinks.stream()
					.filter(link -> {
						String linkName = link.getLinkName();
						return "企业官网".equals(linkName) 
								|| "官网".equals(linkName) 
								|| "website".equalsIgnoreCase(linkName)
								|| "企业网站".equals(linkName);
					})
					.findFirst();
			
			// 转换所有外部链接为VO
			List<JobDetailVO.CompanyLinkVO> linkVOList = externalLinks.stream()
					.map(link -> {
						JobDetailVO.CompanyLinkVO linkVO = new JobDetailVO.CompanyLinkVO();
						linkVO.setLinkName(link.getLinkName());
						linkVO.setLinkUrl(link.getLinkUrl());
						return linkVO;
					})
					.collect(Collectors.toList());
			companyInfo.setCompanyLinks(linkVOList);
			
			vo.setCompanyInfo(companyInfo);
		}
		
		// 查询收藏状态
		boolean isFavorited = false;
		if (studentUserId != null) {
			Optional<JobFavorite> favoriteOpt = jobFavoriteMapper.findByStudentAndJob(studentUserId, jobId);
			isFavorited = favoriteOpt.isPresent();
		}
		vo.setIsFavorited(isFavorited);
		
		return vo;
	}

	@Override
	public void favoriteJob(Integer jobId, Integer studentUserId) {
		logger.info("收藏岗位，职位ID：{}，学生ID：{}", jobId, studentUserId);
		
		if (studentUserId == null) {
			throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户未登录");
		}
		
		// 检查岗位是否存在
		Optional<Job> jobOpt = jobMapper.findById(jobId);
		if (jobOpt.isEmpty()) {
			throw new BusinessException(ErrorCode.NOT_FOUND, "岗位不存在");
		}
		
		// 检查是否已收藏
		Optional<JobFavorite> existingOpt = jobFavoriteMapper.findByStudentAndJob(studentUserId, jobId);
		if (existingOpt.isPresent()) {
			logger.info("岗位已收藏，职位ID：{}，学生ID：{}", jobId, studentUserId);
			return; // 已收藏，直接返回
		}
		
		// 添加收藏
		JobFavorite favorite = new JobFavorite();
		favorite.setStudentUserId(studentUserId);
		favorite.setJobId(jobId);
		jobFavoriteMapper.insert(favorite);
		logger.info("收藏成功，职位ID：{}，学生ID：{}", jobId, studentUserId);
	}

	@Override
	public void unfavoriteJob(Integer jobId, Integer studentUserId) {
		logger.info("取消收藏岗位，职位ID：{}，学生ID：{}", jobId, studentUserId);
		
		if (studentUserId == null) {
			throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户未登录");
		}
		
		jobFavoriteMapper.deleteByStudentAndJob(studentUserId, jobId);
		logger.info("取消收藏成功，职位ID：{}，学生ID：{}", jobId, studentUserId);
	}

	@Override
	public FavoriteStatusVO getFavoriteStatus(Integer jobId, Integer studentUserId) {
		logger.info("查询收藏状态，职位ID：{}，学生ID：{}", jobId, studentUserId);
		
		FavoriteStatusVO vo = new FavoriteStatusVO();
		vo.setJobId(jobId);
		
		if (studentUserId == null) {
			vo.setIsFavorited(false);
			return vo;
		}
		
		Optional<JobFavorite> favoriteOpt = jobFavoriteMapper.findByStudentAndJob(studentUserId, jobId);
		vo.setIsFavorited(favoriteOpt.isPresent());
		
		return vo;
	}

	@Override
	public ApplicationSubmitVO submitApplication(ApplicationSubmitDTO submitDTO, Integer studentUserId) {
		logger.info("岗位投递，职位ID：{}，简历ID：{}，学生ID：{}", 
				submitDTO.getJobId(), submitDTO.getResumeId(), studentUserId);
		
		if (studentUserId == null) {
			throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户未登录");
		}
		
		// 检查岗位是否存在
		Optional<Job> jobOpt = jobMapper.findById(submitDTO.getJobId());
		if (jobOpt.isEmpty()) {
			throw new BusinessException(ErrorCode.NOT_FOUND, "岗位不存在");
		}
		
		Job job = jobOpt.get();
		if (job.getStatus() != JobStatus.APPROVED) {
			throw new BusinessException(ErrorCode.FORBIDDEN, "岗位未发布或已关闭");
		}
		
		// 检查是否已投递
		Optional<Application> existingOpt = applicationMapper.findByJobAndStudent(
				submitDTO.getJobId(), studentUserId);
		if (existingOpt.isPresent()) {
			throw new BusinessException(ErrorCode.BAD_REQUEST, "您已投递过该岗位");
		}
		
		// 创建投递记录
		Application application = new Application();
		application.setJobId(submitDTO.getJobId());
		application.setStudentUserId(studentUserId);
		application.setResumeId(Long.parseLong(submitDTO.getResumeId()));
		application.setStatus(ApplicationStatus.SUBMITTED);
		
		// 设置投递时间和更新时间
		LocalDateTime now = LocalDateTime.now();
		application.setSubmittedAt(now);
		application.setUpdatedAt(now);
		
		applicationMapper.insert(application);
		
		// 返回结果
		ApplicationSubmitVO vo = new ApplicationSubmitVO();
		vo.setApplicationId(application.getId());
		vo.setJobId(application.getJobId());
		vo.setStudentUserId(application.getStudentUserId());
		vo.setResumeId(submitDTO.getResumeId());
		vo.setStatus(application.getStatus().getCode());
		vo.setSubmittedAt(application.getSubmittedAt());
		vo.setUpdatedAt(application.getUpdatedAt());
		
		logger.info("岗位投递成功，投递记录ID：{}", application.getId());
		return vo;
	}

	@Override
	public List<ResumeFileVO> getResumeFiles(Integer studentUserId) {
		logger.info("获取简历文件列表，学生ID：{}", studentUserId);
		
		// TODO: 这里应该调用resume-center的Service，暂时直接实现基本功能
		// 实际应该复用 /resume-center/resume_files 接口逻辑
		List<Resume> resumes = resumeMapper.findByStudentUserId(studentUserId);
		
		return resumes.stream().map(resume -> {
			ResumeFileVO vo = new ResumeFileVO();
			vo.setId(resume.getId().intValue());
			vo.setFileName(resume.getFileName());
			vo.setFileUrl(resume.getFileUrl());
			vo.setFileSize(resume.getFileSize() != null ? resume.getFileSize().intValue() : null);
			vo.setTemplateId(resume.getTemplateId());
			vo.setUsage(resume.getUsageType());
			vo.setUploadedAt(resume.getUploadedAt());
			return vo;
		}).collect(Collectors.toList());
	}

	private String resolveJobAddress(Job job) {
		if (job == null) {
			return null;
		}
		if (StringUtils.hasText(job.getAddressDetail())) {
			return job.getAddressDetail();
		}
		return null;
	}

	private String resolveAddressDetail(Job job) {
		if (job == null) {
			return null;
		}
		if (StringUtils.hasText(job.getAddressDetail())) {
			return job.getAddressDetail();
		}
		return resolveJobAddress(job);
	}

	private void resolveLocationFilters(JobListQueryDTO queryDTO) {
		if (queryDTO == null) {
			return;
		}

		// 处理省份：如果提供了省份名称，尝试转换为ID
		if (queryDTO.getProvinceId() == null && StringUtils.hasText(queryDTO.getProvince())) {
			locationMapper.findProvinceByKeyword(queryDTO.getProvince().trim())
					.ifPresent(province -> {
						queryDTO.setProvinceId(province.getId());
						// 成功解析出ID后，清空名称字段，避免SQL查询时重复条件
						queryDTO.setProvince(null);
					});
		}

		// 处理城市：如果提供了城市名称，尝试转换为ID
		if (StringUtils.hasText(queryDTO.getCity()) && queryDTO.getCityId() == null) {
			Integer provinceId = queryDTO.getProvinceId();
			locationMapper.findCityByKeyword(queryDTO.getCity().trim(), provinceId)
					.ifPresent(city -> {
						queryDTO.setCityId(city.getId());
						// 成功解析出ID后，清空名称字段，避免SQL查询时重复条件
						queryDTO.setCity(null);
						// 如果省份ID为空，从城市信息中获取
						if (queryDTO.getProvinceId() == null) {
							queryDTO.setProvinceId(city.getProvinceId());
						}
					});
		}
	}

	private Map<Integer, Province> loadProvinces(Collection<Job> jobs) {
		Set<Integer> ids = jobs.stream()
				.map(Job::getProvinceId)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		if (ids.isEmpty()) {
			return Collections.emptyMap();
		}
		return locationMapper.findProvincesByIds(new ArrayList<>(ids)).stream()
				.collect(Collectors.toMap(Province::getId, Function.identity()));
	}

	private Map<Integer, City> loadCities(Collection<Job> jobs) {
		Set<Integer> ids = jobs.stream()
				.map(Job::getCityId)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		if (ids.isEmpty()) {
			return Collections.emptyMap();
		}
		return locationMapper.findCitiesByIds(new ArrayList<>(ids)).stream()
				.collect(Collectors.toMap(City::getId, Function.identity()));
	}

	private void recordJobView(Integer jobId, Integer studentUserId, HttpServletRequest request) {
		try {
			JobView jobView = new JobView();
			jobView.setJobId(jobId);
			jobView.setViewerUserId(studentUserId);
			jobView.setClientIp(request != null ? request.getRemoteAddr() : null);
			jobView.setUserAgent(request != null ? request.getHeader("User-Agent") : null);
			jobViewMapper.insert(jobView);
			jobMapper.increaseViewCount(jobId);
		} catch (Exception ex) {
			logger.warn("记录岗位浏览失败 jobId={} studentUserId={}", jobId, studentUserId, ex);
		}
	}
	private Map<Integer, JobCategory> loadJobCategories(Collection<Job> jobs) {
		Set<Integer> ids = jobs.stream()
				.map(Job::getType)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		if (ids.isEmpty()) {
			return Collections.emptyMap();
		}
		return jobCategoryMapper.findByIds(new ArrayList<>(ids)).stream()
				.collect(Collectors.toMap(JobCategory::getId, Function.identity()));
	}

	private String buildDisplayAddress(Job job, Map<Integer, Province> provinceMap, Map<Integer, City> cityMap) {
		StringBuilder sb = new StringBuilder();
		String provinceName = null;
		String cityName = null;
		if (job.getProvinceId() != null) {
			Province province = provinceMap.get(job.getProvinceId());
			if (province != null && StringUtils.hasText(province.getName())) {
				provinceName = province.getName();
				sb.append(provinceName);
			}
		}
		if (job.getCityId() != null) {
			City city = cityMap.get(job.getCityId());
			if (city != null && StringUtils.hasText(city.getName())) {
				cityName = city.getName();
				boolean appendCity = true;
				if (StringUtils.hasText(provinceName)) {
					if (MUNICIPALITIES.contains(provinceName) && provinceName.equals(cityName)) {
						appendCity = false;
					}
				}
				if (appendCity) {
					sb.append(cityName);
				}
			}
		}
		if (sb.length() > 0) {
			return sb.toString();
		}
		return resolveJobAddress(job);
	}

	private String resolveJobCategoryName(Job job, Map<Integer, JobCategory> categoryMap) {
		if (job == null) {
			return null;
		}
		if (job.getType() != null) {
			JobCategory category = categoryMap.get(job.getType());
			if (category != null && StringUtils.hasText(category.getName())) {
				return category.getName();
			}
			return String.valueOf(job.getType());
		}
		return null;
	}

	@Override
	public DeliveryListResponseVO getDeliveryList(cn.sysu.sse.recruitment.job_platform_api.pojo.dto.DeliveryListQueryDTO queryDTO, Integer studentUserId) {
		logger.info("获取已投递岗位列表，查询参数：{}，学生ID：{}", queryDTO, studentUserId);
		
		if (studentUserId == null) {
			throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户未登录");
		}
		
		// 分页参数
		int page = queryDTO.getPage() != null && queryDTO.getPage() > 0 ? queryDTO.getPage() : 1;
		int pageSize = queryDTO.getPageSize() != null && queryDTO.getPageSize() > 0 ? queryDTO.getPageSize() : 10;
		int offset = (page - 1) * pageSize;
		
		// 查询已投递岗位列表
		List<cn.sysu.sse.recruitment.job_platform_api.pojo.dto.DeliveryJobDTO> deliveryJobs = applicationMapper.findDeliveryJobsByStudent(
				studentUserId,
				queryDTO.getJobTitle(),
				queryDTO.getCompanyName(),
				offset,
				pageSize
		);
		
		// 查询总数
		long total = applicationMapper.countDeliveryJobsByStudent(
				studentUserId,
				queryDTO.getJobTitle(),
				queryDTO.getCompanyName()
		);
		
		// 预加载省市名称
		Set<Integer> provinceIds = deliveryJobs.stream()
				.map(cn.sysu.sse.recruitment.job_platform_api.pojo.dto.DeliveryJobDTO::getProvinceId)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		Set<Integer> cityIds = deliveryJobs.stream()
				.map(cn.sysu.sse.recruitment.job_platform_api.pojo.dto.DeliveryJobDTO::getCityId)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		
		final Map<Integer, Province> provinceMap;
		final Map<Integer, City> cityMap;
		if (!provinceIds.isEmpty()) {
			provinceMap = locationMapper.findProvincesByIds(new ArrayList<>(provinceIds)).stream()
					.collect(Collectors.toMap(Province::getId, Function.identity()));
		} else {
			provinceMap = Collections.emptyMap();
		}
		if (!cityIds.isEmpty()) {
			cityMap = locationMapper.findCitiesByIds(new ArrayList<>(cityIds)).stream()
					.collect(Collectors.toMap(City::getId, Function.identity()));
		} else {
			cityMap = Collections.emptyMap();
		}
		
		// 转换为VO
		List<DeliveryListItemVO> jobList = deliveryJobs.stream().map(dto -> {
			DeliveryListItemVO vo = new DeliveryListItemVO();
			vo.setJobId(dto.getJobId());
			vo.setApplicationId(dto.getApplicationId());
			vo.setTitle(dto.getJobTitle());
			vo.setDepartment(dto.getDepartment());
			vo.setCompanyName(dto.getCompanyName());
			vo.setLogoUrl(dto.getLogoUrl());
			vo.setSubmittedAt(dto.getSubmittedAt());
			
			// 薪资范围
			if (dto.getMinSalary() != null && dto.getMaxSalary() != null) {
				vo.setSalaryRange(dto.getMinSalary() + "-" + dto.getMaxSalary());
			} else if (dto.getMinSalary() != null) {
				vo.setSalaryRange(dto.getMinSalary() + "及以上");
			} else {
				vo.setSalaryRange("面议");
			}
			
			// 地址
			vo.setAddress(buildDeliveryAddress(dto, provinceMap, cityMap));
			
			// 工作性质
			if (dto.getWorkNature() != null) {
				vo.setWorkNature(dto.getWorkNature() == WorkNature.INTERNHIP ? "实习" : "校招");
			}
			
			// 状态（注意：状态 20 要显示为"已投递"）
			ApplicationStatus displayStatus = dto.getStatus();
			if (displayStatus == ApplicationStatus.CANDIDATE) {
				displayStatus = ApplicationStatus.SUBMITTED;
			}
			vo.setStatus(convertDeliveryStatus(displayStatus));
			
			return vo;
		}).collect(Collectors.toList());
		
		DeliveryListResponseVO response = new DeliveryListResponseVO();
		response.setTotal(total);
		response.setPage(page);
		response.setPageSize(pageSize);
		response.setJobs(jobList);
		
		return response;
	}

	/**
	 * 构建已投递岗位的地址显示
	 */
	private String buildDeliveryAddress(cn.sysu.sse.recruitment.job_platform_api.pojo.dto.DeliveryJobDTO dto, 
	                                    Map<Integer, Province> provinceMap, 
	                                    Map<Integer, City> cityMap) {
		StringBuilder sb = new StringBuilder();
		String provinceName = null;
		String cityName = null;
		
		if (dto.getProvinceId() != null) {
			Province province = provinceMap.get(dto.getProvinceId());
			if (province != null && StringUtils.hasText(province.getName())) {
				provinceName = province.getName();
				sb.append(provinceName);
			}
		}
		
		if (dto.getCityId() != null) {
			City city = cityMap.get(dto.getCityId());
			if (city != null && StringUtils.hasText(city.getName())) {
				cityName = city.getName();
				boolean appendCity = true;
				if (StringUtils.hasText(provinceName)) {
					if (MUNICIPALITIES.contains(provinceName) && provinceName.equals(cityName)) {
						appendCity = false;
					}
				}
				if (appendCity) {
					sb.append(cityName);
				}
			}
		}
		
		if (sb.length() > 0) {
			return sb.toString();
		}
		
		// 如果没有省市信息，使用详细地址
		return dto.getAddressDetail() != null ? dto.getAddressDetail() : "";
	}

	/**
	 * 转换投递状态为显示文本
	 * 注意：状态 20（候选人）要显示为"已投递"
	 */
	private String convertDeliveryStatus(ApplicationStatus status) {
		if (status == null) {
			return "未知";
		}
		
		switch (status) {
			case SUBMITTED:
				return "已投递";
			case CANDIDATE:
				// 状态 20 在学生端显示为"已投递"
				return "已投递";
			case INTERVIEW:
				return "邀请面试"; // 按照 OpenAPI 规范
			case PASSED:
				return "通过";
			case REJECTED:
				return "拒绝";
			default:
				return "未知";
		}
	}
}

