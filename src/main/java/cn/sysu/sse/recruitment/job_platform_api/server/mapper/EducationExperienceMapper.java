package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.EducationExperience;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EducationExperienceMapper {
	int insert(EducationExperience educationExperience);
	int update(EducationExperience educationExperience);
	int deleteById(@Param("id") Long id);
	Optional<EducationExperience> findById(@Param("id") Long id);
	List<EducationExperience> findByStudentUserId(@Param("studentUserId") Integer studentUserId);
}

