package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.CompanyExternalLink;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyExternalLinkMapper {
	List<CompanyExternalLink> listByCompanyId(@Param("companyId") Integer companyId);
}
