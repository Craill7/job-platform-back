package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Resume;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ResumeMapper {
	int insert(Resume resume);
	Optional<Resume> findById(@Param("id") Long id);
	List<Resume> findByStudentUserId(@Param("studentUserId") Integer studentUserId);
	int deleteById(@Param("id") Long id);
}

