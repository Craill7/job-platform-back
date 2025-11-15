package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Event;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

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
	
	/**
	 * 根据ID查询活动
	 * @param id 活动ID
	 * @return 活动
	 */
	Event findById(@Param("id") Long id);
}

