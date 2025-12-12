package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.EducationExperience;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Student;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Tag;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.User;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.StudentResumePreviewVO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.StudentWelcomeCardVO;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.EducationExperienceMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.StudentMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.TagMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.UserMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.service.StudentCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentCenterServiceImpl implements StudentCenterService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EducationExperienceMapper educationExperienceMapper;
    @Autowired
    private TagMapper tagMapper;

    @Override
    public StudentWelcomeCardVO getWelcomeInfo(Integer userId) {
        Student student = studentMapper.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "学生信息不存在"));
        User user = userMapper.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "账号信息不存在"));

        // 获取最近的一条教育经历作为主显示
        List<EducationExperience> eduList = educationExperienceMapper.findByStudentUserId(userId);
        String schoolName = eduList.isEmpty() ? "未填写学校" : eduList.get(0).getSchoolName();

        // 获取个人标签
        List<Tag> tags = tagMapper.findTagsByStudentId(userId);

        StudentWelcomeCardVO vo = new StudentWelcomeCardVO();
        vo.setFullName(student.getFullName());
        vo.setSchoolName(schoolName);
        vo.setPhoneNumber(student.getPhoneNumber());
        vo.setEmail(user.getEmail());
        vo.setStudentId(student.getStudentId());
        // 格式化最后登录时间
        if (user.getLastLoginAt() != null) {
            vo.setLastLoginAt(user.getLastLoginAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }

        vo.setPersonalTags(tags.stream().map(t -> {
            StudentWelcomeCardVO.PersonalTag pt = new StudentWelcomeCardVO.PersonalTag();
            pt.setTagId(t.getId());
            pt.setName(t.getName());
            return pt;
        }).collect(Collectors.toList()));

        return vo;
    }

    @Override
    public StudentResumePreviewVO getResumePreview(Integer userId) {
        Student student = studentMapper.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "学生信息不存在"));

        List<EducationExperience> eduList = educationExperienceMapper.findByStudentUserId(userId);
        EducationExperience primaryEdu = eduList.isEmpty() ? null : eduList.get(0);

        StudentResumePreviewVO vo = new StudentResumePreviewVO();
        vo.setFullName(student.getFullName());

        // 计算年龄
        if (student.getDateOfBirth() != null) {
            vo.setAge(Period.between(student.getDateOfBirth(), LocalDate.now()).getYears());
        }

        // 薪资展示
        if (student.getExpectedMinSalary() != null) {
            String salary = student.getExpectedMinSalary() +
                    (student.getExpectedMaxSalary() != null ? "-" + student.getExpectedMaxSalary() : "以上");
            vo.setExpectedSalary(salary);
        }
        vo.setExpectedPosition(student.getExpectedPosition());

        // 教育相关
        if (primaryEdu != null) {
            String degreeStr = mapDegreeToString(primaryEdu.getDegreeLevel());
            vo.setDegreeLevel(degreeStr);

            // 计算届别 (例如: 2027-06-30 -> "27届")
            if (primaryEdu.getEndDate() != null) {
                int gradYear = primaryEdu.getEndDate().getYear();
                String shortYear = String.valueOf(gradYear).substring(2);
                vo.setGraduationYear(shortYear + "届" + (gradYear >= LocalDate.now().getYear() ? "应届生" : "毕业生"));
            }

            StudentResumePreviewVO.PrimaryEducation eduVo = new StudentResumePreviewVO.PrimaryEducation();
            eduVo.setSchoolName(primaryEdu.getSchoolName());
            eduVo.setMajor(primaryEdu.getMajor());
            eduVo.setDegreeLevel(degreeStr);
            vo.setPrimaryEducation(eduVo);
        }

        return vo;
    }

    // 辅助方法：将学历数字转换为字符串
    private String mapDegreeToString(Integer degree) {
        if (degree == null) return "本科";
        return switch (degree) {
            case 0 -> "本科";
            case 1 -> "硕士";
            case 2 -> "博士";
            default -> "其他";
        };
    }
}