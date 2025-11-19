package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.ApplicationStatusEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * 投递状态字典 Mapper
 */
@Mapper
public interface ApplicationStatusMapper {
	/**
	 * 根据状态代码查询状态信息
	 * @param code 状态代码
	 * @return 状态信息
	 */
	Optional<ApplicationStatusEntity> findByCode(@Param("code") Integer code);
}

