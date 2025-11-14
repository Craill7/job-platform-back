package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface StudentMapper {
	Optional<Student> findByUserId(@Param("userId") Integer userId);
	int update(Student student);
	int insert(Student student);
}

