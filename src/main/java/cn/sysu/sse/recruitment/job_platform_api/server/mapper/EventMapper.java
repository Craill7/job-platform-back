package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Event;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface EventMapper {
	/**
	 * 查询指定月份的活动
	 * @param startDate 月份开始日期
	 * @param endDate 月份结束日期
	 * @return 活动列表
	 */
	List<Event> findEventsByMonth(@Param("startDate") LocalDateTime startDate, 
	                              @Param("endDate") LocalDateTime endDate);
	
	/**
	 * 查询近期活动（按开始时间倒序）
	 * @param limit 返回数量限制
	 * @return 活动列表
	 */
	List<Event> findUpcomingEvents(@Param("limit") Integer limit);
	Optional<Event> findById(@Param("id") Long id);
	/**
	 * 搜索活动列表 (筛选标题或地点)
	 */
	List<Event> searchEvents(@Param("keyword") String keyword,
							 @Param("offset") int offset,
							 @Param("limit") int limit);

	/**
	 * 统计搜索结果总数
	 */
	long countSearchEvents(@Param("keyword") String keyword);
}

