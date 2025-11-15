package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Application;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ApplicationMapper {
	int insert(Application application);
	int update(Application application);
	Optional<Application> findById(@Param("id") Integer id);
	List<Application> listByJob(@Param("jobId") Integer jobId,
	                            @Param("offset") int offset,
	                            @Param("limit") int limit);
	long countByJob(@Param("jobId") Integer jobId);
	
	// 求职中心相关方法
	Optional<Application> findByJobAndStudent(@Param("jobId") Integer jobId, @Param("studentUserId") Integer studentUserId);
	
	// 检查简历是否被投递记录引用
	int countByResumeId(@Param("resumeId") Long resumeId);
	
	/**
	 * 根据ID和学生ID查询投递详情（关联Job和Company）
	 * @param id 投递记录ID
	 * @param studentUserId 学生用户ID
	 * @return 投递记录，如果不存在或不属于该学生则返回Optional.empty()
	 */
	Optional<Application> findByIdAndStudent(@Param("id") Integer id, @Param("studentUserId") Integer studentUserId);
}
