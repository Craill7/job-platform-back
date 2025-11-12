package cn.sysu.sse.recruitment.job_platform_api.common.enums;

public enum ApplicationStatus implements IntEnum {
	SUBMITTED(10), // 已投递
	CANDIDATE(20), // 候选
	INTERVIEW(30), // 面试
	PASSED(40),    // 通过
	REJECTED(50);  // 拒绝

	private final int code;

	ApplicationStatus(int code) {
		this.code = code;
	}

	@Override
	public int getCode() {
		return code;
	}

	public static ApplicationStatus fromCode(Integer code) {
		if (code == null) return null;
		for (ApplicationStatus v : values()) {
			if (v.code == code) return v;
		}
		throw new IllegalArgumentException("Unknown ApplicationStatus code: " + code);
	}
}
