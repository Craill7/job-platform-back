package cn.sysu.sse.recruitment.job_platform_api.server.handler;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 自定义日期反序列化器，支持 "yyyy-MM" 格式（年月）
 * 自动转换为该月的第一天
 */
public class YearMonthDeserializer extends JsonDeserializer<LocalDate> {
	
	private static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
	private static final DateTimeFormatter FULL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	@Override
	public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		String dateStr = p.getText();
		if (dateStr == null || dateStr.trim().isEmpty()) {
			return null;
		}
		
		dateStr = dateStr.trim();
		
		// 尝试解析 "yyyy-MM" 格式
		if (dateStr.length() == 7 && dateStr.matches("\\d{4}-\\d{2}")) {
			try {
				// 解析为年月，然后设置为该月的第一天
				LocalDate date = LocalDate.parse(dateStr + "-01", FULL_DATE_FORMATTER);
				return date;
			} catch (DateTimeParseException e) {
				throw new IOException("无法解析日期格式: " + dateStr + "，期望格式: yyyy-MM", e);
			}
		}
		
		// 尝试解析完整的 "yyyy-MM-dd" 格式
		if (dateStr.length() == 10 && dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
			try {
				return LocalDate.parse(dateStr, FULL_DATE_FORMATTER);
			} catch (DateTimeParseException e) {
				throw new IOException("无法解析日期格式: " + dateStr, e);
			}
		}
		
		// 如果都不匹配，抛出异常
		throw new IOException("日期格式不正确: " + dateStr + "，期望格式: yyyy-MM 或 yyyy-MM-dd");
	}
}

