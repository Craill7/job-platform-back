package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface JobMapper {
	int insert(Job job);
	int update(Job job);
	int deleteById(@Param("id") Integer id);
	Optional<Job> findById(@Param("id") Integer id);
	List<Job> listByCompany(@Param("companyId") Integer companyId,
	                        @Param("offset") int offset,
	                        @Param("limit") int limit);
	long countByCompany(@Param("companyId") Integer companyId);
	
	// 求职中心相关方法
	List<Job> searchJobs(@Param("keyword") String keyword,
	                     @Param("location") String location,
	                     @Param("type") String type,
	                     @Param("tagIds") List<Integer> tagIds,
	                     @Param("workNature") String workNature,
	                     @Param("offset") int offset,
	                     @Param("limit") int limit);
	long countSearchJobs(@Param("keyword") String keyword,
	                     @Param("location") String location,
	                     @Param("type") String type,
	                     @Param("tagIds") List<Integer> tagIds,
	                     @Param("workNature") String workNature);
	List<Job> findFavoriteJobsByStudent(@Param("studentUserId") Integer studentUserId,
	                                    @Param("offset") int offset,
	                                    @Param("limit") int limit);
	long countFavoriteJobsByStudent(@Param("studentUserId") Integer studentUserId);
	List<Job> findOtherJobsByCompany(@Param("companyId") Integer companyId,
	                                 @Param("excludeJobId") Integer excludeJobId,
	                                 @Param("limit") int limit);
}
