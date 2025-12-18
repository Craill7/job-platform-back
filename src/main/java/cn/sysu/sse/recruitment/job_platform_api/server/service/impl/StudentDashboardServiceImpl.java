package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.common.enums.ApplicationStatus;
import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import cn.sysu.sse.recruitment.job_platform_api.common.result.Pagination;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.*;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.*;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.*;
import cn.sysu.sse.recruitment.job_platform_api.server.service.StudentDashboardService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 学生主页服务实现
 */
@Service
public class StudentDashboardServiceImpl implements StudentDashboardService {

	private static final Logger logger = LoggerFactory.getLogger(StudentDashboardServiceImpl.class);
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Value("${file.server-prefix}")
	private String serverPrefix;

	@Autowired
	private StudentMapper studentMapper;

	@Autowired
	private EventMapper eventMapper;

	@Autowired
	private JobMapper jobMapper;

	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private ApplicationMapper applicationMapper;

	@Autowired
	private ApplicationStatusMapper applicationStatusMapper;

	@Override
	public StudentMeVO getStudentMe(Integer studentUserId) {
		logger.info("获取学生信息，学生ID：{}", studentUserId);
		
		Optional<Student> studentOpt = studentMapper.findByUserId(studentUserId);
		if (studentOpt.isEmpty()) {
			logger.warn("学生信息不存在，学生ID：{}", studentUserId);
			return new StudentMeVO("");
		}
		
		Student student = studentOpt.get();
		return new StudentMeVO(student.getFullName() != null ? student.getFullName() : "");
	}

	@Override
	public CalendarVO getCalendar(String month) {
		logger.info("获取日历数据，月份：{}", month);
		
		// 解析月份，如果为null则使用当前月
		YearMonth yearMonth;
		if (month != null && !month.trim().isEmpty()) {
			try {
				yearMonth = YearMonth.parse(month);
			} catch (Exception e) {
				logger.warn("月份格式错误：{}，使用当前月", month);
				yearMonth = YearMonth.now();
			}
		} else {
			yearMonth = YearMonth.now();
		}
		
		// 计算月份的开始和结束时间
		LocalDateTime startDate = yearMonth.atDay(1).atStartOfDay();
		LocalDateTime endDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);
		
		// 查询该月份的活动
		List<Event> events = eventMapper.findEventsByMonth(startDate, endDate);
		
		// 格式化显示月份
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年 MM月", Locale.CHINA);
		String displayMonth = yearMonth.format(formatter);
		
		// 获取当前日期
		LocalDate today = LocalDate.now();
		Integer currentDay = (yearMonth.equals(YearMonth.from(today))) ? today.getDayOfMonth() : null;
		
		// 按日期分组活动
		Map<LocalDate, List<Event>> eventsByDate = events.stream()
				.collect(Collectors.groupingBy(event -> event.getEventStartTime().toLocalDate()));
		
		// 构建每日事件列表
		List<DailyEventVO> dailyEvents = eventsByDate.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.map(entry -> {
					LocalDate eventDate = entry.getKey();
					List<Event> dayEvents = entry.getValue();
					
					List<EventSummaryVO> summaries = dayEvents.stream()
							.map(event -> {
								// 构建摘要：时间 - 公司名-标题...
								String timeStr = event.getEventStartTime().format(DateTimeFormatter.ofPattern("HH:mm"));
								String summary = timeStr + " - " + event.getEventTitle();
								// 如果标题太长，截断
								if (summary.length() > 30) {
									summary = summary.substring(0, 27) + "...";
								}
								return new EventSummaryVO("evt-" + event.getId(), summary);
							})
							.collect(Collectors.toList());
					
					return new DailyEventVO(
							eventDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
							summaries
					);
				})
				.collect(Collectors.toList());
		
