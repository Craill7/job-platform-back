package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.*;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.*;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.*;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.*;
import cn.sysu.sse.recruitment.job_platform_api.server.service.ResumeCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 简历中心服务实现
 */
@Service
public class ResumeCenterServiceImpl implements ResumeCenterService {

	private static final Logger logger = LoggerFactory.getLogger(ResumeCenterServiceImpl.class);
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

	@Autowired
	private StudentMapper studentMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private EducationExperienceMapper educationExperienceMapper;
	
	@Autowired
	private WorkExperienceMapper workExperienceMapper;
	
	@Autowired
	private ProjectExperienceMapper projectExperienceMapper;
	
	@Autowired
	private OrganizationExperienceMapper organizationExperienceMapper;
	
	@Autowired
	private CompetitionExperienceMapper competitionExperienceMapper;
	
	@Autowired
	private ResumeMapper resumeMapper;
	
	@Autowired
	private ApplicationMapper applicationMapper;

	@Override
	public ResumeDraftVO getResumeDraft(Integer studentUserId) {
		logger.info("获取简历草稿，学生ID：{}", studentUserId);
		
		ResumeDraftVO vo = new ResumeDraftVO();
		
		// 获取学生信息
		java.util.Optional<Student> studentOpt = studentMapper.findByUserId(studentUserId);
		if (studentOpt.isEmpty()) {
			throw new BusinessException(ErrorCode.NOT_FOUND, "学生信息不存在");
		}
		Student student = studentOpt.get();
		
		// 获取用户信息（邮箱）
		java.util.Optional<User> userOpt = userMapper.findById(studentUserId);
		
		// 构建Profile
		ResumeDraftVO.ProfileVO profile = new ResumeDraftVO.ProfileVO();
		profile.setFullName(student.getFullName());
		if (student.getDateOfBirth() != null) {
			profile.setDateOfBirth(student.getDateOfBirth().format(DATE_FORMATTER));
		}
		if (userOpt.isPresent()) {
			profile.setEmail(userOpt.get().getEmail());
		}
		profile.setGender(student.getGender() != null ? (student.getGender() == 0 ? "male" : "female") : null);
		if (student.getJobSeekingStatus() != null) {
			String[] statuses = {"在校-暂不考虑", "在校-寻求实习", "应届-寻求实习", "应届-寻求校招"};
			if (student.getJobSeekingStatus() >= 0 && student.getJobSeekingStatus() < statuses.length) {
				profile.setJobSeekingStatus(statuses[student.getJobSeekingStatus()]);
			}
		}
		profile.setPhoneNumber(student.getPhoneNumber());
		profile.setAvatarUrl(student.getAvatarUrl());
		vo.setProfile(profile);
		
		// 获取教育经历（主档案，通常只有一条）
		List<EducationExperience> educations = educationExperienceMapper.findByStudentUserId(studentUserId);
		if (!educations.isEmpty()) {
			EducationExperience edu = educations.get(0);
			ResumeDraftVO.EducationVO education = new ResumeDraftVO.EducationVO();
			if (edu.getDegreeLevel() != null) {
				String[] degrees = {"bachelor", "master", "doctor"};
				if (edu.getDegreeLevel() >= 0 && edu.getDegreeLevel() < degrees.length) {
					education.setDegree(degrees[edu.getDegreeLevel()]);
				}
			}
			education.setSchoolName(edu.getSchoolName());
			education.setMajor(edu.getMajor());
			education.setMajorRank(edu.getMajorRank());
			if (edu.getStartDate() != null) {
				education.setStartDate(edu.getStartDate().format(DATE_FORMATTER));
			}
			if (edu.getEndDate() != null) {
				education.setEndDate(edu.getEndDate().format(DATE_FORMATTER));
			}
			vo.setEducation(education);
		}
		
		// 技能摘要
		vo.setSkillsSummary(student.getSkillsSummary());
		
		// 工作经历
		List<WorkExperience> workExps = workExperienceMapper.findByStudentUserId(studentUserId);
		vo.setWorkExperiences(workExps.stream().map(exp -> {
			ResumeDraftVO.WorkExperienceVO wevo = new ResumeDraftVO.WorkExperienceVO();
			wevo.setId(exp.getId());
			wevo.setCompanyName(exp.getCompanyName());
			wevo.setPositionTitle(exp.getPositionTitle());
			if (exp.getStartDate() != null) {
				wevo.setStartDate(exp.getStartDate().format(DATE_FORMATTER));
			}
			if (exp.getEndDate() != null) {
				wevo.setEndDate(exp.getEndDate().format(DATE_FORMATTER));
			}
			wevo.setDescription(exp.getDescription());
			return wevo;
		}).collect(Collectors.toList()));
		
		// 项目经历
		List<ProjectExperience> projects = projectExperienceMapper.findByStudentUserId(studentUserId);
		vo.setProjects(projects.stream().map(proj -> {
			ResumeDraftVO.ProjectExperienceVO pvo = new ResumeDraftVO.ProjectExperienceVO();
			pvo.setId(proj.getId());
			pvo.setProjectName(proj.getProjectName());
			pvo.setRole(proj.getRole());
			pvo.setProjectLink(proj.getProjectLink());
			if (proj.getStartDate() != null) {
				pvo.setStartDate(proj.getStartDate().format(DATE_FORMATTER));
			}
			if (proj.getEndDate() != null) {
				pvo.setEndDate(proj.getEndDate().format(DATE_FORMATTER));
			}
			pvo.setDescription(proj.getDescription());
			return pvo;
		}).collect(Collectors.toList()));
		
		// 社团组织经历
		List<OrganizationExperience> orgs = organizationExperienceMapper.findByStudentUserId(studentUserId);
		vo.setOrganizations(orgs.stream().map(org -> {
			ResumeDraftVO.OrganizationExperienceVO ovo = new ResumeDraftVO.OrganizationExperienceVO();
			ovo.setId(org.getId());
			ovo.setOrganizationName(org.getOrganizationName());
			ovo.setRole(org.getRole());
			if (org.getStartDate() != null) {
				ovo.setStartDate(org.getStartDate().format(DATE_FORMATTER));
			}
			if (org.getEndDate() != null) {
				ovo.setEndDate(org.getEndDate().format(DATE_FORMATTER));
			}
			ovo.setDescription(org.getDescription());
			return ovo;
		}).collect(Collectors.toList()));
		
		// 竞赛经历
		List<CompetitionExperience> competitions = competitionExperienceMapper.findByStudentUserId(studentUserId);
		vo.setCompetitions(competitions.stream().map(comp -> {
			ResumeDraftVO.CompetitionExperienceVO cvo = new ResumeDraftVO.CompetitionExperienceVO();
			cvo.setId(comp.getId());
			cvo.setCompetitionName(comp.getCompetitionName());
			cvo.setRole(comp.getRole());
			cvo.setAward(comp.getAward());
			cvo.setDate(comp.getDate());
			return cvo;
		}).collect(Collectors.toList()));
		
		return vo;
	}

