package cn.sysu.sse.recruitment.job_platform_api.common.enums;

public enum JobStatus implements IntEnum {
	PENDING(10),
	APPROVED(20),
	REJECTED(30),
	CLOSED(40);

	private final int code;

	JobStatus(int code) {
		this.code = code;
	}

	@Override
	public int getCode() {
		return code;
	}

	public static JobStatus fromCode(Integer code) {
		if (code == null) return null;
		for (JobStatus v : values()) {
			if (v.code == code) return v;
		}
		throw new IllegalArgumentException("Unknown JobStatus code: " + code);
	}
}
