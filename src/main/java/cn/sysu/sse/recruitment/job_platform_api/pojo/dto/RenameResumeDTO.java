package cn.sysu.sse.recruitment.job_platform_api.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 重命名简历请求DTO
 */
public class RenameResumeDTO {

    @NotBlank(message = "自定义名称不能为空")
    @Size(max = 200, message = "文件名长度不能超过200字符")
    private String customName;

    public RenameResumeDTO() {}

    public RenameResumeDTO(String customName) {
        this.customName = customName;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }
}