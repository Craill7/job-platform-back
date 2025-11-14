package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.CompetitionExperience;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CompetitionExperienceMapper {
	int insert(CompetitionExperience competitionExperience);
	int update(CompetitionExperience competitionExperience);
	int deleteById(@Param("id") Long id);
	Optional<CompetitionExperience> findById(@Param("id") Long id);
	List<CompetitionExperience> findByStudentUserId(@Param("studentUserId") Integer studentUserId);
}

