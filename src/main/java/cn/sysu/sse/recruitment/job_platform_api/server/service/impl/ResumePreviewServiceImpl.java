package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.EducationExperience;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Student;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Tag;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.ResumePreviewVO;
import cn.sysu.sse.recruitment.job_platform_api.server.service.ResumePreviewService;
import org.springframework.stereotype.Service;

import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.EducationExperience;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Student;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Tag;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.ResumePreviewVO; // 导入新VO
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.EducationExperienceMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.StudentMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.TagMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.service.ResumePreviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResumePreviewServiceImpl implements ResumePreviewService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private EducationExperienceMapper educationExperienceMapper;
    @Autowired
    private TagMapper tagMapper;

    private static final DateTimeFormatter MONTH_FMT = DateTimeFormatter.ofPattern("yyyy-MM");

    @Override
    public ResumePreviewVO getStudentResume(Integer studentUserId) {
        // 1. 查询基础信息
        Student student = studentMapper.findByUserId(studentUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "未找到该学生信息"));

        // 2. 查询教育经历（取最新的一条作为主要学历）
        List<EducationExperience> eduList = educationExperienceMapper.findByStudentUserId(studentUserId);
        EducationExperience primaryEdu = eduList.isEmpty() ? null : eduList.get(0);

        // 3. 查询个人标签
        List<Tag> tags = tagMapper.findTagsByStudentId(studentUserId);

        // 4. 组装 VO
        ResumePreviewVO vo = new ResumePreviewVO();
        vo.setAvatarUrl(student.getAvatarUrl() != null ? student.getAvatarUrl() : "");

        // 4.1 组装 BasicInfo
        ResumePreviewVO.BasicInfo basicInfo = new ResumePreviewVO.BasicInfo();
        basicInfo.setFullName(student.getFullName());
        basicInfo.setGender(student.getGender() != null ? (student.getGender() == 0 ? "男" : "女") : "未知");
        // 计算年龄
        if (student.getDateOfBirth() != null) {
            basicInfo.setAge(Period.between(student.getDateOfBirth(), LocalDate.now()).getYears());
        }
        // 从教育经历中获取学历，放入 BasicInfo
        if (primaryEdu != null) {
            basicInfo.setDegreeLevel(convertDegree(primaryEdu.getDegreeLevel()));
        } else {
            basicInfo.setDegreeLevel("未填写");
        }
        basicInfo.setJobSeekingStatus(mapJobStatusToString(student.getJobSeekingStatus()));
        vo.setBasicInfo(basicInfo);

        // 4.2 组装 PrimaryEducation
        List<ResumePreviewVO.EducationBlock> eduVos = new ArrayList<>();
        if (eduList != null && !eduList.isEmpty()) {
            eduVos = eduList.stream()
                    // 核心修改：添加排序逻辑
                    // 方式一：按学历等级升序 (0本科 -> 1硕士 -> 2博士)
                    .sorted(Comparator.comparing(EducationExperience::getDegreeLevel))

                    .map(edu -> {
                        ResumePreviewVO.EducationBlock block = new ResumePreviewVO.EducationBlock();
                        block.setSchoolName(edu.getSchoolName());
                        block.setMajor(edu.getMajor());
                        block.setMajorRank(edu.getMajorRank());
                        block.setDegreeLevel(convertDegree(edu.getDegreeLevel()));
                        block.setStartDate(edu.getStartDate() != null ? edu.getStartDate().format(MONTH_FMT) : "");
                        block.setEndDate(edu.getEndDate() != null ? edu.getEndDate().format(MONTH_FMT) : "");
                        return block;
                    }).collect(Collectors.toList());
        }
        vo.setEducationExperiences(eduVos);

        // 4.3 组装 ExpectedJob
        ResumePreviewVO.ExpectedJob jobVo = new ResumePreviewVO.ExpectedJob();
        jobVo.setExpectedPosition(student.getExpectedPosition());
        // 直接赋值 min 和 max，不再拼接字符串
        jobVo.setExpectedMinSalary(student.getExpectedMinSalary());
        jobVo.setExpectedMaxSalary(student.getExpectedMaxSalary());
        vo.setExpectedJob(jobVo);

        // 4.4 组装 PersonalTags
        List<ResumePreviewVO.PersonalTag> tagVos = tags.stream().map(t -> {
            ResumePreviewVO.PersonalTag tagVo = new ResumePreviewVO.PersonalTag();
            tagVo.setTagId(t.getId());
            tagVo.setName(t.getName());
            return tagVo;
        }).collect(Collectors.toList());
        vo.setPersonalTags(tagVos);

        return vo;
    }

    // 辅助方法：转换学历
    private String convertDegree(Integer degreeLevel) {
        if (degreeLevel == null) return "";
        return switch (degreeLevel) {
            case 0 -> "本科";
            case 1 -> "硕士";
            case 2 -> "博士";
            default -> "其他";
        };
    }

    // 辅助方法：格式化薪资
    private String formatSalary(Integer min, Integer max) {
        if (min == null) return "面议";
        if (max == null) return min + "及以上"; // 假设单位是元，如果是k需要乘1000，根据数据库实际存储调整
        return min + "-" + max;
    }
    // [新增] 辅助方法：转换求职状态
    private String mapJobStatusToString(Integer status) {
        if (status == null) return "未填写";
        return switch (status) {
            case 0 -> "在校-暂不考虑";
            case 1 -> "在校-寻求实习";
            case 2 -> "应届-寻求实习";
            case 3 -> "应届-寻求校招";
            default -> "未知状态";
        };
    }
}
