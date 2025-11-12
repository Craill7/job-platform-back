package cn.sysu.sse.recruitment.job_platform_api.common.util;

public enum ErrorCode {
	OK(200, "OK"),
	CREATED(201, "Created"),
	ACCEPTED(202, "Accepted"),
	BAD_REQUEST(400, "Bad Request"),
	UNAUTHORIZED(401, "Unauthorized"),
	FORBIDDEN(403, "Forbidden"),
	NOT_FOUND(404, "Not Found"),
	CONFLICT(409, "Conflict"),
	INTERNAL_ERROR(500, "Internal Server Error");

	private final int code;
	private final String defaultMessage;

	ErrorCode(int code, String defaultMessage) {
		this.code = code;
		this.defaultMessage = defaultMessage;
	}

	public int getCode() {
		return code;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}
}
