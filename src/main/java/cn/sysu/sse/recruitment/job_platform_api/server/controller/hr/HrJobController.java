package cn.sysu.sse.recruitment.job_platform_api.server.controller.hr;

import cn.sysu.sse.recruitment.job_platform_api.common.result.ApiResponse;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobCreateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobListQueryDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.HrJobUpdateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrCandidateListResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobCreateResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobDetailResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobListResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobStatusResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.HrJobUpdateResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.server.service.HrJobService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hr")
public class HrJobController {

    private static final Logger logger = LoggerFactory.getLogger(HrJobController.class);

    @Autowired
    private HrJobService hrJobService;

    @GetMapping("/jobs")
    public ApiResponse<HrJobListResponseVO> listCompanyJobs(@ModelAttribute HrJobListQueryDTO queryDTO,
            Authentication authentication) {
        Integer userId = getHrUserId(authentication);
        if (userId == null) {
            logger.warn("未登录用户尝试访问企业岗位列表");
            return ApiResponse.error(401, "用户未登录");
        }
        logger.info("收到企业岗位列表请求，用户ID={}，参数={}", userId, queryDTO);
        HrJobListResponseVO data = hrJobService.listCompanyJobs(userId, queryDTO);
        return ApiResponse.success(data);
    }

    @GetMapping("/talentpool/job/list/{job_id}")
    public ApiResponse<HrCandidateListResponseVO> listCandidatesByJob(
            @PathVariable("job_id") Integer jobId,
            @RequestParam(value = "name_keyword", required = false) String nameKeyword,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "page_size", required = false) Integer pageSize,
            Authentication authentication) {
        Integer userId = getHrUserId(authentication);
        if (userId == null) {
            logger.warn("未登录用户尝试访问岗位人才列表 jobId={}", jobId);
            return ApiResponse.error(401, "用户未登录");
        }
        logger.info("收到岗位人才列表请求 userId={} jobId={} nameKeyword={} status={} page={} pageSize={}",
                userId, jobId, nameKeyword, status, page, pageSize);
        HrCandidateListResponseVO result = hrJobService.listCandidatesByJob(userId, jobId, nameKeyword, status, page, pageSize);
        return ApiResponse.success(result);
    }

    /**
     * 下线岗位
     */
    @PutMapping("/jobs/{job_id}/status")
    public ApiResponse<HrJobStatusResponseVO> closeJob(
            @PathVariable("job_id") Integer jobId,
            Authentication authentication) {
        Integer userId = getHrUserId(authentication);
        if (userId == null) {
            logger.warn("未登录用户尝试下线岗位 jobId={}", jobId);
            return ApiResponse.error(401, "用户未登录");
        }
        logger.info("收到下线岗位请求，userId={} jobId={}", userId, jobId);
        HrJobStatusResponseVO result = hrJobService.closeJob(userId, jobId);
        return ApiResponse.success(result);
    }

    /**
     * 更新岗位信息
     * @param jobId 岗位ID
     * @param dto 更新内容
     * @param authentication 登录信息
     * @return 更新结果
     */
    @PutMapping("/jobs/{job_id}")
    public ApiResponse<HrJobUpdateResponseVO> updateJob(
            @PathVariable("job_id") Integer jobId,
            @RequestBody @Valid HrJobUpdateDTO dto,
            Authentication authentication) {
        Integer userId = getHrUserId(authentication);
        if (userId == null) {
            logger.warn("未登录用户尝试更新岗位 jobId={}", jobId);
            return ApiResponse.error(401, "用户未登录");
        }
        logger.info("收到更新岗位请求，userId={} jobId={}", userId, jobId);
        HrJobUpdateResponseVO result = hrJobService.updateJob(userId, jobId, dto);
        return ApiResponse.success(result);
    }

    @PostMapping("/jobs")
    public ApiResponse<HrJobCreateResponseVO> createJob(@RequestBody @Valid HrJobCreateDTO dto,
            Authentication authentication) {
        Integer userId = getHrUserId(authentication);
        if (userId == null) {
            logger.warn("未登录用户尝试创建岗位");
            return ApiResponse.error(401, "用户未登录");
        }
        logger.info("收到创建岗位请求，用户ID={}，payload={}", userId, dto);
        HrJobCreateResponseVO result = hrJobService.createJob(userId, dto);
        return ApiResponse.of(201, "Created", result);
    }

    /**
     * 获取岗位详情
     * @param jobId 岗位ID
     * @param authentication 登录信息
     * @return 岗位详情
     */
    @GetMapping("/jobs/{job_id}")
    public ApiResponse<HrJobDetailResponseVO> getJobDetail(
            @PathVariable("job_id") Integer jobId,
            Authentication authentication) {
        Integer userId = getHrUserId(authentication);
        if (userId == null) {
            logger.warn("未登录用户尝试获取岗位详情 jobId={}", jobId);
            return ApiResponse.error(401, "用户未登录");
        }
        logger.info("收到岗位详情请求，userId={} jobId={}", userId, jobId);
        HrJobDetailResponseVO detail = hrJobService.getJobDetail(userId, jobId);
        return ApiResponse.success(detail);
    }

    /**
     * 删除草稿岗位
     * @param jobId 岗位ID
     * @param authentication 登录信息
     * @return 操作结果
     */
    @DeleteMapping("/jobs/{job_id}")
    public ApiResponse<Void> deleteDraftJob(
            @PathVariable("job_id") Integer jobId,
            Authentication authentication) {
        Integer userId = getHrUserId(authentication);
        if (userId == null) {
            logger.warn("未登录用户尝试删除岗位 jobId={}", jobId);
            return ApiResponse.error(401, "用户未登录");
        }
        logger.info("收到删除草稿岗位请求，userId={} jobId={}", userId, jobId);
        hrJobService.deleteDraftJob(userId, jobId);
        return ApiResponse.of(200, "删除草稿成功", null);
    }

    private Integer getHrUserId(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        try {
            return Integer.parseInt(authentication.getName());
        } catch (NumberFormatException ex) {
            logger.warn("解析用户ID失败", ex);
            return null;
        }
    }
}
