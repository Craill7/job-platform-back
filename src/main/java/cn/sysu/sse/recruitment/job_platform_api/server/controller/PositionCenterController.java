package cn.sysu.sse.recruitment.job_platform_api.server.controller;

import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import cn.sysu.sse.recruitment.job_platform_api.common.result.ApiResponse;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.ApplicationSubmitDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.JobListQueryDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.UploadResumeFileDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.*;
import cn.sysu.sse.recruitment.job_platform_api.server.service.PositionCenterService;
import cn.sysu.sse.recruitment.job_platform_api.server.service.ResumeCenterService;
import cn.sysu.sse.recruitment.job_platform_api.server.util.Base64MultipartFile;
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
 * 求职中心控制器
 */
@RestController
@RequestMapping("/position-center")
public class PositionCenterController {

	private static final Logger logger = LoggerFactory.getLogger(PositionCenterController.class);

	@Autowired
	private PositionCenterService positionCenterService;
	
	@Autowired
	private ResumeCenterService resumeCenterService;

	/**
	 * 获取岗位列表
	 * @param queryDTO 查询参数
	 * @param authentication 当前登录用户认证信息（可选）
	 * @return 岗位列表
	 */
	@GetMapping("/jobs")
	public ApiResponse<JobListResponseVO> getJobList(
			@ModelAttribute JobListQueryDTO queryDTO,
			Authentication authentication) {
		logger.info("收到获取岗位列表请求，查询参数：{}", queryDTO);
		
		Integer studentUserId = getStudentUserId(authentication);
		JobListResponseVO result = positionCenterService.getJobList(queryDTO, studentUserId);
		
		return ApiResponse.success(result);
	}

	/**
	 * 获取用户收藏的岗位列表
	 * @param page 页码
	 * @param size 每页数量
	 * @param authentication 当前登录用户认证信息
	 * @return 收藏岗位列表
	 */
	@GetMapping("/favorites")
	public ApiResponse<JobListResponseVO> getFavoriteJobs(
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer size,
			Authentication authentication) {
		logger.info("收到获取收藏岗位列表请求，页码：{}，每页数量：{}", page, size);
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		JobListResponseVO result = positionCenterService.getFavoriteJobs(page, size, studentUserId);
		
		return ApiResponse.success(result);
	}

	/**
	 * 获取职位详情
	 * @param jobId 职位ID
	 * @param authentication 当前登录用户认证信息（可选）
	 * @return 职位详情
	 */
	@GetMapping("/jobs/{job_id}")
	public ApiResponse<JobDetailVO> getJobDetail(
			@PathVariable("job_id") Integer jobId,
			Authentication authentication) {
		logger.info("收到获取职位详情请求，职位ID：{}", jobId);
		
		Integer studentUserId = getStudentUserId(authentication);
		JobDetailVO result = positionCenterService.getJobDetail(jobId, studentUserId);
		
		return ApiResponse.success(result);
	}

	/**
	 * 收藏岗位
	 * @param jobId 岗位ID
	 * @param authentication 当前登录用户认证信息
	 * @return 操作结果
	 */
	@PostMapping("/favorites/{job_id}")
	public ApiResponse<Void> favoriteJob(
			@PathVariable("job_id") Integer jobId,
			Authentication authentication) {
		logger.info("收到收藏岗位请求，职位ID：{}", jobId);
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		positionCenterService.favoriteJob(jobId, studentUserId);
		
		return ApiResponse.success(null);
	}

	/**
	 * 取消收藏岗位
	 * @param jobId 岗位ID
	 * @param authentication 当前登录用户认证信息
	 * @return 操作结果
	 */
	@DeleteMapping("/favorites/{job_id}")
	public ApiResponse<Void> unfavoriteJob(
			@PathVariable("job_id") Integer jobId,
			Authentication authentication) {
		logger.info("收到取消收藏岗位请求，职位ID：{}", jobId);
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		positionCenterService.unfavoriteJob(jobId, studentUserId);
		
		return ApiResponse.success(null);
	}

