package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.common.enums.ApplicationStatus;
import cn.sysu.sse.recruitment.job_platform_api.common.enums.JobStatus;
import cn.sysu.sse.recruitment.job_platform_api.common.enums.WorkNature;
import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.ApplicationSubmitDTO;
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
import java.util.stream.Collectors;

/**
 * 求职中心服务实现
 */
@Service
public class PositionCenterServiceImpl implements PositionCenterService {

	private static final Logger logger = LoggerFactory.getLogger(PositionCenterServiceImpl.class);

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
		
		// 分页参数
		int page = queryDTO.getPage() != null && queryDTO.getPage() > 0 ? queryDTO.getPage() : 1;
		int pageSize = queryDTO.getPageSize() != null && queryDTO.getPageSize() > 0 ? queryDTO.getPageSize() : 10;
		int offset = (page - 1) * pageSize;
		
		// 查询岗位列表
		List<Job> jobs = jobMapper.searchJobs(
				queryDTO.getKeyword(),
				queryDTO.getLocation(),
				queryDTO.getType(),
				tagIds,
				workNature,
				offset,
				pageSize
		);
		
		// 查询总数
		long total = jobMapper.countSearchJobs(
				queryDTO.getKeyword(),
				queryDTO.getLocation(),
				queryDTO.getType(),
				tagIds,
				workNature
		);
		
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
			
			vo.setLocation(job.getLocation());
			vo.setWorkNature(job.getWorkNature() != null ? 
					(job.getWorkNature() == WorkNature.INTERNHIP ? "实习" : "校招") : null);
			vo.setType(job.getType() != null ? String.valueOf(job.getType()) : null);
			vo.setDepartment(job.getDepartment());
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
			
			vo.setLocation(job.getLocation());
			vo.setWorkNature(job.getWorkNature() != null ? 
					(job.getWorkNature() == WorkNature.INTERNHIP ? "实习" : "校招") : null);
			vo.setType(job.getType() != null ? String.valueOf(job.getType()) : null);
			vo.setDepartment(job.getDepartment());
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
	public JobDetailVO getJobDetail(Integer jobId, Integer studentUserId) {
		logger.info("获取职位详情，职位ID：{}，学生ID：{}", jobId, studentUserId);
		
		Optional<Job> jobOpt = jobMapper.findById(jobId);
		if (jobOpt.isEmpty()) {
			throw new BusinessException(ErrorCode.NOT_FOUND, "岗位不存在");
		}
		
		Job job = jobOpt.get();
		if (job.getStatus() != JobStatus.APPROVED) {
			throw new BusinessException(ErrorCode.FORBIDDEN, "岗位未发布或已关闭");
		}
		
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
		
		vo.setLocation(job.getLocation());
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
		vo.setPostedAt(job.getCreatedAt() != null ? job.getCreatedAt().toLocalDate() : null);
		vo.setPositionDescription(job.getDescription());
		vo.setPositionRequirements(job.getDescription()); // 这里可以根据实际需求调整
		vo.setWorkAddress(job.getWorkAddress());
		
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
			companyInfo.setCompanyLogoUrl(company.getLogoUrl());
			companyInfo.setCompanyName(company.getCompanyName());
			companyInfo.setContactPersonName(company.getContactPersonName());
			companyInfo.setContactPersonPhone(company.getContactPersonPhone());
			// 行业和规模需要查询关联表，这里简化处理
			companyInfo.setCompanyIndustry(null);
			companyInfo.setCompanyScale(null);
			companyInfo.setCompanyWebsiteUrl(null);
			
			// 查询同公司其他岗位
			List<Job> otherJobs = jobMapper.findOtherJobsByCompany(company.getCompanyId(), jobId, 5);
			List<JobDetailVO.OtherJobVO> otherJobList = otherJobs.stream().map(otherJob -> {
				JobDetailVO.OtherJobVO otherJobVO = new JobDetailVO.OtherJobVO();
				otherJobVO.setJobId(otherJob.getId());
				otherJobVO.setTitle(otherJob.getTitle());
				return otherJobVO;
			}).collect(Collectors.toList());
			companyInfo.setOtherJobs(otherJobList);
			
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
}

