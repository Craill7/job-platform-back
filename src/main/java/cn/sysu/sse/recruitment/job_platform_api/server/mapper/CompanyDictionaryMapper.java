package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.CompanyDictionaryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CompanyDictionaryMapper {
	Optional<CompanyDictionaryDTO> findByCompanyId(@Param("companyId") Integer companyId);
	List<String> listIndustries();
	List<String> listCompanyNatures();
	List<String> listCompanyScales();
}
