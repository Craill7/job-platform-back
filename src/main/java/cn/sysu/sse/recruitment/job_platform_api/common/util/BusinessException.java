package cn.sysu.sse.recruitment.job_platform_api.common.util;

public class BusinessException extends RuntimeException {
	private final int code;

	public BusinessException(int code, String message) {
		super(message);
		this.code = code;
	}

	public BusinessException(ErrorCode errorCode, String message) {
		super(message);
		this.code = errorCode.getCode();
	}

	public int getCode() {
		return code;
	}
}
