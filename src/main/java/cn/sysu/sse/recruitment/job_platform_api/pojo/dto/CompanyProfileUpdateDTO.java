package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * 企业信息更新请求 DTO
 */
@Data
public class CompanyProfileUpdateDTO {

	@NotBlank(message = "企业简介不能为空")
	@Size(max = 2000, message = "企业简介长度不能超过2000字符")
	private String description;

	@NotBlank(message = "企业地址不能为空")
	@Size(max = 255, message = "企业地址长度不能超过255字符")
	private String companyAddress;

	@NotBlank(message = "企业性质不能为空")
	@Size(max = 64, message = "企业性质长度不能超过64字符")
	private String nature;

	@NotBlank(message = "所属行业不能为空")
	@Size(max = 64, message = "所属行业长度不能超过64字符")
	private String industry;

	@NotBlank(message = "企业规模不能为空")
	@Size(max = 64, message = "企业规模长度不能超过64字符")
	private String companyScale;

	@NotBlank(message = "联系人姓名不能为空")
	@Size(max = 50, message = "联系人姓名长度不能超过50字符")
	private String contactPersonName;

	@NotBlank(message = "联系电话不能为空")
	@Pattern(regexp = "^[0-9+\\-()\\s]{5,30}$", message = "联系电话格式不正确")
	private String contactPersonPhone;

	@Valid
	@Size(max = 10, message = "外部链接最多支持10个")
	private List<ExternalLinkDTO> externalLinks;

	@Data
	public static class ExternalLinkDTO {
		@NotBlank(message = "链接名称不能为空")
		@Size(max = 50, message = "链接名称长度不能超过50字符")
		private String linkName;

		@NotBlank(message = "链接地址不能为空")
		@Size(max = 255, message = "链接地址长度不能超过255字符")
		private String linkUrl;
	}
}