		return new CalendarVO(displayMonth, currentDay, dailyEvents);
	}

	@Override
	public UpcomingEventsVO getUpcomingEvents(Integer limit) {
		logger.info("获取近期活动，限制数量：{}", limit);
		
		int actualLimit = (limit != null && limit > 0) ? limit : 5;
		List<Event> events = eventMapper.findUpcomingEvents(actualLimit);
		
		List<EventItemVO> eventItems = events.stream()
				.map(event -> {
					// 格式化为 "yyyy-MM-dd HH:mm" 以匹配OpenAPI示例
					String startTimeStr = event.getEventStartTime()
							.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
					String location = event.getEventLocation() != null && !event.getEventLocation().trim().isEmpty() 
							? event.getEventLocation() : "暂无地点";
					
					return new EventItemVO(
							event.getId().intValue(),
							event.getEventTitle(),
							startTimeStr,
							location
					);
				})
				.collect(Collectors.toList());
		
		return new UpcomingEventsVO(eventItems);
	}

	@Override
	public RankedJobsVO getRankedJobs(Integer limit) {
		logger.info("获取岗位热度排行，限制数量：{}", limit);
		
		int actualLimit = (limit != null && limit > 0) ? limit : 5;
		List<Job> jobs = jobMapper.findRankedJobs(actualLimit);
		
		// 获取公司信息
		Set<Integer> companyIds = jobs.stream()
				.map(Job::getCompanyId)
				.collect(Collectors.toSet());
		
		Map<Integer, Company> companyMap = new HashMap<>();
		for (Integer companyId : companyIds) {
			Optional<Company> companyOpt = companyMapper.findById(companyId);
			companyOpt.ifPresent(company -> companyMap.put(companyId, company));
		}
		
		// 构建排行榜
		List<RankedJobItemVO> rankedJobs = new ArrayList<>();
		int rank = 1;
		for (Job job : jobs) {
			Company company = companyMap.get(job.getCompanyId());
			String companyName = (company != null && company.getCompanyName() != null) 
					? company.getCompanyName() : "未知公司";
			String location = job.getLocation() != null ? job.getLocation() : "未知地点";
			
			rankedJobs.add(new RankedJobItemVO(
					rank++,
					job.getId(),
					job.getTitle() != null ? job.getTitle() : "",
					companyName,
					location
			));
		}
		
		return new RankedJobsVO(rankedJobs);
	}

	@Override
	public RecentJobsVO getRecentJobs(String jobTypeFilter, Integer limit) {
		logger.info("获取近期招聘信息，类型筛选：{}，限制数量：{}", jobTypeFilter, limit);
		
		int actualLimit = (limit != null && limit > 0) ? limit : 10;
		List<Job> jobs = jobMapper.findRecentJobs(jobTypeFilter, actualLimit);
		
		List<RecentJobItemVO> jobPostings = jobs.stream()
				.map(job -> {
					// 格式化为 ISO 8601 格式，匹配 OpenAPI 示例 "2025-10-27T00:00:00Z"
					String postedAt = job.getCreatedAt() != null 
							? job.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "Z"
							: "";
					
					return new RecentJobItemVO(
							job.getId(),
							job.getTitle() != null ? job.getTitle() : "",
							postedAt
					);
				})
				.collect(Collectors.toList());
		
		return new RecentJobsVO(jobPostings);
	}

	@Override
	public ApplicationDetailVO getApplicationDetail(Integer applicationId, Integer studentUserId) {
		logger.info("获取投递详情，投递ID：{}，学生ID：{}", applicationId, studentUserId);
		
		// 查询投递记录，确保属于该学生
		Optional<Application> applicationOpt = applicationMapper.findByIdAndStudent(applicationId, studentUserId);
		if (applicationOpt.isEmpty()) {
			logger.warn("投递记录不存在或不属于该学生，投递ID：{}，学生ID：{}", applicationId, studentUserId);
			throw new BusinessException(ErrorCode.NOT_FOUND, "未找到该投递记录");
		}
		
		Application application = applicationOpt.get();
		
		// 查询岗位信息
		Optional<Job> jobOpt = jobMapper.findById(application.getJobId());
		if (jobOpt.isEmpty()) {
			logger.warn("岗位不存在，岗位ID：{}", application.getJobId());
			throw new BusinessException(ErrorCode.NOT_FOUND, "岗位不存在");
		}
		Job job = jobOpt.get();
		
		// 查询公司信息
		Optional<Company> companyOpt = companyMapper.findById(job.getCompanyId());
		if (companyOpt.isEmpty()) {
			logger.warn("公司不存在，公司ID：{}", job.getCompanyId());
			throw new BusinessException(ErrorCode.NOT_FOUND, "公司不存在");
		}
		Company company = companyOpt.get();
		
		// 构建响应VO
		ApplicationDetailVO vo = new ApplicationDetailVO();
		vo.setId(application.getId());
		
		// 查询状态详情
		// 注意：状态 20（候选人）在学生端要显示为"已投递"
		ApplicationStatus displayStatus = application.getStatus();
		if (application.getStatus() == ApplicationStatus.CANDIDATE) {
			// 状态 20 在学生端显示为状态 10（已投递）
			displayStatus = ApplicationStatus.SUBMITTED;
		}
		
		String statusName = convertStatusToChinese(displayStatus);
		if (displayStatus != null) {
			applicationStatusMapper.findByCode(displayStatus.getCode())
					.ifPresent(statusEntity -> {
						vo.setStatus(statusEntity.getName());
						vo.setStatusDetail(statusEntity.getDetail());
					});
		}
		// 如果数据库中没有找到，使用默认值
		if (vo.getStatus() == null) {
			vo.setStatus(statusName);
		}
		if (vo.getStatusDetail() == null) {
			vo.setStatusDetail(getDefaultStatusDetail(displayStatus));
		}
		
		vo.setSubmittedAt(application.getSubmittedAt());
		vo.setUpdatedAt(application.getUpdatedAt());
		
		// 设置岗位信息
		ApplicationDetailVO.JobInfo jobInfo = new ApplicationDetailVO.JobInfo();
		jobInfo.setId(job.getId());
		jobInfo.setTitle(job.getTitle() != null ? job.getTitle() : "");
		vo.setJob(jobInfo);
		
		// 设置公司信息
		ApplicationDetailVO.CompanyInfo companyInfo = new ApplicationDetailVO.CompanyInfo();
		companyInfo.setName(company.getCompanyName() != null ? company.getCompanyName() : "");
		vo.setCompany(companyInfo);
		
		return vo;
	}
	
	/**
	 * 将ApplicationStatus枚举转换为中文字符串
	 * @param status 投递状态枚举
	 * @return 中文字符串
	 */
	private String convertStatusToChinese(ApplicationStatus status) {
		if (status == null) {
			return "未知";
		}
		
		switch (status) {
			case SUBMITTED:
				return "已投递";
			case CANDIDATE:
				return "候选人";
			case INTERVIEW:
				return "面试邀请";
			case PASSED:
				return "通过";
			case REJECTED:
				return "拒绝";
			default:
				return "未知";
		}
	}

	/**
	 * 获取默认状态详情（当数据库中没有找到时使用）
	 */
	private String getDefaultStatusDetail(ApplicationStatus status) {
		if (status == null) {
			return "状态未知";
		}
		
		switch (status) {
			case SUBMITTED:
				return "您的简历已成功投递至企业，请耐心等待企业审核，后续通知将通过平台或预留联系方式发送。";
			case CANDIDATE:
				return "企业已将您加入候选人名单。若后续岗位匹配，将会通过平台或联系方式继续与您沟通。";
			case INTERVIEW:
				return "您的简历已通过初筛，请留意平台及预留的联系方式，企业将向您发送具体的面试安排和通知。";
			case PASSED:
				return "恭喜您已通过本次招聘流程！企业将通过平台或您的联系方式与您进一步沟通入职相关事宜。";
			case REJECTED:
				return "很遗憾，您的简历未能通过本次筛选。建议您继续关注其他岗位或优化简历后再次申请，祝您求职顺利。";
			default:
				return "状态详情未知";
		}
	}
	@Override
	public EventDetailVO getEventDetail(Long eventId) {
		Event event = eventMapper.findById(eventId)
				.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "活动不存在"));

		EventDetailVO vo = new EventDetailVO();

		// 1. 先批量拷贝同名属性
		BeanUtils.copyProperties(event, vo);

		// 2. 特殊处理 ID（如果 VO 字段名和 Entity 不一致，比如 eventId vs id）
		vo.setEventId(event.getId());

		// 3. 核心：处理富文本图片路径
		if (StringUtils.isNotEmpty(event.getEventSummary())) {
			vo.setEventSummary(processHtmlImageUrls(event.getEventSummary()));
		}

		// 4. 处理枚举或特定字段
		vo.setEventTargetAudience(event.getTargetAudience());

		// 5. 格式化时间
		if (event.getEventStartTime() != null) {
			vo.setEventStartTime(event.getEventStartTime().format(TIME_FORMATTER));
		}
		if (event.getEventEndTime() != null) {
			vo.setEventEndTime(event.getEventEndTime().format(TIME_FORMATTER));
		}

		return vo;
	}

	/**
	 * 处理富文本中的图片路径
	 */
	private String processHtmlImageUrls(String content) {
		if (StringUtils.isEmpty(content) || StringUtils.isEmpty(serverPrefix)) {
			return content;
		}

		// 1. 先把可能存在的 /dev-api 前缀去掉（如果有的话）
		// 这样 src="/dev-api/profile/..." 变成 src="/profile/..."
		String cleanContent = content.replace("src=\"/dev-api/", "src=\"/");

		// 2. 补全完整的服务器前缀
		// 确保 serverPrefix = http://localhost:8081
		// 结果：src="http://localhost:8081/profile/..."
		return cleanContent.replace("src=\"/profile/", "src=\"" + serverPrefix + "/profile/")
				.replace("src='/profile/", "src='" + serverPrefix + "/profile/");
	}

	@Override
	public EventListResponseVO getEventList(Integer page, Integer pageSize, String keyword) {
		int p = (page == null || page < 1) ? 1 : page;
		int size = (pageSize == null || pageSize < 1) ? 10 : pageSize;
		int offset = (p - 1) * size;

		// 执行查询
		List<Event> events = eventMapper.searchEvents(keyword, offset, size);
		long total = eventMapper.countSearchEvents(keyword);

		// 转换 VO
		List<EventListResponseVO.EventItem> items = events.stream().map(event -> {
			EventListResponseVO.EventItem item = new EventListResponseVO.EventItem();
			item.setEventId(event.getId());
			item.setEventTitle(event.getEventTitle());
			item.setEventSummary(event.getEventSummary());
			item.setEventLocation(event.getEventLocation());

			if (event.getEventStartTime() != null) {
				item.setEventStartTime(event.getEventStartTime().format(TIME_FORMATTER));
			}
			return item;
		}).collect(Collectors.toList());

		// 构建分页信息
		long totalPages = (total + size - 1) / size;
		Pagination pagination = new Pagination(total, totalPages, p, size);

		EventListResponseVO vo = new EventListResponseVO();
		vo.setEvents(items);
		vo.setPagination(pagination);

		return vo;
	}


}

