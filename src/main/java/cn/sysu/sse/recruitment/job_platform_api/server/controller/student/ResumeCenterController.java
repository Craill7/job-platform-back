package cn.sysu.sse.recruitment.job_platform_api.server.controller.student;

import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import cn.sysu.sse.recruitment.job_platform_api.common.result.ApiResponse;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.*;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.*;
import cn.sysu.sse.recruitment.job_platform_api.server.service.ResumeCenterService;
import cn.sysu.sse.recruitment.job_platform_api.common.util.Base64MultipartFile;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

/**
 * 简历中心控制器
 */
@RestController
@RequestMapping("/resume-center")
public class ResumeCenterController {

	private static final Logger logger = LoggerFactory.getLogger(ResumeCenterController.class);

	@Autowired
	private ResumeCenterService resumeCenterService;

	/**
	 * 获取当前简历草稿
	 */
	@GetMapping("/resume_draft")
	public ApiResponse<ResumeDraftVO> getResumeDraft(Authentication authentication) {
		logger.info("收到获取简历草稿请求");
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		ResumeDraftVO result = resumeCenterService.getResumeDraft(studentUserId);
		return ApiResponse.success(result);
	}

	/**
	 * 更新技能信息
	 */
	@PutMapping("/resume_draft/skills")
	public ApiResponse<SkillsResponseVO> updateSkills(
			@Valid @RequestBody UpdateSkillsDTO dto,
			Authentication authentication) {
		logger.info("收到更新技能信息请求");
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		SkillsResponseVO result = resumeCenterService.updateSkills(studentUserId, dto);
		return ApiResponse.success(result);
	}

	/**
	 * 新增工作经历
	 */
	@PostMapping("/resume_draft/work_experiences")
	public ApiResponse<WorkExperienceResponseVO> createWorkExperience(
			@Valid @RequestBody WorkExperienceDTO dto,
			Authentication authentication) {
		logger.info("收到新增工作经历请求");
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		WorkExperienceResponseVO result = resumeCenterService.createWorkExperience(studentUserId, dto);
		return ApiResponse.success(result);
	}

	/**
	 * 更新工作经历
	 */
	@PutMapping("/resume_draft/work_experiences/{id}")
	public ApiResponse<WorkExperienceResponseVO> updateWorkExperience(
			@PathVariable("id") Long id,
			@Valid @RequestBody WorkExperienceDTO dto,
			Authentication authentication) {
		logger.info("收到更新工作经历请求，ID：{}", id);
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		WorkExperienceResponseVO result = resumeCenterService.updateWorkExperience(studentUserId, id, dto);
		return ApiResponse.success(result);
	}

	/**
	 * 删除工作经历
	 */
	@DeleteMapping("/resume_draft/work_experiences/{id}")
	public ApiResponse<Void> deleteWorkExperience(
			@PathVariable("id") Long id,
			Authentication authentication) {
		logger.info("收到删除工作经历请求，ID：{}", id);
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		resumeCenterService.deleteWorkExperience(studentUserId, id);
		return ApiResponse.success(null);
	}

	/**
	 * 新增项目经历
	 */
	@PostMapping("/resume_draft/projects")
	public ApiResponse<ProjectExperienceResponseVO> createProjectExperience(
			@Valid @RequestBody ProjectExperienceDTO dto,
			Authentication authentication) {
		logger.info("收到新增项目经历请求");
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		ProjectExperienceResponseVO result = resumeCenterService.createProjectExperience(studentUserId, dto);
		return ApiResponse.success(result);
	}

	/**
	 * 更新项目经历
	 */
	@PutMapping("/resume_draft/projects/{id}")
	public ApiResponse<ProjectExperienceResponseVO> updateProjectExperience(
			@PathVariable("id") Long id,
			@Valid @RequestBody ProjectExperienceDTO dto,
			Authentication authentication) {
		logger.info("收到更新项目经历请求，ID：{}", id);
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		ProjectExperienceResponseVO result = resumeCenterService.updateProjectExperience(studentUserId, id, dto);
		return ApiResponse.success(result);
	}

	/**
	 * 删除项目经历
	 */
	@DeleteMapping("/resume_draft/projects/{id}")
	public ApiResponse<Void> deleteProjectExperience(
			@PathVariable("id") Long id,
			Authentication authentication) {
		logger.info("收到删除项目经历请求，ID：{}", id);
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		resumeCenterService.deleteProjectExperience(studentUserId, id);
		return ApiResponse.success(null);
	}

	/**
	 * 新增社团组织经历
	 */
	@PostMapping("/resume_draft/organizations")
	public ApiResponse<OrganizationExperienceResponseVO> createOrganizationExperience(
			@Valid @RequestBody OrganizationExperienceDTO dto,
			Authentication authentication) {
		logger.info("收到新增社团组织经历请求");
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		OrganizationExperienceResponseVO result = resumeCenterService.createOrganizationExperience(studentUserId, dto);
		return ApiResponse.success(result);
	}

	/**
	 * 更新社团组织经历
	 */
	@PutMapping("/resume_draft/organizations/{id}")
	public ApiResponse<OrganizationExperienceResponseVO> updateOrganizationExperience(
			@PathVariable("id") Long id,
			@Valid @RequestBody OrganizationExperienceDTO dto,
			Authentication authentication) {
		logger.info("收到更新社团组织经历请求，ID：{}", id);
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		OrganizationExperienceResponseVO result = resumeCenterService.updateOrganizationExperience(studentUserId, id, dto);
		return ApiResponse.success(result);
	}

