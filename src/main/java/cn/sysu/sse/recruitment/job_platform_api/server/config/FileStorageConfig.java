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

	@Value("${app.storage.student-avatar-dir}")
	private String avatarDir;

	@Value("${app.storage.student-avatar-url-prefix}")
	private String avatarUrlPrefix;

	@Value("${app.storage.resume-dir}")
	private String resumeDir;

	@Value("${app.storage.resume-url-prefix}")
	private String resumeUrlPrefix;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 映射企业 Logo
		registerPath(registry, logoUrlPrefix, logoDir, "企业Logo");

		// 映射学生头像
		registerPath(registry, avatarUrlPrefix, avatarDir, "学生头像");

		// 映射简历文件
		registerPath(registry, resumeUrlPrefix, resumeDir, "简历文件");
	}

	// 提取公共方法
	private void registerPath(ResourceHandlerRegistry registry, String urlPrefix, String dirPath, String name) {
		if (!StringUtils.hasText(dirPath) || !StringUtils.hasText(urlPrefix)) {
			logger.warn("未配置{}静态资源路径，跳过资源映射", name);
			return;
		}

		Path dir = Paths.get(dirPath).toAbsolutePath().normalize();
		String handler = urlPrefix.endsWith("/") ? urlPrefix + "**" : urlPrefix + "/**";
		if (!handler.startsWith("/")) {
			handler = "/" + handler;
		}

		String location = dir.toUri().toString();
		if (!location.endsWith("/")) {
			location = location + "/";
		}

		registry.addResourceHandler(handler).addResourceLocations(location);
		logger.info("已映射{}静态资源：{} -> {}", name, handler, location);
	}
}
