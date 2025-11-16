package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.common.enums.JobStatus;
import cn.sysu.sse.recruitment.job_platform_api.common.enums.WorkNature;
import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import cn.sysu.sse.recruitment.job_platform_api.common.result.Pagination;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobListQueryDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.JobWithStatsDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Company;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobListResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.CompanyMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.JobMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.service.HrJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HrJobServiceImpl implements HrJobService {

	private static final Logger logger = LoggerFactory.getLogger(HrJobServiceImpl.class);

	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private JobMapper jobMapper;

	private static final Map<String, WorkNature> WORK_NATURE_MAP = Map.of(
			"internship", WorkNature.INTERNHIP,
			"full-time", WorkNature.FULL_TIME,
			"实习", WorkNature.INTERNHIP,
			"校招", WorkNature.FULL_TIME
	);

	private static final Map<String, JobStatus> JOB_STATUS_MAP = Map.of(
			"pending", JobStatus.PENDING,
			"approved", JobStatus.APPROVED,
			"rejected", JobStatus.REJECTED,
			"closed", JobStatus.CLOSED
	);

	@Override
	public HrJobListResponseVO listCompanyJobs(Integer userId, HrJobListQueryDTO queryDTO) {
		logger.info("查询企业岗位列表 userId={}, query={}", userId, queryDTO);
		if (userId == null) {
			throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户未登录");
		}

		Company company = companyMapper.findByUserId(userId)
				.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "未找到企业信息"));

		int page = queryDTO.getPage() != null && queryDTO.getPage() > 0 ? queryDTO.getPage() : 1;
		int pageSize = queryDTO.getPageSize() != null && queryDTO.getPageSize() > 0 ? queryDTO.getPageSize() : 10;
		int offset = (page - 1) * pageSize;

		Integer workNatureCode = resolveWorkNature(queryDTO.getWorkNature());
		Integer statusCode = resolveJobStatus(queryDTO.getStatus());

		List<JobWithStatsDTO> data = jobMapper.searchCompanyJobs(
				company.getCompanyId(),
				queryDTO.getTitleKeyword(),
				workNatureCode,
				statusCode,
				offset,
				pageSize
		);

		long total = jobMapper.countCompanyJobs(
				company.getCompanyId(),
				queryDTO.getTitleKeyword(),
				workNatureCode,
				statusCode
		);

		List<HrJobListResponseVO.JobSummary> jobList = data.stream()
				.map(this::convertToVo)
				.collect(Collectors.toList());

		long totalPages = (total + pageSize - 1) / pageSize;
		Pagination pagination = new Pagination(total, totalPages, page, pageSize);

		HrJobListResponseVO response = new HrJobListResponseVO();
		response.setJobList(jobList);
		response.setPagination(pagination);
		return response;
	}

	private Integer resolveWorkNature(String workNature) {
		if (!StringUtils.hasText(workNature)) {
			return null;
		}
		WorkNature enumVal = WORK_NATURE_MAP.get(workNature.trim().toLowerCase());
		if (enumVal != null) {
			return enumVal.getCode();
		}
		try {
			return Integer.parseInt(workNature.trim());
		} catch (NumberFormatException ex) {
			logger.warn("无法解析 work_nature 参数：{}", workNature);
			return null;
		}
	}

	private Integer resolveJobStatus(String status) {
		if (!StringUtils.hasText(status)) {
			return null;
		}
		JobStatus enumVal = JOB_STATUS_MAP.get(status.trim().toLowerCase());
		if (enumVal != null) {
			return enumVal.getCode();
		}
		try {
			return Integer.parseInt(status.trim());
		} catch (NumberFormatException ex) {
			logger.warn("无法解析 status 参数：{}", status);
			return null;
		}
	}

	private HrJobListResponseVO.JobSummary convertToVo(JobWithStatsDTO dto) {
		HrJobListResponseVO.JobSummary vo = new HrJobListResponseVO.JobSummary();
		vo.setJobId(dto.getJobId());
		vo.setTitle(dto.getTitle());
		vo.setWorkNature(mapWorkNature(dto.getWorkNature()));
		vo.setStatus(mapJobStatus(dto.getStatus()));
		vo.setUpdatedAt(dto.getUpdatedAt());
		vo.setReceivedNum(dto.getReceivedNum());
		vo.setNoReviewNum(dto.getNoReviewNum());
		return vo;
	}

	private String mapWorkNature(Integer code) {
		if (code == null) {
			return null;
		}
		try {
			WorkNature workNature = WorkNature.fromCode(code);
			return workNature == WorkNature.INTERNHIP ? "internship" : "full-time";
		} catch (IllegalArgumentException ex) {
			logger.warn("未知的 work_nature code: {}", code);
			return null;
		}
	}

	private String mapJobStatus(Integer code) {
		if (code == null) {
			return null;
		}
		try {
			return JobStatus.fromCode(code).name().toLowerCase();
		} catch (IllegalArgumentException ex) {
			logger.warn("未知的 job status code: {}", code);
			return null;
		}
	}
}
