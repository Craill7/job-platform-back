package cn.sysu.sse.recruitment.job_platform_api.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ai.dashscope")
public class AiDashscopeProperties {

    /**
     * 百炼 API Key，建议从环境变量注入
     */
    private String apiKey;

    /**
     * 模型名称，默认使用 qwen-vl-plus
     */
    private String model = "qwen-vl-plus";

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
