package cn.sysu.sse.recruitment.job_platform_api.domain.enums;

public enum WorkNature implements IntEnum {
	INTERNHIP(1),
	FULL_TIME(2);

	private final int code;

	WorkNature(int code) {
		this.code = code;
	}

	@Override
	public int getCode() {
		return code;
	}

	public static WorkNature fromCode(Integer code) {
		if (code == null) return null;
		for (WorkNature v : values()) {
			if (v.code == code) return v;
		}
		throw new IllegalArgumentException("Unknown WorkNature code: " + code);
	}
}