	/**
	 * 删除社团组织经历
	 */
	@DeleteMapping("/resume_draft/organizations/{id}")
	public ApiResponse<Void> deleteOrganizationExperience(
			@PathVariable("id") Long id,
			Authentication authentication) {
		logger.info("收到删除社团组织经历请求，ID：{}", id);
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		resumeCenterService.deleteOrganizationExperience(studentUserId, id);
		return ApiResponse.success(null);
	}

	/**
	 * 新增竞赛经历
	 */
	@PostMapping("/resume_draft/competitions")
	public ApiResponse<CompetitionExperienceResponseVO> createCompetitionExperience(
			@Valid @RequestBody CompetitionExperienceDTO dto,
			Authentication authentication) {
		logger.info("收到新增竞赛经历请求");
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		CompetitionExperienceResponseVO result = resumeCenterService.createCompetitionExperience(studentUserId, dto);
		return ApiResponse.success(result);
	}

	/**
	 * 更新竞赛经历
	 */
	@PutMapping("/resume_draft/competitions/{id}")
	public ApiResponse<CompetitionExperienceResponseVO> updateCompetitionExperience(
			@PathVariable("id") Long id,
			@Valid @RequestBody CompetitionExperienceDTO dto,
			Authentication authentication) {
		logger.info("收到更新竞赛经历请求，ID：{}", id);
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		CompetitionExperienceResponseVO result = resumeCenterService.updateCompetitionExperience(studentUserId, id, dto);
		return ApiResponse.success(result);
	}

	/**
	 * 删除竞赛经历
	 */
	@DeleteMapping("/resume_draft/competitions/{id}")
	public ApiResponse<Void> deleteCompetitionExperience(
			@PathVariable("id") Long id,
			Authentication authentication) {
		logger.info("收到删除竞赛经历请求，ID：{}", id);
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		resumeCenterService.deleteCompetitionExperience(studentUserId, id);
		return ApiResponse.success(null);
	}

	/**
	 * 设置简历模板
	 */
	@PatchMapping("/resume_draft/template")
	public ApiResponse<TemplateResponseVO> setTemplate(
			@Valid @RequestBody SetTemplateDTO dto,
			Authentication authentication) {
		logger.info("收到设置简历模板请求，模板ID：{}", dto.getTemplateId());
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		TemplateResponseVO result = resumeCenterService.setTemplate(studentUserId, dto);
		return ApiResponse.success(result);
	}

	/**
	 * 获取我的简历列表
	 */
	@GetMapping("/resume_files")
	public ApiResponse<List<ResumeFileVO>> getResumeFiles(Authentication authentication) {
		logger.info("收到获取简历文件列表请求");
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		List<ResumeFileVO> result = resumeCenterService.getResumeFiles(studentUserId);
		return ApiResponse.success(result);
	}

	/**
	 * 上传PDF简历
	 * 接收JSON请求体，包含base64编码的文件内容
	 */
	@PostMapping("/resume_files/upload")
	public ApiResponse<ResumeFileVO> uploadResumeFile(
			@Valid @RequestBody UploadResumeFileDTO dto,
			Authentication authentication) {
		logger.info("收到上传简历文件请求，用途：{}，模板ID：{}", dto.getUsage(), dto.getTemplateId());
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		// 解析base64字符串
		MultipartFile file;
		try {
			// 处理base64字符串（可能包含data:application/pdf;base64,前缀）
			String base64Content = dto.getFile();
			if (base64Content == null || base64Content.trim().isEmpty()) {
				throw new BusinessException(ErrorCode.BAD_REQUEST, "文件内容不能为空");
			}
			
			// 移除data URL前缀（如果存在）
			if (base64Content.contains(",")) {
				base64Content = base64Content.substring(base64Content.indexOf(",") + 1);
			}
			
			// 清理所有空白字符（空格、换行符、制表符等）
			base64Content = base64Content.replaceAll("\\s+", "");
			
			byte[] fileBytes = Base64.getDecoder().decode(base64Content);
			if (fileBytes.length == 0) {
				throw new BusinessException(ErrorCode.BAD_REQUEST, "文件内容为空");
			}
			
			String fileName = "resume.pdf"; // 默认文件名，实际应该从请求中获取或根据时间生成
			file = new Base64MultipartFile(fileBytes, "file", fileName, "application/pdf");
		} catch (IllegalArgumentException e) {
			logger.error("base64解码失败", e);
			throw new BusinessException(ErrorCode.BAD_REQUEST, "文件格式错误，base64解码失败：" + e.getMessage());
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			logger.error("处理文件失败", e);
			throw new BusinessException(ErrorCode.BAD_REQUEST, "文件处理失败：" + e.getMessage());
		}
		
		ResumeFileVO result = resumeCenterService.uploadResumeFile(
				studentUserId, 
				file, 
				dto.getUsage() != null ? dto.getUsage() : "resume_pdf",
				dto.getTemplateId()
		);
		
		return ApiResponse.of(200, "File uploaded successfully", result);
	}

	/**
	 * 删除PDF简历
	 */
	@DeleteMapping("/resume_files/{id}")
	public ApiResponse<Void> deleteResumeFile(
			@PathVariable("id") Long id,
			Authentication authentication) {
		logger.info("收到删除简历文件请求，文件ID：{}", id);
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		resumeCenterService.deleteResumeFile(studentUserId, id);
		return ApiResponse.success(null);
	}

	/**
	 * 从Authentication中获取学生用户ID
	 */
	private Integer getStudentUserId(Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		
		try {
			if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails) {
				org.springframework.security.core.userdetails.UserDetails userDetails = 
						(org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();
				String userIdStr = userDetails.getUsername();
				return Integer.parseInt(userIdStr);
			}
			String userIdStr = authentication.getName();
			return Integer.parseInt(userIdStr);
		} catch (Exception e) {
			logger.warn("获取用户ID失败", e);
			return null;
		}
	}
}

