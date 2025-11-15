package cn.sysu.sse.recruitment.job_platform_api.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 静态资源映射配置，用于暴露上传的企业Logo
 */
@Configuration
public class FileStorageConfig implements WebMvcConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(FileStorageConfig.class);

	@Value("${app.storage.company-logo-dir}")
	private String logoDir;

	@Value("${app.storage.company-logo-url-prefix}")
	private String logoUrlPrefix;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (!StringUtils.hasText(logoDir) || !StringUtils.hasText(logoUrlPrefix)) {
			logger.warn("未配置公司Logo静态资源路径，跳过资源映射");
			return;
		}

		Path dir = Paths.get(logoDir).toAbsolutePath().normalize();
		String handler = logoUrlPrefix.endsWith("/") ? logoUrlPrefix + "**" : logoUrlPrefix + "/**";
		if (!handler.startsWith("/")) {
			handler = "/" + handler;
		}

		String location = dir.toUri().toString();
		if (!location.endsWith("/")) {
			location = location + "/";
		}

		registry.addResourceHandler(handler)
				.addResourceLocations(location);
		logger.info("已映射公司Logo静态资源：{} -> {}", handler, location);
	}
}
