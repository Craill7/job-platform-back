package cn.sysu.sse.recruitment.job_platform_api.mapper;

import cn.sysu.sse.recruitment.job_platform_api.domain.entity.Job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface JobMapper {
	int insert(Job job);
	int update(Job job);
	int deleteById(@Param("id") Integer id);
	Optional<Job> findById(@Param("id") Integer id);
	List<Job> listByCompany(@Param("companyId") Integer companyId,
	                        @Param("offset") int offset,
	                        @Param("limit") int limit);
	long countByCompany(@Param("companyId") Integer companyId);
}


