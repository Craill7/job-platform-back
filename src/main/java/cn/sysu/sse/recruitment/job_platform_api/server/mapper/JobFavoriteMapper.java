package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.JobFavorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface JobFavoriteMapper {
	int insert(JobFavorite jobFavorite);
	int deleteByStudentAndJob(@Param("studentUserId") Integer studentUserId, @Param("jobId") Integer jobId);
	Optional<JobFavorite> findByStudentAndJob(@Param("studentUserId") Integer studentUserId, @Param("jobId") Integer jobId);
	List<Integer> findJobIdsByStudent(@Param("studentUserId") Integer studentUserId);
	long countByStudent(@Param("studentUserId") Integer studentUserId);
}

