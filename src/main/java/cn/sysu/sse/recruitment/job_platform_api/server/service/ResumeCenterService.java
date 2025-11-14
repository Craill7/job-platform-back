package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.*;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.*;

/**
 * 简历中心服务接口
 */
public interface ResumeCenterService {
	
	/**
	 * 获取当前简历草稿
	 * @param studentUserId 学生用户ID
	 * @return 简历草稿数据
	 */
	ResumeDraftVO getResumeDraft(Integer studentUserId);
	
	/**
	 * 更新技能信息
	 * @param studentUserId 学生用户ID
	 * @param dto 技能更新请求
	 * @return 更新后的技能信息
	 */
	SkillsResponseVO updateSkills(Integer studentUserId, UpdateSkillsDTO dto);
	
	/**
	 * 新增工作经历
	 * @param studentUserId 学生用户ID
	 * @param dto 工作经历数据
	 * @return 创建的工作经历
	 */
	WorkExperienceResponseVO createWorkExperience(Integer studentUserId, WorkExperienceDTO dto);
	
	/**
	 * 更新工作经历
	 * @param studentUserId 学生用户ID
	 * @param id 工作经历ID
	 * @param dto 工作经历数据
	 * @return 更新后的工作经历
	 */
	WorkExperienceResponseVO updateWorkExperience(Integer studentUserId, Long id, WorkExperienceDTO dto);
	
	/**
	 * 删除工作经历
	 * @param studentUserId 学生用户ID
	 * @param id 工作经历ID
	 */
	void deleteWorkExperience(Integer studentUserId, Long id);
	
	/**
	 * 新增项目经历
	 * @param studentUserId 学生用户ID
	 * @param dto 项目经历数据
	 * @return 创建的项目经历
	 */
	ProjectExperienceResponseVO createProjectExperience(Integer studentUserId, ProjectExperienceDTO dto);
	
	/**
	 * 更新项目经历
	 * @param studentUserId 学生用户ID
	 * @param id 项目经历ID
	 * @param dto 项目经历数据
	 * @return 更新后的项目经历
	 */
	ProjectExperienceResponseVO updateProjectExperience(Integer studentUserId, Long id, ProjectExperienceDTO dto);
	
	/**
	 * 删除项目经历
	 * @param studentUserId 学生用户ID
	 * @param id 项目经历ID
	 */
	void deleteProjectExperience(Integer studentUserId, Long id);
	
	/**
	 * 新增社团组织经历
	 * @param studentUserId 学生用户ID
	 * @param dto 组织经历数据
	 * @return 创建的组织经历
	 */
	OrganizationExperienceResponseVO createOrganizationExperience(Integer studentUserId, OrganizationExperienceDTO dto);
	
	/**
	 * 更新社团组织经历
	 * @param studentUserId 学生用户ID
	 * @param id 组织经历ID
	 * @param dto 组织经历数据
	 * @return 更新后的组织经历
	 */
	OrganizationExperienceResponseVO updateOrganizationExperience(Integer studentUserId, Long id, OrganizationExperienceDTO dto);
	
	/**
	 * 删除社团组织经历
	 * @param studentUserId 学生用户ID
	 * @param id 组织经历ID
	 */
	void deleteOrganizationExperience(Integer studentUserId, Long id);
	
	/**
	 * 新增竞赛经历
	 * @param studentUserId 学生用户ID
	 * @param dto 竞赛经历数据
	 * @return 创建的竞赛经历
	 */
	CompetitionExperienceResponseVO createCompetitionExperience(Integer studentUserId, CompetitionExperienceDTO dto);
	
	/**
	 * 更新竞赛经历
	 * @param studentUserId 学生用户ID
	 * @param id 竞赛经历ID
	 * @param dto 竞赛经历数据
	 * @return 更新后的竞赛经历
	 */
	CompetitionExperienceResponseVO updateCompetitionExperience(Integer studentUserId, Long id, CompetitionExperienceDTO dto);
	
	/**
	 * 删除竞赛经历
	 * @param studentUserId 学生用户ID
	 * @param id 竞赛经历ID
	 */
	void deleteCompetitionExperience(Integer studentUserId, Long id);
	
	/**
	 * 设置简历模板
	 * @param studentUserId 学生用户ID
	 * @param dto 模板设置请求
	 * @return 模板信息
	 */
	TemplateResponseVO setTemplate(Integer studentUserId, SetTemplateDTO dto);
	
	/**
	 * 获取简历文件列表
	 * @param studentUserId 学生用户ID
	 * @return 简历文件列表
	 */
	java.util.List<ResumeFileVO> getResumeFiles(Integer studentUserId);
	
	/**
	 * 上传简历文件
	 * @param studentUserId 学生用户ID
	 * @param file 文件
	 * @param usage 用途
	 * @param templateId 模板ID（可选）
	 * @return 上传后的文件信息
	 */
	ResumeFileVO uploadResumeFile(Integer studentUserId, org.springframework.web.multipart.MultipartFile file, String usage, Integer templateId);
	
	/**
	 * 删除简历文件
	 * @param studentUserId 学生用户ID
	 * @param id 文件ID
	 */
	void deleteResumeFile(Integer studentUserId, Long id);
	
}

