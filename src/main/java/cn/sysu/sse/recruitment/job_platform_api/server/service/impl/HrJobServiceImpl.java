package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.common.enums.JobStatus;
import cn.sysu.sse.recruitment.job_platform_api.common.enums.WorkNature;
import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import cn.sysu.sse.recruitment.job_platform_api.common.result.Pagination;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobCreateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobListQueryDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.JobWithStatsDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Company;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Job;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobCreateResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobListResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.CompanyMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.JobMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.TagMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.service.HrJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HrJobServiceImpl implements HrJobService {

    private static final Logger logger = LoggerFactory.getLogger(HrJobServiceImpl.class);

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private JobMapper jobMapper;

    @Autowired
    private TagMapper tagMapper;

    private static final Map<String, WorkNature> WORK_NATURE_MAP = Map.ofEntries(
            Map.entry("internship", WorkNature.INTERNHIP),
            Map.entry("full-time", WorkNature.FULL_TIME),
            Map.entry("fulltime", WorkNature.FULL_TIME),
            Map.entry("实习", WorkNature.INTERNHIP),
            Map.entry("校招", WorkNature.FULL_TIME)
    );

    private static final Map<String, JobStatus> JOB_STATUS_MAP = Map.ofEntries(
            Map.entry("draft", JobStatus.DRAFT),
            Map.entry("pending", JobStatus.PENDING),
            Map.entry("approved", JobStatus.APPROVED),
            Map.entry("rejected", JobStatus.REJECTED),
            Map.entry("closed", JobStatus.CLOSED)
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HrJobCreateResponseVO createJob(Integer userId, HrJobCreateDTO dto) {
        logger.info("创建岗位 userId={}, dto={}", userId, dto);
        if (userId == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户未登录");
        }

        Company company = companyMapper.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "未找到企业信息，无法创建岗位"));

        SalaryRangeValue salaryRange = resolveSalaryRange(dto);
        WorkNature workNature = StringUtils.hasText(dto.getWorkNature())
                ? parseWorkNatureEnum(dto.getWorkNature(), true)
                : null;
        JobStatus status = StringUtils.hasText(dto.getStatus())
                ? parseJobStatusEnum(dto.getStatus(), true)
                : JobStatus.DRAFT;
        String addressDetail = resolveAddressDetail(dto);
        String workAddress = StringUtils.hasText(dto.getWorkAddress()) ? dto.getWorkAddress() : addressDetail;

        Job job = new Job();
        job.setCompanyId(company.getCompanyId());
        job.setPostedByUserId(userId);
        job.setTitle(dto.getTitle());
        job.setDepartment(dto.getDepartment());
        job.setDescription(dto.getDescription());
        job.setTechRequirements(dto.getTechRequirements());
        job.setBonusPoints(dto.getBonusPoints());
        job.setType(dto.getType());
        job.setHeadcount(dto.getHeadcount());
        job.setRequiredDegree(dto.getRequiredDegree());
        job.setRequiredStartDate(dto.getRequiredStartDate());
        job.setDeadline(dto.getDeadline());
        job.setWorkNature(workNature);
        job.setStatus(status != null ? status : JobStatus.DRAFT);
        job.setMinSalary(salaryRange.getMinSalary());
        job.setMaxSalary(salaryRange.getMaxSalary());
        job.setProvinceId(dto.getProvinceId());
        job.setCityId(dto.getCityId());
        job.setAddressDetail(addressDetail);
        job.setWorkAddress(workAddress);

        int inserted = jobMapper.insert(job);
        if (inserted != 1 || job.getId() == null) {
            throw new BusinessException(ErrorCode.INTERNAL_ERROR, "创建岗位失败");
        }

        handleJobTags(job.getId(), dto.getTags());
        logger.info("岗位创建成功 jobId={} userId={}", job.getId(), userId);

        HrJobCreateResponseVO.NewJob newJob = new HrJobCreateResponseVO.NewJob();
        newJob.setJobId(job.getId());
        newJob.setTitle(job.getTitle());
        newJob.setStatus(job.getStatus() != null ? job.getStatus().name().toLowerCase(Locale.ROOT) : null);

        HrJobCreateResponseVO response = new HrJobCreateResponseVO();
        response.setNewJob(newJob);
        return response;
    }

    private Integer resolveWorkNature(String workNature) {
        WorkNature enumVal = parseWorkNatureEnum(workNature, false);
        return enumVal != null ? enumVal.getCode() : null;
    }

    private Integer resolveJobStatus(String status) {
        JobStatus enumVal = parseJobStatusEnum(status, false);
        return enumVal != null ? enumVal.getCode() : null;
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

    private WorkNature parseWorkNatureEnum(String workNature, boolean strict) {
        if (!StringUtils.hasText(workNature)) {
            return null;
        }
        String key = normalizeKey(workNature);
        WorkNature enumVal = WORK_NATURE_MAP.get(key);
        if (enumVal != null) {
            return enumVal;
        }
        try {
            return WorkNature.fromCode(Integer.parseInt(key));
        } catch (Exception ex) {
            if (strict) {
                throw new BusinessException(ErrorCode.BAD_REQUEST, "无效的工作性质");
            }
            logger.warn("无法解析 work_nature 参数：{}", workNature);
            return null;
        }
    }

    private JobStatus parseJobStatusEnum(String status, boolean strict) {
        if (!StringUtils.hasText(status)) {
            return null;
        }
        String key = normalizeKey(status);
        JobStatus enumVal = JOB_STATUS_MAP.get(key);
        if (enumVal != null) {
            return enumVal;
        }
        try {
            return JobStatus.fromCode(Integer.parseInt(key));
        } catch (Exception ex) {
            if (strict) {
                throw new BusinessException(ErrorCode.BAD_REQUEST, "无效的岗位状态");
            }
            logger.warn("无法解析 status 参数：{}", status);
            return null;
        }
    }

    private SalaryRangeValue resolveSalaryRange(HrJobCreateDTO dto) {
        Integer min = dto.getMinSalary();
        Integer max = dto.getMaxSalary();
        if (StringUtils.hasText(dto.getSalaryRange())) {
            SalaryRangeValue parsed = parseSalaryRangeString(dto.getSalaryRange());
            min = parsed.getMinSalary();
            max = parsed.getMaxSalary();
        }
        if ((min != null && min < 0) || (max != null && max < 0)) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "薪资不能为负数");
        }
        if (min != null && max != null && min > max) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "最低薪资不能高于最高薪资");
        }
        return new SalaryRangeValue(min, max);
    }

    private SalaryRangeValue parseSalaryRangeString(String salaryRange) {
        String normalized = salaryRange.trim().toLowerCase(Locale.ROOT)
                .replace("k", "")
                .replace("－", "-")
                .replace("—", "-")
                .replace("–", "-")
                .replace("~", "-")
                .replace("～", "-")
                .replace(" ", "");
        if (!StringUtils.hasText(normalized)) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "薪资范围格式错误");
        }
        String[] parts = normalized.split("-");
        try {
            if (parts.length == 1) {
                Integer value = Integer.parseInt(parts[0]);
                return new SalaryRangeValue(value, value);
            } else if (parts.length == 2) {
                Integer min = Integer.parseInt(parts[0]);
                Integer max = Integer.parseInt(parts[1]);
                return new SalaryRangeValue(min, max);
            }
        } catch (NumberFormatException ex) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "薪资范围格式错误");
        }
        throw new BusinessException(ErrorCode.BAD_REQUEST, "薪资范围格式错误");
    }

    private String resolveAddressDetail(HrJobCreateDTO dto) {
        if (StringUtils.hasText(dto.getAddressDetail())) {
            return dto.getAddressDetail();
        }
        if (StringUtils.hasText(dto.getLegacyLocation())) {
            return dto.getLegacyLocation();
        }
        return null;
    }

    private void handleJobTags(Integer jobId, List<Integer> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return;
        }
        List<Integer> normalized = tagIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (normalized.isEmpty()) {
            return;
        }
        List<Integer> existing = tagMapper.findExistingIds(normalized);
        Set<Integer> missing = new LinkedHashSet<>(normalized);
        if (existing != null) {
            missing.removeAll(existing);
        }
        if (!missing.isEmpty()) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "以下标签不存在：" + missing);
        }
        jobMapper.batchInsertJobTags(jobId, normalized);
    }

    private String normalizeKey(String raw) {
        return raw.trim().toLowerCase(Locale.ROOT).replace('_', '-').replace(" ", "-");
    }

    private static class SalaryRangeValue {
        private final Integer minSalary;
        private final Integer maxSalary;

        SalaryRangeValue(Integer minSalary, Integer maxSalary) {
            this.minSalary = minSalary;
            this.maxSalary = maxSalary;
        }

        Integer getMinSalary() {
            return minSalary;
        }

        Integer getMaxSalary() {
            return maxSalary;
        }
    }
}
