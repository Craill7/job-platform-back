package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.ProvinceVO;

import java.util.List;

/**
 * 省市数据服务接口
 */
public interface LocationService {

    /**
     * 获取省市层级数据
     * @return 省份列表，包含下属城市
     */
    List<ProvinceVO> getProvincesCities();
}
