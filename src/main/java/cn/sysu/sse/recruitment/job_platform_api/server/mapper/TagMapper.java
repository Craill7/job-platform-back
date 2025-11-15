package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagMapper {
	int insert(Tag tag);
	List<Tag> listAll();
	List<Tag> listByCategory(@Param("categoryId") Integer categoryId);
	
	// 求职中心相关方法
	List<Tag> findByNameIn(@Param("names") List<String> names);
	List<Tag> findTagsByJobId(@Param("jobId") Integer jobId);
}
