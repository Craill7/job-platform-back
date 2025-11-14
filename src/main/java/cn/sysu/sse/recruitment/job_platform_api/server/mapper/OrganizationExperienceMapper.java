package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.OrganizationExperience;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface OrganizationExperienceMapper {
	int insert(OrganizationExperience organizationExperience);
	int update(OrganizationExperience organizationExperience);
	int deleteById(@Param("id") Long id);
	Optional<OrganizationExperience> findById(@Param("id") Long id);
	List<OrganizationExperience> findByStudentUserId(@Param("studentUserId") Integer studentUserId);
}