	@Override
	@Transactional
	public SkillsResponseVO updateSkills(Integer studentUserId, UpdateSkillsDTO dto) {
		logger.info("更新技能信息，学生ID：{}", studentUserId);
		
		java.util.Optional<Student> studentOpt = studentMapper.findByUserId(studentUserId);
		Student student;
		
		if (studentOpt.isEmpty()) {
			// 容错处理：如果学生记录不存在，自动创建
			logger.warn("学生记录不存在，自动创建，学生ID：{}", studentUserId);
			student = new Student();
			student.setUserId(studentUserId);
			// student_id 现在可以为空，用户后续可以填写
			student.setSkillsSummary(dto.getSkillsSummary());
			studentMapper.insert(student);
			logger.info("已自动创建学生记录，学生ID：{}", studentUserId);
		} else {
			student = studentOpt.get();
			student.setSkillsSummary(dto.getSkillsSummary());
			studentMapper.update(student);
		}
		
		SkillsResponseVO vo = new SkillsResponseVO();
		vo.setSkillsSummary(dto.getSkillsSummary());
		return vo;
	}

	@Override
	@Transactional
	public WorkExperienceResponseVO createWorkExperience(Integer studentUserId, WorkExperienceDTO dto) {
		logger.info("新增工作经历，学生ID：{}", studentUserId);
		
		WorkExperience exp = new WorkExperience();
		exp.setStudentUserId(studentUserId);
		exp.setCompanyName(dto.getCompanyName());
		exp.setPositionTitle(dto.getPositionTitle());
		exp.setStartDate(dto.getStartDate());
		exp.setEndDate(dto.getEndDate());
		exp.setDescription(dto.getDescription());
		
		workExperienceMapper.insert(exp);
		
		return convertToWorkExperienceVO(exp);
	}

