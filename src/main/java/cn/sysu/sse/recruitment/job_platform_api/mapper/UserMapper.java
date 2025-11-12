package cn.sysu.sse.recruitment.job_platform_api.mapper;

import cn.sysu.sse.recruitment.job_platform_api.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
	int insert(User user);
	int update(User user);
	int deleteById(@Param("id") Integer id);
	Optional<User> findById(@Param("id") Integer id);
	Optional<User> findByEmail(@Param("email") String email);
	List<User> list(@Param("offset") int offset, @Param("limit") int limit);
	long countAll();
}


