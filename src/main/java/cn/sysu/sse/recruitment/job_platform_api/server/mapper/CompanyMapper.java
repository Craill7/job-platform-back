package cn.sysu.sse.recruitment.job_platform_api.server.mapper;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Company;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface CompanyMapper {
	Optional<Company> findById(@Param("companyId") Integer companyId);
	Optional<Company> findByUserId(@Param("userId") Integer userId);
	int insert(Company company);
}

