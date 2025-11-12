package cn.sysu.sse.recruitment.job_platform_api.domain.enums;

public enum UserRole implements IntEnum {
	STUDENT(1),
	HR(2);

	private final int code;

	UserRole(int code) {
		this.code = code;
	}

	@Override
	public int getCode() {
		return code;
	}

	public static UserRole fromCode(Integer code) {
		if (code == null) return null;
		for (UserRole v : values()) {
			if (v.code == code) return v;
		}
		throw new IllegalArgumentException("Unknown UserRole code: " + code);
	}
}


