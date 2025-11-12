package cn.sysu.sse.recruitment.job_platform_api.common.api;

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
	@JsonInclude(JsonInclude.Include.NON_NULL)
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


