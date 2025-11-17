package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Province;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.City;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.ProvinceVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.CityVO;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.LocationMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 省市数据服务实现
 */
@Service
public class LocationServiceImpl implements LocationService {

    private static final Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);

    @Autowired
    private LocationMapper locationMapper;

    @Override
    public List<ProvinceVO> getProvincesCities() {
        logger.info("开始获取省市层级数据");
        
        // 获取所有省份
        List<Province> provinces = locationMapper.findAllProvinces();
        logger.info("获取到 {} 个省份", provinces.size());
        
        // 为每个省份获取城市列表并转换为 VO
        List<ProvinceVO> result = provinces.stream().map(province -> {
            ProvinceVO provinceVO = new ProvinceVO();
            provinceVO.setProvinceId(province.getId());
            provinceVO.setName(province.getName());
            provinceVO.setCode(province.getCode());
            
            // 获取该省份下的城市
            List<City> cities = locationMapper.findCitiesByProvinceId(province.getId());
            List<CityVO> cityVOs = cities.stream().map(city -> {
                CityVO cityVO = new CityVO();
                cityVO.setCityId(city.getId());
                cityVO.setName(city.getName());
                cityVO.setCode(city.getCode());
                return cityVO;
            }).collect(Collectors.toList());
            
            provinceVO.setCities(cityVOs);
            return provinceVO;
        }).collect(Collectors.toList());
        
        logger.info("省市数据转换完成，共 {} 个省份", result.size());
        return result;
    }
}
