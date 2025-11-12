package cn.sysu.sse.recruitment.job_platform_api.server.handler;

import cn.sysu.sse.recruitment.job_platform_api.common.enums.IntEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({
		cn.sysu.sse.recruitment.job_platform_api.common.enums.UserRole.class,
		cn.sysu.sse.recruitment.job_platform_api.common.enums.UserStatus.class,
		cn.sysu.sse.recruitment.job_platform_api.common.enums.JobStatus.class,
		cn.sysu.sse.recruitment.job_platform_api.common.enums.ApplicationStatus.class,
		cn.sysu.sse.recruitment.job_platform_api.common.enums.WorkNature.class
})
public class UniversalIntEnumTypeHandler<E extends Enum<E> & IntEnum> extends BaseTypeHandler<E> {

	private final Class<E> type;

	public UniversalIntEnumTypeHandler(Class<E> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type argument cannot be null");
		}
		this.type = type;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getCode());
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int code = rs.getInt(columnName);
		return rs.wasNull() ? null : fromCode(code);
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		int code = rs.getInt(columnIndex);
		return rs.wasNull() ? null : fromCode(code);
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int code = cs.getInt(columnIndex);
		return cs.wasNull() ? null : fromCode(code);
	}

	private E fromCode(int code) {
		for (E e : type.getEnumConstants()) {
			if (e.getCode() == code) {
				return e;
			}
		}
		throw new IllegalArgumentException("Unknown enum code " + code + " for type " + type.getName());
	}
}
