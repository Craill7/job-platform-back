package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.City;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Province;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * 省市数据 Mapper
 */
@Mapper
public interface LocationMapper {

    /**
     * 获取所有省份
     * @return 省份列表
     */
    List<Province> findAllProvinces();

    /**
     * 根据省份ID获取城市列表
     * @param provinceId 省份ID
     * @return 城市列表
     */
    List<City> findCitiesByProvinceId(Integer provinceId);

    /**
     * 根据关键字（名称/简称/代码）查找省份
     * @param keyword 省份关键字
     * @return 省份
     */
    Optional<Province> findProvinceByKeyword(@Param("keyword") String keyword);

    /**
     * 根据关键字查找城市，可选限定省份
     * @param keyword 城市关键字
     * @param provinceId 省份ID，可为空
     * @return 城市
     */
    Optional<City> findCityByKeyword(@Param("keyword") String keyword, @Param("provinceId") Integer provinceId);

    /**
     * 批量查询省份
     */
    List<Province> findProvincesByIds(@Param("ids") List<Integer> ids);

    /**
     * 批量查询城市
     */
    List<City> findCitiesByIds(@Param("ids") List<Integer> ids);
}
