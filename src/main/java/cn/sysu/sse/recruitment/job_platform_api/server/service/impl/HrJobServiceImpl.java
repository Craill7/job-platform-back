package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.common.enums.ApplicationStatus;
import cn.sysu.sse.recruitment.job_platform_api.common.enums.JobStatus;
import cn.sysu.sse.recruitment.job_platform_api.common.enums.WorkNature;
import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import cn.sysu.sse.recruitment.job_platform_api.common.result.Pagination;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.CandidateApplicationSummaryDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobCreateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobListQueryDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.JobWithStatsDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobUpdateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Company;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Job;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Tag;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrCandidateListResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobCreateResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobDetailResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobDetailVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobListResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobStatusResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobUpdateResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.server.service.HrJobService;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.CompanyMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.JobMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.TagMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.ApplicationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.EnumSet;
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

    @Autowired
    private ApplicationMapper applicationMapper;

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

    private static final Map<JobStatus, Set<JobStatus>> STATUS_TRANSITIONS = Map.of(
            JobStatus.DRAFT, EnumSet.of(JobStatus.DRAFT, JobStatus.PENDING),
            JobStatus.PENDING, EnumSet.of(JobStatus.PENDING, JobStatus.APPROVED, JobStatus.REJECTED),
            JobStatus.APPROVED, EnumSet.of(JobStatus.APPROVED, JobStatus.CLOSED),
            JobStatus.REJECTED, EnumSet.of(JobStatus.REJECTED, JobStatus.PENDING),
            JobStatus.CLOSED, EnumSet.of(JobStatus.CLOSED)
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

    /**
     * 获取岗位详情
     * @param userId HR用户ID
     * @param jobId 岗位ID
     * @return 岗位详情
     */
    @Override
    public HrJobDetailResponseVO getJobDetail(Integer userId, Integer jobId) {
        logger.info("查询岗位详情 userId={}, jobId={}", userId, jobId);
        if (userId == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户未登录");
        }

        Company company = companyMapper.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "未找到企业信息"));

        Job job = jobMapper.findByIdAndCompany(jobId, company.getCompanyId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "岗位不存在或无权访问"));

        HrJobDetailVO detailVO = buildJobDetailVo(job);
        List<Tag> tags = tagMapper.findTagsByJobId(jobId);
        List<HrJobDetailVO.JobTagVO> tagVOList = (tags == null ? Collections.<Tag>emptyList() : tags).stream()
                .map(tag -> {
                    HrJobDetailVO.JobTagVO tagVO = new HrJobDetailVO.JobTagVO();
                    tagVO.setTagId(tag.getId());
                    tagVO.setTagName(tag.getName());
                    return tagVO;
                })
                .collect(Collectors.toList());
        detailVO.setTags(tagVOList);

        HrJobDetailResponseVO response = new HrJobDetailResponseVO();
        response.setJobDetails(detailVO);
        return response;
    }

    /**
     * 删除草稿岗位
     * @param userId HR用户ID
     * @param jobId 岗位ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDraftJob(Integer userId, Integer jobId) {
        logger.info("删除草稿岗位 userId={}, jobId={}", userId, jobId);
        if (userId == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户未登录");
        }

        Company company = companyMapper.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "未找到企业信息"));

        Job job = jobMapper.findByIdAndCompany(jobId, company.getCompanyId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "岗位不存在或无权访问"));

        if (job.getStatus() != JobStatus.DRAFT) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "操作失败：只能删除草稿状态的岗位");
        }

        long applicationCount = applicationMapper.countByJob(jobId);
        if (applicationCount > 0) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "该岗位已有投递记录，无法删除");
        }

        jobMapper.deleteJobTagsByJobId(jobId);
        int affected = jobMapper.deleteById(jobId);
        if (affected != 1) {
            throw new BusinessException(ErrorCode.INTERNAL_ERROR, "删除岗位失败");
        }

        logger.info("草稿岗位删除成功 jobId={} userId={}", jobId, userId);
    }

    /**
     * 更新岗位
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public HrJobUpdateResponseVO updateJob(Integer userId, Integer jobId, HrJobUpdateDTO dto) {
        logger.info("更新岗位 userId={} jobId={} dto={}", userId, jobId, dto);
        if (userId == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户未登录");
        }

        Company company = companyMapper.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "未找到企业信息"));

        Job job = jobMapper.findByIdAndCompany(jobId, company.getCompanyId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "岗位不存在或无权访问"));

        JobStatus targetStatus = job.getStatus();
        if (StringUtils.hasText(dto.getStatus())) {
            JobStatus parsedStatus = parseJobStatusEnum(dto.getStatus(), true);
            validateStatusTransition(job.getStatus(), parsedStatus);
            targetStatus = parsedStatus;
        }

        WorkNature workNature = job.getWorkNature();
        if (StringUtils.hasText(dto.getWorkNature())) {
            workNature = parseWorkNatureEnum(dto.getWorkNature(), true);
        }

        SalaryRangeValue salaryRange = resolveSalaryRange(dto, job);
        String resolvedAddressDetail = resolveAddressDetail(dto, job);
        String resolvedWorkAddress = resolveWorkAddress(dto, resolvedAddressDetail, job);

        job.setTitle(valueOrDefault(dto.getTitle(), job.getTitle()));
        job.setDepartment(valueOrDefault(dto.getDepartment(), job.getDepartment()));
        job.setDescription(valueOrDefault(dto.getDescription(), job.getDescription()));
        job.setTechRequirements(valueOrDefault(dto.getTechRequirements(), job.getTechRequirements()));
        job.setBonusPoints(valueOrDefault(dto.getBonusPoints(), job.getBonusPoints()));
        job.setType(valueOrDefault(dto.getType(), job.getType()));
        job.setHeadcount(valueOrDefault(dto.getHeadcount(), job.getHeadcount()));
        job.setRequiredDegree(valueOrDefault(dto.getRequiredDegree(), job.getRequiredDegree()));
        job.setRequiredStartDate(valueOrDefault(dto.getRequiredStartDate(), job.getRequiredStartDate()));
        job.setDeadline(valueOrDefault(dto.getDeadline(), job.getDeadline()));
        job.setProvinceId(valueOrDefault(dto.getProvinceId(), job.getProvinceId()));
        job.setCityId(valueOrDefault(dto.getCityId(), job.getCityId()));
        job.setAddressDetail(resolvedAddressDetail);
        job.setWorkAddress(resolvedWorkAddress);
        job.setWorkNature(workNature);
        job.setStatus(targetStatus);
        job.setMinSalary(salaryRange.getMinSalary());
        job.setMaxSalary(salaryRange.getMaxSalary());

        int affected = jobMapper.update(job);
        if (affected != 1) {
            throw new BusinessException(ErrorCode.INTERNAL_ERROR, "更新岗位失败");
        }

        if (dto.getTags() != null) {
            jobMapper.deleteJobTagsByJobId(jobId);
            handleJobTags(jobId, dto.getTags());
        }

        HrJobUpdateResponseVO response = new HrJobUpdateResponseVO();
        HrJobUpdateResponseVO.UpdatedJob updatedJob = new HrJobUpdateResponseVO.UpdatedJob();
        updatedJob.setJobId(job.getId());
        updatedJob.setTitle(job.getTitle());
        updatedJob.setStatus(job.getStatus() != null ? job.getStatus().name().toLowerCase(Locale.ROOT) : null);
        response.setUpdatedJob(updatedJob);
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HrJobStatusResponseVO closeJob(Integer userId, Integer jobId) {
        logger.info("下线岗位 userId={} jobId={}", userId, jobId);
        if (userId == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户未登录");
        }

        Company company = companyMapper.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "未找到企业信息"));

        Job job = jobMapper.findByIdAndCompany(jobId, company.getCompanyId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "岗位不存在或无权访问"));

        if (job.getStatus() == JobStatus.CLOSED) {
            logger.info("岗位已是 closed，直接返回 jobId={} userId={}", jobId, userId);
            return buildJobStatusResponse(job);
        }

        if (job.getStatus() != JobStatus.APPROVED) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "仅已发布岗位可下线");
        }

        job.setStatus(JobStatus.CLOSED);
        int affected = jobMapper.update(job);
        if (affected != 1) {
            throw new BusinessException(ErrorCode.INTERNAL_ERROR, "更新岗位状态失败");
        }

        logger.info("岗位下线成功 jobId={} userId={}", jobId, userId);
        return buildJobStatusResponse(job);
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

    @Override
    public HrCandidateListResponseVO listCandidatesByJob(Integer userId,
                                                         Integer jobId,
                                                         String nameKeyword,
                                                         Integer status,
                                                         Integer page,
                                                         Integer pageSize) {
        logger.info("查询岗位下人才列表 userId={} jobId={} nameKeyword={} status={} page={} pageSize={}", userId, jobId, nameKeyword, status, page, pageSize);
        if (userId == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户未登录");
        }

        Company company = companyMapper.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "未找到企业信息"));

        jobMapper.findByIdAndCompany(jobId, company.getCompanyId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "岗位不存在或无权访问"));

        int currentPage = (page != null && page > 0) ? page : 1;
        int currentPageSize = (pageSize != null && pageSize > 0) ? pageSize : 10;
        int offset = (currentPage - 1) * currentPageSize;

        List<CandidateApplicationSummaryDTO> summaries = applicationMapper.listCandidatesByJob(
                jobId,
                StringUtils.hasText(nameKeyword) ? nameKeyword.trim() : null,
                status,
                offset,
                currentPageSize
        );
        long totalItems = applicationMapper.countCandidatesByJob(
                jobId,
                StringUtils.hasText(nameKeyword) ? nameKeyword.trim() : null,
                status
        );
        long totalPages = (totalItems + currentPageSize - 1) / currentPageSize;

        List<HrCandidateListResponseVO.CandidateSummaryVO> candidateList = summaries.stream()
                .map(this::convertCandidateSummary)
                .collect(Collectors.toList());

        HrCandidateListResponseVO response = new HrCandidateListResponseVO();
        response.setCandidateList(candidateList);
        response.setPagination(new Pagination(totalItems, totalPages, currentPage, currentPageSize));
        return response;
    }

    private HrCandidateListResponseVO.CandidateSummaryVO convertCandidateSummary(CandidateApplicationSummaryDTO dto) {
        HrCandidateListResponseVO.CandidateSummaryVO vo = new HrCandidateListResponseVO.CandidateSummaryVO();
        vo.setApplicationId(dto.getApplicationId());
        vo.setCandidateName(dto.getCandidateName());
        vo.setGrade(buildGrade(dto.getStartYear()));
        vo.setDegree(mapDegree(dto.getDegreeLevel()));
        vo.setResumeStatus(mapStatusToDisplay(dto.getStatus()));
        return vo;
    }

    private String buildGrade(Integer startYear) {
        return startYear != null ? startYear + "级" : null;
    }

    private String mapDegree(Integer degreeLevel) {
        if (degreeLevel == null) {
            return null;
        }
        return switch (degreeLevel) {
            case 0 -> "bachelor";
            case 1 -> "master";
            case 2 -> "doctor";
            default -> null;
        };
    }

    private String mapStatusToDisplay(ApplicationStatus status) {
        if (status == null) {
            return "未知";
        }
        return switch (status) {
            case SUBMITTED -> "已投递";
            case CANDIDATE -> "候选人";
            case INTERVIEW -> "面试邀请";
            case PASSED -> "通过";
            case REJECTED -> "拒绝";
        };
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
        return resolveSalaryRange(dto.getMinSalary(), dto.getMaxSalary(), dto.getSalaryRange());
    }

    private SalaryRangeValue resolveSalaryRange(HrJobUpdateDTO dto, Job existingJob) {
        Integer min = dto.getMinSalary() != null ? dto.getMinSalary() : existingJob.getMinSalary();
        Integer max = dto.getMaxSalary() != null ? dto.getMaxSalary() : existingJob.getMaxSalary();
        return resolveSalaryRange(min, max, dto.getSalaryRange());
    }

    private SalaryRangeValue resolveSalaryRange(Integer minSalary, Integer maxSalary, String salaryRangeExpression) {
        Integer min = minSalary;
        Integer max = maxSalary;
        if (StringUtils.hasText(salaryRangeExpression)) {
            SalaryRangeValue parsed = parseSalaryRangeString(salaryRangeExpression);
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

    private void validateStatusTransition(JobStatus currentStatus, JobStatus targetStatus) {
        if (targetStatus == null || currentStatus == targetStatus) {
            return;
        }
        Set<JobStatus> allowed = STATUS_TRANSITIONS.getOrDefault(currentStatus, Collections.emptySet());
        if (!allowed.contains(targetStatus)) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "非法的岗位状态流转");
        }
    }

    private <T> T valueOrDefault(T newValue, T oldValue) {
        return newValue != null ? newValue : oldValue;
    }

    private String resolveAddressDetail(HrJobUpdateDTO dto, Job job) {
        if (dto.getAddressDetail() != null) {
            return dto.getAddressDetail();
        }
        if (StringUtils.hasText(dto.getLegacyLocation())) {
            return dto.getLegacyLocation();
        }
        return job.getAddressDetail();
    }

    private String resolveWorkAddress(HrJobUpdateDTO dto, String resolvedAddressDetail, Job job) {
        if (dto.getWorkAddress() != null) {
            return dto.getWorkAddress();
        }
        if (!StringUtils.hasText(job.getWorkAddress())) {
            return resolvedAddressDetail;
        }
        return job.getWorkAddress();
    }

    private HrJobStatusResponseVO buildJobStatusResponse(Job job) {
        HrJobStatusResponseVO response = new HrJobStatusResponseVO();
        response.setJobId(job.getId());
        response.setStatus(job.getStatus() != null ? job.getStatus().name().toLowerCase(Locale.ROOT) : null);
        return response;
    }

    private HrJobDetailVO buildJobDetailVo(Job job) {
        HrJobDetailVO vo = new HrJobDetailVO();
        vo.setJobId(job.getId());
        vo.setTitle(job.getTitle());
        vo.setStatus(job.getStatus() != null ? job.getStatus().getCode() : null);
        vo.setDescription(job.getDescription());
        vo.setTechRequirements(job.getTechRequirements());
        vo.setBonusPoints(job.getBonusPoints());
        vo.setMinSalary(job.getMinSalary());
        vo.setMaxSalary(job.getMaxSalary());
        vo.setProvinceId(job.getProvinceId());
        vo.setCityId(job.getCityId());
        vo.setAddressDetail(job.getAddressDetail());
        vo.setWorkNature(job.getWorkNature() != null ? job.getWorkNature().getCode() : null);
        vo.setDepartment(job.getDepartment());
        vo.setHeadcount(job.getHeadcount());
        vo.setType(job.getType());
        vo.setRequiredDegree(job.getRequiredDegree());
        vo.setRequiredStartDate(job.getRequiredStartDate());
        vo.setDeadline(job.getDeadline());
        vo.setCreatedAt(job.getCreatedAt());
        vo.setUpdatedAt(job.getUpdatedAt());
        vo.setWorkAddress(job.getWorkAddress());
        return vo;
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