	@Override
	@Transactional
	public WorkExperienceResponseVO updateWorkExperience(Integer studentUserId, Long id, WorkExperienceDTO dto) {
		logger.info("更新工作经历，学生ID：{}，经历ID：{}", studentUserId, id);
		
		java.util.Optional<WorkExperience> expOpt = workExperienceMapper.findById(id);
		if (expOpt.isEmpty() || !expOpt.get().getStudentUserId().equals(studentUserId)) {
			throw new BusinessException(ErrorCode.NOT_FOUND, "工作经历不存在");
		}
		
		WorkExperience exp = expOpt.get();
		exp.setCompanyName(dto.getCompanyName());
		exp.setPositionTitle(dto.getPositionTitle());
		exp.setStartDate(dto.getStartDate());
		exp.setEndDate(dto.getEndDate());
		exp.setDescription(dto.getDescription());
		
		workExperienceMapper.update(exp);
		
		return convertToWorkExperienceVO(exp);
	}

	@Override
	@Transactional
	public void deleteWorkExperience(Integer studentUserId, Long id) {
		logger.info("删除工作经历，学生ID：{}，经历ID：{}", studentUserId, id);
		
		java.util.Optional<WorkExperience> expOpt = workExperienceMapper.findById(id);
		if (expOpt.isEmpty() || !expOpt.get().getStudentUserId().equals(studentUserId)) {
			throw new BusinessException(ErrorCode.NOT_FOUND, "工作经历不存在");
		}
		
		workExperienceMapper.deleteById(id);
	}

	@Override
	@Transactional
	public ProjectExperienceResponseVO createProjectExperience(Integer studentUserId, ProjectExperienceDTO dto) {
		logger.info("新增项目经历，学生ID：{}", studentUserId);
		
		ProjectExperience proj = new ProjectExperience();
		proj.setStudentUserId(studentUserId);
		proj.setProjectName(dto.getProjectName());
		proj.setRole(dto.getRole());
		proj.setProjectLink(dto.getProjectLink());
		proj.setStartDate(dto.getStartDate());
		proj.setEndDate(dto.getEndDate());
		proj.setDescription(dto.getDescription());
		
		projectExperienceMapper.insert(proj);
		
		return convertToProjectExperienceVO(proj);
	}

	@Override
	@Transactional
	public ProjectExperienceResponseVO updateProjectExperience(Integer studentUserId, Long id, ProjectExperienceDTO dto) {
		logger.info("更新项目经历，学生ID：{}，经历ID：{}", studentUserId, id);
		
		java.util.Optional<ProjectExperience> projOpt = projectExperienceMapper.findById(id);
		if (projOpt.isEmpty() || !projOpt.get().getStudentUserId().equals(studentUserId)) {
			throw new BusinessException(ErrorCode.NOT_FOUND, "项目经历不存在");
		}
		
		ProjectExperience proj = projOpt.get();
		proj.setProjectName(dto.getProjectName());
		proj.setRole(dto.getRole());
		proj.setProjectLink(dto.getProjectLink());
		proj.setStartDate(dto.getStartDate());
		proj.setEndDate(dto.getEndDate());
		proj.setDescription(dto.getDescription());
		
		projectExperienceMapper.update(proj);
		
		return convertToProjectExperienceVO(proj);
	}

	@Override
	@Transactional
	public void deleteProjectExperience(Integer studentUserId, Long id) {
		logger.info("删除项目经历，学生ID：{}，经历ID：{}", studentUserId, id);
		
		java.util.Optional<ProjectExperience> projOpt = projectExperienceMapper.findById(id);
		if (projOpt.isEmpty() || !projOpt.get().getStudentUserId().equals(studentUserId)) {
			throw new BusinessException(ErrorCode.NOT_FOUND, "项目经历不存在");
		}
		
		projectExperienceMapper.deleteById(id);
	}

	@Override
	@Transactional
	public OrganizationExperienceResponseVO createOrganizationExperience(Integer studentUserId, OrganizationExperienceDTO dto) {
		logger.info("新增社团组织经历，学生ID：{}", studentUserId);
		
		OrganizationExperience org = new OrganizationExperience();
		org.setStudentUserId(studentUserId);
		org.setOrganizationName(dto.getOrganizationName());
		org.setRole(dto.getRole());
		org.setStartDate(dto.getStartDate());
		org.setEndDate(dto.getEndDate());
		org.setDescription(dto.getDescription());
		
		organizationExperienceMapper.insert(org);
		
		return convertToOrganizationExperienceVO(org);
	}

