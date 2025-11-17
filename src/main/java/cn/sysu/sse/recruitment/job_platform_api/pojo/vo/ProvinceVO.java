package cn.sysu.sse.recruitment.job_platform_api.pojo.vo;

import lombok.Data;
import java.util.List;

/**
 * 省份数据 VO
 */
@Data
public class ProvinceVO {
    private Integer provinceId;
    private String name;
    private String code;
    private List<CityVO> cities;
}
