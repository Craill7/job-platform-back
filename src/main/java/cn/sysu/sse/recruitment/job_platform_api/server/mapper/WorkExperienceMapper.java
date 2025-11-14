package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.WorkExperience;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface WorkExperienceMapper {
	int insert(WorkExperience workExperience);
	int update(WorkExperience workExperience);
	int deleteById(@Param("id") Long id);
	Optional<WorkExperience> findById(@Param("id") Long id);
	List<WorkExperience> findByStudentUserId(@Param("studentUserId") Integer studentUserId);
}