	@Override
	@Transactional
	public OrganizationExperienceResponseVO updateOrganizationExperience(Integer studentUserId, Long id, OrganizationExperienceDTO dto) {
		logger.info("更新社团组织经历，学生ID：{}，经历ID：{}", studentUserId, id);
		
		java.util.Optional<OrganizationExperience> orgOpt = organizationExperienceMapper.findById(id);
		if (orgOpt.isEmpty() || !orgOpt.get().getStudentUserId().equals(studentUserId)) {
			throw new BusinessException(ErrorCode.NOT_FOUND, "组织经历不存在");
		}
		
		OrganizationExperience org = orgOpt.get();
		org.setOrganizationName(dto.getOrganizationName());
		org.setRole(dto.getRole());
		org.setStartDate(dto.getStartDate());
		org.setEndDate(dto.getEndDate());
		org.setDescription(dto.getDescription());
		
		organizationExperienceMapper.update(org);
		
		return convertToOrganizationExperienceVO(org);
	}

	@Override
	@Transactional
	public void deleteOrganizationExperience(Integer studentUserId, Long id) {
		logger.info("删除社团组织经历，学生ID：{}，经历ID：{}", studentUserId, id);
		
		java.util.Optional<OrganizationExperience> orgOpt = organizationExperienceMapper.findById(id);
		if (orgOpt.isEmpty() || !orgOpt.get().getStudentUserId().equals(studentUserId)) {
			throw new BusinessException(ErrorCode.NOT_FOUND, "组织经历不存在");
		}
		
		organizationExperienceMapper.deleteById(id);
	}

	@Override
	@Transactional
	public CompetitionExperienceResponseVO createCompetitionExperience(Integer studentUserId, CompetitionExperienceDTO dto) {
		logger.info("新增竞赛经历，学生ID：{}", studentUserId);
		
		CompetitionExperience comp = new CompetitionExperience();
		comp.setStudentUserId(studentUserId);
		comp.setCompetitionName(dto.getCompetitionName());
		comp.setRole(dto.getRole());
		comp.setAward(dto.getAward());
		comp.setDate(dto.getDate());
		
		competitionExperienceMapper.insert(comp);
		
		return convertToCompetitionExperienceVO(comp);
	}

	@Override
	@Transactional
	public CompetitionExperienceResponseVO updateCompetitionExperience(Integer studentUserId, Long id, CompetitionExperienceDTO dto) {
		logger.info("更新竞赛经历，学生ID：{}，经历ID：{}", studentUserId, id);
		
		java.util.Optional<CompetitionExperience> compOpt = competitionExperienceMapper.findById(id);
		if (compOpt.isEmpty() || !compOpt.get().getStudentUserId().equals(studentUserId)) {
			throw new BusinessException(ErrorCode.NOT_FOUND, "竞赛经历不存在");
		}
		
		CompetitionExperience comp = compOpt.get();
		comp.setCompetitionName(dto.getCompetitionName());
		comp.setRole(dto.getRole());
		comp.setAward(dto.getAward());
		comp.setDate(dto.getDate());
		
		competitionExperienceMapper.update(comp);
		
		return convertToCompetitionExperienceVO(comp);
	}

	@Override
	@Transactional
	public void deleteCompetitionExperience(Integer studentUserId, Long id) {
		logger.info("删除竞赛经历，学生ID：{}，经历ID：{}", studentUserId, id);
		
		java.util.Optional<CompetitionExperience> compOpt = competitionExperienceMapper.findById(id);
		if (compOpt.isEmpty() || !compOpt.get().getStudentUserId().equals(studentUserId)) {
			throw new BusinessException(ErrorCode.NOT_FOUND, "竞赛经历不存在");
		}
		
		competitionExperienceMapper.deleteById(id);
	}

	@Override
	@Transactional
	public TemplateResponseVO setTemplate(Integer studentUserId, SetTemplateDTO dto) {
		logger.info("设置简历模板，学生ID：{}，模板ID：{}", studentUserId, dto.getTemplateId());
		
		java.util.Optional<Student> studentOpt = studentMapper.findByUserId(studentUserId);
		if (studentOpt.isEmpty()) {
			throw new BusinessException(ErrorCode.NOT_FOUND, "学生信息不存在");
		}
		
		Student student = studentOpt.get();
		student.setCurrentTemplateId(dto.getTemplateId().longValue());
		studentMapper.update(student);
		
		TemplateResponseVO vo = new TemplateResponseVO();
		vo.setCurrentTemplateId(dto.getTemplateId());
		// TODO: 从模板表查询模板名称和预览图URL
		vo.setTemplateName("模板" + dto.getTemplateId());
		vo.setTemplatePreviewUrl(null);
		
		return vo;
	}