	/**
	 * 获取收藏状态
	 * @param jobId 岗位ID
	 * @param authentication 当前登录用户认证信息（可选）
	 * @return 收藏状态
	 */
	@GetMapping("/favorites/status/{job_id}")
	public ApiResponse<FavoriteStatusVO> getFavoriteStatus(
			@PathVariable("job_id") Integer jobId,
			Authentication authentication) {
		logger.info("收到查询收藏状态请求，职位ID：{}", jobId);
		
		Integer studentUserId = getStudentUserId(authentication);
		FavoriteStatusVO result = positionCenterService.getFavoriteStatus(jobId, studentUserId);
		
		return ApiResponse.success(result);
	}

	/**
	 * 岗位投递
	 * @param submitDTO 投递请求
	 * @param authentication 当前登录用户认证信息
	 * @return 投递结果
	 */
	@PostMapping("/applications")
	public ApiResponse<ApplicationSubmitVO> submitApplication(
			@Valid @RequestBody ApplicationSubmitDTO submitDTO,
			Authentication authentication) {
		logger.info("收到岗位投递请求，职位ID：{}，简历ID：{}", 
				submitDTO.getJobId(), submitDTO.getResumeId());
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		ApplicationSubmitVO result = positionCenterService.submitApplication(submitDTO, studentUserId);
		
		return ApiResponse.success(result);
	}

	/**
	 * 获取简历列表（求职中心）
	 * 注意：根据OpenAPI规范，此接口应复用 /resume-center/resume_files 接口逻辑
	 * @param authentication 当前登录用户认证信息
	 * @return 简历文件列表
	 */
	@GetMapping("/resume-files")
	public ApiResponse<List<ResumeFileVO>> getResumeFiles(
			Authentication authentication) {
		logger.info("收到获取简历列表请求");
		
		Integer studentUserId = getStudentUserId(authentication);
		if (studentUserId == null) {
			return ApiResponse.error(401, "用户未登录");
		}
		
		// TODO: 这里应该调用resume-center的Service，暂时直接实现基本功能
		// 实际应该复用 /resume-center/resume_files 接口逻辑
		List<ResumeFileVO> result = positionCenterService.getResumeFiles(studentUserId);
		
		return ApiResponse.success(result);
	}

	/**
	 * 上传PDF简历（求职中心）
	 * 接收JSON请求体，包含base64编码的文件内容
	 * @param dto 上传请求DTO，包含file（base64字符串）、usage、template_id
	 * @param authentication 当前登录用户认证信息
	 * @return 上传结果
	 */
	@PostMapping("/resume_files/upload")
	public ApiResponse<ResumeFileVO> uploadResume(
			@Valid @RequestBody UploadResumeFileDTO dto,
			Authentication authentication) {
		logger.info("收到上传简历请求，用途：{}，模板ID：{}", dto.getUsage(), dto.getTemplateId());
		
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
		
		// 调用简历中心服务上传文件
		ResumeFileVO result = resumeCenterService.uploadResumeFile(
				studentUserId, 
				file, 
				dto.getUsage() != null ? dto.getUsage() : "resume_pdf",
				dto.getTemplateId()
		);
		
		return ApiResponse.of(200, "File uploaded successfully", result);
	}

	/**
	 * 从Authentication中获取学生用户ID
	 * @param authentication 认证信息
	 * @return 学生用户ID，如果未登录或不是学生则返回null
	 */
	private Integer getStudentUserId(Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		
		try {
			// CustomUserDetails的getUsername()返回的是用户ID的字符串形式
			// 参见CustomUserDetails.getUsername()方法
			if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails) {
				org.springframework.security.core.userdetails.UserDetails userDetails = 
						(org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();
				String userIdStr = userDetails.getUsername();
				return Integer.parseInt(userIdStr);
			}
			// 备用方案：从name中获取
			String userIdStr = authentication.getName();
			return Integer.parseInt(userIdStr);
		} catch (Exception e) {
			logger.warn("获取用户ID失败", e);
			return null;
		}
	}
}

