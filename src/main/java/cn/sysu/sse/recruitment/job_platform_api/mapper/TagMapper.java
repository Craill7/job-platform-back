package cn.sysu.sse.recruitment.job_platform_api.mapper;

import cn.sysu.sse.recruitment.job_platform_api.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagMapper {
	int insert(Tag tag);
	List<Tag> listByCategory(@Param("categoryId") Integer categoryId);
}


