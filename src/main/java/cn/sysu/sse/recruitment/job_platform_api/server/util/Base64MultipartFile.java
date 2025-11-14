package cn.sysu.sse.recruitment.job_platform_api.server.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 将base64字符串转换为MultipartFile的工具类
 */
public class Base64MultipartFile implements MultipartFile {
	private final byte[] content;
	private final String name;
	private final String originalFilename;
	private final String contentType;

	public Base64MultipartFile(byte[] content, String name, String originalFilename, String contentType) {
		this.content = content;
		this.name = name;
		this.originalFilename = originalFilename;
		this.contentType = contentType;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getOriginalFilename() {
		return originalFilename;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public boolean isEmpty() {
		return content == null || content.length == 0;
	}

	@Override
	public long getSize() {
		return content != null ? content.length : 0;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return content;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(content);
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		throw new UnsupportedOperationException("transferTo is not supported");
	}
}

