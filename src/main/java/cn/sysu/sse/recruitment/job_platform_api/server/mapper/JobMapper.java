package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.JobListQueryDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.JobWithStatsDTO;
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
	Optional<Job> findByIdAndCompany(@Param("id") Integer id, @Param("companyId") Integer companyId);
	long countApprovedJobsByCompany(@Param("companyId") Integer companyId);
	List<Job> listByCompany(@Param("companyId") Integer companyId,
	                        @Param("offset") int offset,
	                        @Param("limit") int limit);
	long countByCompany(@Param("companyId") Integer companyId);
	List<JobWithStatsDTO> searchCompanyJobs(@Param("companyId") Integer companyId,
	                                      @Param("titleKeyword") String titleKeyword,
	                                      @Param("workNature") Integer workNature,
	                                      @Param("status") Integer status,
	                                      @Param("offset") int offset,
	                                      @Param("limit") int limit);
	long countCompanyJobs(@Param("companyId") Integer companyId,
	                     @Param("titleKeyword") String titleKeyword,
	                     @Param("workNature") Integer workNature,
	                     @Param("status") Integer status);
	
	// 求职中心相关方法
	List<Job> searchJobs(@Param("criteria") JobListQueryDTO criteria,
	                     @Param("tagIds") List<Integer> tagIds,
	                     @Param("workNature") String workNature,
	                     @Param("offset") int offset,
	                     @Param("limit") int limit);
	long countSearchJobs(@Param("criteria") JobListQueryDTO criteria,
	                     @Param("tagIds") List<Integer> tagIds,
	                     @Param("workNature") String workNature);
	List<Job> findFavoriteJobsByStudent(@Param("studentUserId") Integer studentUserId,
	                                    @Param("offset") int offset,
	                                    @Param("limit") int limit);
	long countFavoriteJobsByStudent(@Param("studentUserId") Integer studentUserId);
	List<Job> findOtherJobsByCompany(@Param("companyId") Integer companyId,
	                                 @Param("excludeJobId") Integer excludeJobId,
	                                 @Param("limit") Integer limit);

	int increaseViewCount(@Param("jobId") Integer jobId);
	
	// 学生主页相关方法
	/**
	 * 查询岗位热度排行榜（按投递数量排序）
	 * @param limit 返回数量限制
	 * @return 岗位列表
	 */
	List<Job> findRankedJobs(@Param("limit") Integer limit);
	
	/**
	 * 查询近期招聘信息列表
	 * @param jobTypeFilter 招聘类型筛选（企业招聘、事业单位招聘、实习招聘）
	 * @param limit 返回数量限制
	 * @return 岗位列表
	 */
	List<Job> findRecentJobs(@Param("jobTypeFilter") String jobTypeFilter,
	                         @Param("limit") Integer limit);

	int batchInsertJobTags(@Param("jobId") Integer jobId, @Param("tagIds") List<Integer> tagIds);
	int deleteJobTagsByJobId(@Param("jobId") Integer jobId);
}
