package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.CandidateApplicationSummaryDTO;
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
	long countByCompany(@Param("companyId") Integer companyId);
	long countProcessedByCompany(@Param("companyId") Integer companyId);

	/**
	 * 查询岗位下的人才列表
	 */
	List<CandidateApplicationSummaryDTO> listCandidatesByJob(@Param("jobId") Integer jobId,
	                                                       @Param("nameKeyword") String nameKeyword,
	                                                       @Param("status") Integer status,
	                                                       @Param("offset") int offset,
	                                                       @Param("limit") int limit);

	/**
	 * 统计岗位下的人才数量
	 */
	long countCandidatesByJob(@Param("jobId") Integer jobId,
	                          @Param("nameKeyword") String nameKeyword,
	                          @Param("status") Integer status);
	
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
	
	/**
	 * 查询学生已投递的岗位列表（关联Job和Company）
	 * @param studentUserId 学生用户ID
	 * @param jobTitle 岗位名称（模糊搜索）
	 * @param companyName 公司名称（模糊搜索）
	 * @param offset 偏移量
	 * @param limit 限制数量
	 * @return 已投递岗位列表
	 */
	List<cn.sysu.sse.recruitment.job_platform_api.pojo.dto.DeliveryJobDTO> findDeliveryJobsByStudent(
			@Param("studentUserId") Integer studentUserId,
			@Param("jobTitle") String jobTitle,
			@Param("companyName") String companyName,
			@Param("offset") int offset,
			@Param("limit") int limit);
	
	/**
	 * 统计学生已投递的岗位数量
	 * @param studentUserId 学生用户ID
	 * @param jobTitle 岗位名称（模糊搜索）
	 * @param companyName 公司名称（模糊搜索）
	 * @return 总数量
	 */
	long countDeliveryJobsByStudent(
			@Param("studentUserId") Integer studentUserId,
			@Param("jobTitle") String jobTitle,
			@Param("companyName") String companyName);
}
