package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.TagCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagCategoryMapper {
	List<TagCategory> listAll();
}
