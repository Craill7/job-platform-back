package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.common.enums.ApplicationStatus;
import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.*;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.*;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.*;
import cn.sysu.sse.recruitment.job_platform_api.server.service.StudentDashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
		vo.setStatus(convertStatusToChinese(application.getStatus()));
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
				return "候选"; // OpenAPI文档中没有此状态，但保留处理
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
}

