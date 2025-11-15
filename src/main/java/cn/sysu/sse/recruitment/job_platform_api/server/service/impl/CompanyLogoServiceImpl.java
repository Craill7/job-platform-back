package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.CompanyLogoUploadVO;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.CompanyMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.service.CompanyLogoService;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.UUID;

@Service
public class CompanyLogoServiceImpl implements CompanyLogoService {

	private static final Logger logger = LoggerFactory.getLogger(CompanyLogoServiceImpl.class);
	private static final long MAX_FILE_SIZE = 5 * 1024 * 1024L;
	private static final int TARGET_SIZE = 512;
	private static final double OUTPUT_QUALITY = 0.8d;

	@Value("${app.storage.company-logo-dir}")
	private String logoDir;

	@Value("${app.storage.company-logo-url-prefix}")
	private String logoUrlPrefix;

	@Autowired
	private CompanyMapper companyMapper;

	@Override
	@Transactional
	public CompanyLogoUploadVO uploadCompanyLogo(Integer userId, MultipartFile file) {
		logger.info("上传企业Logo，用户ID：{}，文件名：{}", userId, file != null ? file.getOriginalFilename() : "");
		if (userId == null) {
			throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户未登录");
		}
		if (file == null || file.isEmpty()) {
			throw new BusinessException(ErrorCode.BAD_REQUEST, "文件不能为空");
		}
		if (file.getSize() > MAX_FILE_SIZE) {
			throw new BusinessException(ErrorCode.BAD_REQUEST, "文件大小不能超过5MB");
		}

		byte[] originalBytes;
		try {
			originalBytes = file.getBytes();
		} catch (IOException e) {
			logger.error("读取上传文件失败", e);
			throw new BusinessException(ErrorCode.INTERNAL_ERROR, "读取文件失败");
		}

		ImageType imageType = detectImageType(originalBytes);
		if (imageType == null) {
			throw new BusinessException(ErrorCode.BAD_REQUEST, "仅支持 JPEG/PNG/WebP 图片");
		}

		try {
			if (ImageIO.read(new ByteArrayInputStream(originalBytes)) == null) {
				throw new BusinessException(ErrorCode.BAD_REQUEST, "无法解析图片内容");
			}
		} catch (IOException e) {
			logger.error("解析图片失败", e);
			throw new BusinessException(ErrorCode.BAD_REQUEST, "无法解析图片内容");
		}

		String outputFormat = supportsWebp() ? "webp" : "jpg";
		byte[] processedBytes = processImage(originalBytes, outputFormat);
		String filename = buildFilename(outputFormat);
		Path savedPath = saveFile(processedBytes, filename);
		String url = buildAccessibleUrl(filename);

		int rows = companyMapper.updateLogo(userId, url);
		if (rows == 0) {
			try {
				Files.deleteIfExists(savedPath);
			} catch (IOException ignore) {
				logger.warn("删除未使用的Logo文件失败：{}", savedPath);
			}
			throw new BusinessException(ErrorCode.NOT_FOUND, "未找到企业信息");
		}

		CompanyLogoUploadVO vo = new CompanyLogoUploadVO();
		vo.setUrl(url);
		logger.info("企业Logo上传成功，用户ID：{}，文件路径：{}", userId, savedPath.toAbsolutePath());
		return vo;
	}

	private byte[] processImage(byte[] source, String outputFormat) {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			Thumbnails.of(new ByteArrayInputStream(source))
					.size(TARGET_SIZE, TARGET_SIZE)
					.crop(Positions.CENTER)
					.outputQuality(OUTPUT_QUALITY)
					.outputFormat(outputFormat)
					.toOutputStream(outputStream);
			return outputStream.toByteArray();
		} catch (IOException e) {
			logger.error("处理图片失败", e);
			throw new BusinessException(ErrorCode.INTERNAL_ERROR, "处理图片失败，请稍后再试");
		}
	}

	private Path saveFile(byte[] bytes, String filename) {
		try {
			Path dir = Paths.get(logoDir).toAbsolutePath().normalize();
			Files.createDirectories(dir);
			Path filePath = dir.resolve(filename);
			Files.write(filePath, bytes);
			return filePath;
		} catch (IOException e) {
			logger.error("保存Logo文件失败", e);
			throw new BusinessException(ErrorCode.INTERNAL_ERROR, "存储图片失败");
		}
	}

	private String buildFilename(String extension) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid + "." + extension.toLowerCase(Locale.ROOT);
	}

	private String buildAccessibleUrl(String filename) {
		String prefix = logoUrlPrefix.endsWith("/") ? logoUrlPrefix : logoUrlPrefix + "/";
		return prefix + filename;
	}

	private ImageType detectImageType(byte[] data) {
		if (data.length < 12) {
			return null;
		}
		if ((data[0] & 0xFF) == 0xFF && (data[1] & 0xFF) == 0xD8) {
			return ImageType.JPEG;
		}
		if ((data[0] & 0xFF) == 0x89 && data[1] == 0x50 && data[2] == 0x4E && data[3] == 0x47
				&& data[4] == 0x0D && data[5] == 0x0A && data[6] == 0x1A && data[7] == 0x0A) {
			return ImageType.PNG;
		}
		if (data[0] == 'R' && data[1] == 'I' && data[2] == 'F' && data[3] == 'F'
				&& data[8] == 'W' && data[9] == 'E' && data[10] == 'B' && data[11] == 'P') {
			return ImageType.WEBP;
		}
		return null;
	}

	private boolean supportsWebp() {
		return ImageIO.getImageWritersByFormatName("webp").hasNext();
	}

	private enum ImageType {
		JPEG, PNG, WEBP
	}
}
