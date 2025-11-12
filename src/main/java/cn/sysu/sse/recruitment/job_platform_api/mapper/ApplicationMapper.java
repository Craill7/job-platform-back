package cn.sysu.sse.recruitment.job_platform_api.mapper;

import cn.sysu.sse.recruitment.job_platform_api.domain.Application;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ApplicationMapper {
	int insert(Application application);
	int update(Application application);
	Optional<Application> findById(@Param("id") Integer id);
	List<Application> listByJob(@Param("jobId") Integer jobId,
	                            @Param("offset") int offset,
	                            @Param("limit") int limit);
	long countByJob(@Param("jobId") Integer jobId);
}


