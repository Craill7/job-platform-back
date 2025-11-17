package cn.sysu.sse.recruitment.job_platform_api.pojo.entity;

import lombok.Data;

/**
 * 城市实体类
 */
@Data
public class City {
    private Integer id;
    private Integer provinceId;
    private String code;
    private String name;
}
