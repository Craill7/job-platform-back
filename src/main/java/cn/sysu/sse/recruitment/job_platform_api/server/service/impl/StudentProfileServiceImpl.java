package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.StudentProfileUpdateDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.EducationExperience;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Student;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Tag;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.User;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.StudentProfileVO;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.EducationExperienceMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.StudentMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.TagMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.UserMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.service.StudentProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentProfileServiceImpl implements StudentProfileService {

    private static final Logger logger = LoggerFactory.getLogger(StudentProfileServiceImpl.class);
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter MONTH_FMT = DateTimeFormatter.ofPattern("yyyy-MM");

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EducationExperienceMapper educationExperienceMapper;
    @Autowired
    private TagMapper tagMapper;

    @Override
    public StudentProfileVO getMyProfile(Integer userId) {
        // 1. 获取基础信息
        Student student = studentMapper.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "学生信息不存在"));
        User user = userMapper.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "账号信息不存在"));

        // 2. 获取教育经历 (取第一条作为主学历)
        List<EducationExperience> eduList = educationExperienceMapper.findByStudentUserId(userId);
        EducationExperience primaryEdu = eduList.isEmpty() ? null : eduList.get(0);

        // 3. 获取标签
        List<Tag> tags = tagMapper.findTagsByStudentId(userId);

        // 4. 组装 VO
        StudentProfileVO vo = new StudentProfileVO();
        vo.setAvatarUrl(student.getAvatarUrl());

        // Basic Info
        StudentProfileVO.BasicInfo basic = new StudentProfileVO.BasicInfo();
        basic.setFullName(student.getFullName());
        basic.setGender(student.getGender() == null ? "" : (student.getGender() == 0 ? "男" : "女"));
        basic.setDateOfBirth(student.getDateOfBirth() != null ? student.getDateOfBirth().format(DATE_FMT) : "");
        basic.setJobSeekingStatus(mapJobStatusToString(student.getJobSeekingStatus()));
        basic.setEmail(user.getEmail());
        basic.setPhoneNumber(student.getPhoneNumber());
        vo.setBasicInfo(basic);

        // Primary Education
        StudentProfileVO.PrimaryEducation eduVo = new StudentProfileVO.PrimaryEducation();
        if (primaryEdu != null) {
            eduVo.setSchoolName(primaryEdu.getSchoolName());
            eduVo.setDegreeLevel(mapDegreeToString(primaryEdu.getDegreeLevel()));
            eduVo.setMajor(primaryEdu.getMajor());
            eduVo.setMajorRank(primaryEdu.getMajorRank());
            eduVo.setStartDate(primaryEdu.getStartDate() != null ? primaryEdu.getStartDate().format(MONTH_FMT) : "");
            eduVo.setEndDate(primaryEdu.getEndDate() != null ? primaryEdu.getEndDate().format(MONTH_FMT) : "");
        }
        vo.setPrimaryEducation(eduVo);

        // Expected Job
        StudentProfileVO.ExpectedJob jobVo = new StudentProfileVO.ExpectedJob();
        jobVo.setExpectedPosition(student.getExpectedPosition());
        if (student.getExpectedMinSalary() != null) {
            String salary = student.getExpectedMinSalary() +
                    (student.getExpectedMaxSalary() != null ? "-" + student.getExpectedMaxSalary() : "以上");
            jobVo.setExpectedSalary(salary);
        }
        vo.setExpectedJob(jobVo);

        // Tags
        vo.setPersonalTags(tags.stream().map(t -> {
            StudentProfileVO.PersonalTag pt = new StudentProfileVO.PersonalTag();
            pt.setTagId(t.getId());
            pt.setName(t.getName());
            return pt;
        }).collect(Collectors.toList()));

        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMyBaseProfile(Integer userId, StudentProfileUpdateDTO dto) {
        // 1. 更新 Student 表
        Student student = studentMapper.findByUserId(userId).orElse(new Student());
        student.setUserId(userId);

        var basic = dto.getBasicInfo();
        student.setFullName(basic.getFullName());
        student.setPhoneNumber(basic.getPhoneNumber());
        student.setGender("男".equals(basic.getGender()) ? 0 : 1);
        try {
            if (basic.getDateOfBirth() != null && !basic.getDateOfBirth().isEmpty()) {
                student.setDateOfBirth(LocalDate.parse(basic.getDateOfBirth(), DATE_FMT));
            }
        } catch (Exception e) {
            logger.warn("日期解析失败: {}", basic.getDateOfBirth());
        }
        student.setJobSeekingStatus(mapStringToJobStatus(basic.getJobSeekingStatus()));

        var job = dto.getExpectedJob();
        student.setExpectedPosition(job.getExpectedPosition());
        parseAndSetSalary(student, job.getExpectedSalary());

        if (studentMapper.findByUserId(userId).isPresent()) {
            studentMapper.update(student);
        } else {
            studentMapper.insert(student);
        }

        // 2. 更新 User 表 (Email)
        if (basic.getEmail() != null) {
            User user = userMapper.findById(userId).orElseThrow();
            user.setEmail(basic.getEmail());
            userMapper.update(user);
        }

        // 3. 更新 Education (主要学历)
        var eduDto = dto.getPrimaryEducation();
        List<EducationExperience> eduList = educationExperienceMapper.findByStudentUserId(userId);
        EducationExperience edu;
        if (eduList.isEmpty()) {
            edu = new EducationExperience();
            edu.setStudentUserId(userId);
        } else {
            edu = eduList.get(0); // 更新第一条作为主学历
        }
        edu.setSchoolName(eduDto.getSchoolName());
        edu.setMajor(eduDto.getMajor());
        edu.setMajorRank(eduDto.getMajorRank());
        edu.setDegreeLevel(mapStringToDegree(eduDto.getDegreeLevel()));

        // 处理日期 (支持 yyyy-MM 格式)
        try {
            if (eduDto.getStartDate() != null && !eduDto.getStartDate().isEmpty()) {
                edu.setStartDate(LocalDate.parse(eduDto.getStartDate() + "-01", DATE_FMT));
            }
            if (eduDto.getEndDate() != null && !eduDto.getEndDate().isEmpty()) {
                edu.setEndDate(LocalDate.parse(eduDto.getEndDate() + "-01", DATE_FMT));
            }
        } catch (Exception e) {
            logger.warn("教育经历日期解析失败", e);
        }

        if (edu.getId() == null) {
            educationExperienceMapper.insert(edu);
        } else {
            educationExperienceMapper.update(edu);
        }

        // 4. 更新 Tags
        if (dto.getPersonalTagIds() != null) {
            tagMapper.deleteStudentTagsByUserId(userId);
            if (!dto.getPersonalTagIds().isEmpty()) {
                tagMapper.batchInsertStudentTags(userId, dto.getPersonalTagIds());
            }
        }
    }

    @Override
    public String uploadAvatar(Integer userId, MultipartFile file) {
        // 模拟上传，实际应接入 OSS/MinIO
        String mockUrl = "https://api.dicebear.com/7.x/avataaars/svg?seed=" + userId;
        Student student = studentMapper.findByUserId(userId).orElse(new Student());
        student.setUserId(userId);
        student.setAvatarUrl(mockUrl);

        if (studentMapper.findByUserId(userId).isPresent()) {
            studentMapper.update(student);
        } else {
            studentMapper.insert(student);
        }
        return mockUrl;
    }

    // --- 辅助转换方法 ---

    private String mapJobStatusToString(Integer status) {
        if (status == null) return "";
        return switch (status) {
            case 0 -> "暂不考虑";
            case 1 -> "寻求实习";
            case 2 -> "寻求校招";
            case 3 -> "积极求职";
            default -> "";
        };
    }

    private Integer mapStringToJobStatus(String status) {
        if (status == null) return 1;
        if (status.contains("不")) return 0;
        if (status.contains("实习")) return 1;
        if (status.contains("校招") || status.contains("全职") || status.contains("应届")) return 2;
        return 1; // 默认寻求实习
    }

    private String mapDegreeToString(Integer degree) {
        if (degree == null) return "本科";
        return switch (degree) {
            case 0 -> "本科";
            case 1 -> "硕士";
            case 2 -> "博士";
            default -> "本科";
        };
    }

    private Integer mapStringToDegree(String degree) {
        if (degree == null) return 0;
        if (degree.contains("硕")) return 1;
        if (degree.contains("博")) return 2;
        return 0;
    }

    private void parseAndSetSalary(Student student, String salaryStr) {
        if (salaryStr == null) return;
        try {
            // 提取字符串中的第一个数字作为 min_salary
            String num = salaryStr.replaceAll("[^0-9-]", "");
            if (num.contains("-")) {
                String[] parts = num.split("-");
                student.setExpectedMinSalary(Integer.parseInt(parts[0]));
                student.setExpectedMaxSalary(Integer.parseInt(parts[1]));
            } else if (!num.isEmpty()) {
                student.setExpectedMinSalary(Integer.parseInt(num));
            }
        } catch (Exception e) {
            // ignore
        }
    }
}
