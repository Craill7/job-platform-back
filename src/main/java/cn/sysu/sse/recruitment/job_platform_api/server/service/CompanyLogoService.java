package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.CompanyLogoUploadVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 企业Logo上传服务
 */
public interface CompanyLogoService {
	/**
	 * 上传企业Logo并更新公司资料
	 * @param userId 当前登录HR用户ID
	 * @param file 上传文件
	 * @return Logo访问地址
	 */
	CompanyLogoUploadVO uploadCompanyLogo(Integer userId, MultipartFile file);
}
