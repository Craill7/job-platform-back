package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 上传简历文件请求DTO
 */
@Data
public class UploadResumeFileDTO {
	@NotBlank(message = "文件不能为空")
	private String file; // base64编码的文件内容
	
	private String usage = "resume_pdf"; // 文件用途，默认为 resume_pdf
	
	private Integer templateId; // 模板ID（可选）
}

