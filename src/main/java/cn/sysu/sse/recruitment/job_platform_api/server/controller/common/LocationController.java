package cn.sysu.sse.recruitment.job_platform_api.server.controller.common;

import cn.sysu.sse.recruitment.job_platform_api.common.result.ApiResponse;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.ProvinceVO;
import cn.sysu.sse.recruitment.job_platform_api.server.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 省市数据控制器
 */
@RestController
@RequestMapping("/api")
public class LocationController {

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private LocationService locationService;

    /**
     * 获取省市层级数据
     * @return 省市层级数据
     */
    @GetMapping("/locations")
    public ApiResponse<List<ProvinceVO>> getLocations() {
        logger.info("收到获取省市数据请求");
        
        try {
            List<ProvinceVO> provinces = locationService.getProvincesCities();
            logger.info("成功获取省市数据，共 {} 个省份", provinces.size());
            return ApiResponse.success(provinces);
        } catch (Exception e) {
            logger.error("获取省市数据失败", e);
            return ApiResponse.error(500, "获取省市数据失败");
        }
    }
}
