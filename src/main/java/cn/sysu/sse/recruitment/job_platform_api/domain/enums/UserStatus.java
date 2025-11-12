package cn.sysu.sse.recruitment.job_platform_api.domain.enums;

public enum UserStatus implements IntEnum {
	PENDING(0),
	ACTIVE(1),
	DISABLED(2);

	private final int code;

	UserStatus(int code) {
		this.code = code;
	}

	@Override
	public int getCode() {
		return code;
	}

	public static UserStatus fromCode(Integer code) {
		if (code == null) return null;
		for (UserStatus v : values()) {
			if (v.code == code) return v;
		}
		throw new IllegalArgumentException("Unknown UserStatus code: " + code);
	}
}


