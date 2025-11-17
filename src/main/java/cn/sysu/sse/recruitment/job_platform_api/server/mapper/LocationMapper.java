package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Province;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.City;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
}
