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

	Tag findByName(@Param("name") String name);
	
	// 求职中心相关方法
	List<Tag> findByNameIn(@Param("names") List<String> names);
	List<Tag> findTagsByJobId(@Param("jobId") Integer jobId);
	List<Integer> findExistingIds(@Param("tagIds") List<Integer> tagIds);
	// 学生个人中心相关方法
	// 获取学生关联的所有标签
	List<Tag> findTagsByStudentId(@Param("studentUserId") Integer studentUserId);
    //批量插入学生标签关联
	int batchInsertStudentTags(@Param("studentUserId") Integer studentUserId, @Param("tagIds") List<Integer> tagIds);
    //删除指定学生的所有标签关联
	int deleteStudentTagsByUserId(@Param("studentUserId") Integer studentUserId);

}
