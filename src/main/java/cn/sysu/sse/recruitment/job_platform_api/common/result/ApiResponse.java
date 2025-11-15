package cn.sysu.sse.recruitment.job_platform_api.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
	private Integer code;
	private String message;
	// data 字段必须始终存在（即使为 null），以符合 OpenAPI 规范
	private T data;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Pagination pagination;

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(200, "OK", data, null);
	}

	public static <T> ApiResponse<T> success(T data, Pagination pagination) {
		return new ApiResponse<>(200, "OK", data, pagination);
	}

	public static <T> ApiResponse<T> of(Integer code, String message, T data) {
		return new ApiResponse<>(code, message, data, null);
	}

	public static <T> ApiResponse<T> error(Integer code, String message) {
		return new ApiResponse<>(code, message, null, null);
	}
}
