package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TagCreateDTO {

	@NotBlank(message = "标签名称不能为空")
	@Size(min = 1, max = 100, message = "标签名称长度需在1-100个字符内")
	private String name;

	@NotNull(message = "分类ID不能为空")
	@Min(value = 1, message = "分类ID必须为正整数")
	private Integer categoryId;
}
