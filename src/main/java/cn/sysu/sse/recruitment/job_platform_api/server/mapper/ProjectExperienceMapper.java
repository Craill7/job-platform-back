package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.ProjectExperience;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProjectExperienceMapper {
	int insert(ProjectExperience projectExperience);
	int update(ProjectExperience projectExperience);
	int deleteById(@Param("id") Long id);
	Optional<ProjectExperience> findById(@Param("id") Long id);
	List<ProjectExperience> findByStudentUserId(@Param("studentUserId") Integer studentUserId);
}