	@Override
	public List<ResumeFileVO> getResumeFiles(Integer studentUserId) {
		logger.info("获取简历文件列表，学生ID：{}", studentUserId);
		
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

	@Override
	@Transactional
	public ResumeFileVO uploadResumeFile(Integer studentUserId, MultipartFile file, String usage, Integer templateId) {
		logger.info("上传简历文件，学生ID：{}，文件名：{}，用途：{}", studentUserId, file.getOriginalFilename(), usage);
		
		if (file == null || file.isEmpty()) {
			throw new BusinessException(ErrorCode.BAD_REQUEST, "文件不能为空");
		}
		
		// TODO: 实现文件上传到云存储（OSS/CDN）
		// 这里先返回一个模拟的URL，实际需要集成云存储服务
		String fileUrl = "https://cdn.example.com/resumes/" + System.currentTimeMillis() + ".pdf";
		String fileName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "resume.pdf";
		
		Resume resume = new Resume();
		resume.setStudentUserId(studentUserId);
		resume.setFileName(fileName);
		resume.setFileUrl(fileUrl);
		resume.setFileSize(file.getSize());
		resume.setUsageType(usage != null ? usage : "resume_pdf");
		resume.setTemplateId(templateId);
		
		resumeMapper.insert(resume);
		
		ResumeFileVO vo = new ResumeFileVO();
		vo.setId(resume.getId().intValue());
		vo.setFileName(resume.getFileName());
		vo.setFileUrl(resume.getFileUrl());
		vo.setFileSize(resume.getFileSize() != null ? resume.getFileSize().intValue() : null);
		vo.setTemplateId(resume.getTemplateId());
		vo.setUsage(resume.getUsageType());
		vo.setUploadedAt(resume.getUploadedAt());
		
		return vo;
	}

	@Override
	@Transactional
	public void deleteResumeFile(Integer studentUserId, Long id) {
		logger.info("删除简历文件，学生ID：{}，文件ID：{}", studentUserId, id);
		
		java.util.Optional<Resume> resumeOpt = resumeMapper.findById(id);
		if (resumeOpt.isEmpty() || !resumeOpt.get().getStudentUserId().equals(studentUserId)) {
			throw new BusinessException(ErrorCode.NOT_FOUND, "简历文件不存在");
		}
		
		// 检查是否有投递记录使用该简历
		int applicationCount = applicationMapper.countByResumeId(id);
		if (applicationCount > 0) {
			String message = String.format(
					"无法删除该简历文件。该简历已被 %d 条投递记录使用，删除后会影响相关投递记录的数据完整性。如需删除，请先取消或删除所有使用此简历的投递记录。",
					applicationCount
			);
			throw new BusinessException(ErrorCode.BAD_REQUEST, message);
		}
		
		// TODO: 删除云存储中的文件
		// 这里只删除数据库记录，实际需要同时删除云存储文件
		
		resumeMapper.deleteById(id);
		logger.info("简历文件删除成功，文件ID：{}", id);
	}

	// 辅助方法：实体转VO
	private WorkExperienceResponseVO convertToWorkExperienceVO(WorkExperience exp) {
		WorkExperienceResponseVO vo = new WorkExperienceResponseVO();
		vo.setId(exp.getId());
		vo.setCompanyName(exp.getCompanyName());
		vo.setPositionTitle(exp.getPositionTitle());
		vo.setStartDate(exp.getStartDate());
		vo.setEndDate(exp.getEndDate());
		vo.setDescription(exp.getDescription());
		return vo;
	}

	private ProjectExperienceResponseVO convertToProjectExperienceVO(ProjectExperience proj) {
		ProjectExperienceResponseVO vo = new ProjectExperienceResponseVO();
		vo.setId(proj.getId());
		vo.setProjectName(proj.getProjectName());
		vo.setRole(proj.getRole());
		vo.setProjectLink(proj.getProjectLink());
		vo.setStartDate(proj.getStartDate());
		vo.setEndDate(proj.getEndDate());
		vo.setDescription(proj.getDescription());
		return vo;
	}

	private OrganizationExperienceResponseVO convertToOrganizationExperienceVO(OrganizationExperience org) {
		OrganizationExperienceResponseVO vo = new OrganizationExperienceResponseVO();
		vo.setId(org.getId());
		vo.setOrganizationName(org.getOrganizationName());
		vo.setRole(org.getRole());
		vo.setStartDate(org.getStartDate());
		vo.setEndDate(org.getEndDate());
		vo.setDescription(org.getDescription());
		return vo;
	}

	private CompetitionExperienceResponseVO convertToCompetitionExperienceVO(CompetitionExperience comp) {
		CompetitionExperienceResponseVO vo = new CompetitionExperienceResponseVO();
		vo.setId(comp.getId());
		vo.setCompetitionName(comp.getCompetitionName());
		vo.setRole(comp.getRole());
		vo.setAward(comp.getAward());
		vo.setDate(comp.getDate());
		return vo;
	}
}

