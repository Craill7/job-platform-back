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
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentProfileServiceImpl implements StudentProfileService {

    private static final Logger logger = LoggerFactory.getLogger(StudentProfileServiceImpl.class);
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter MONTH_FMT = DateTimeFormatter.ofPattern("yyyy-MM");

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024L; // 5MB
    private static final int TARGET_SIZE = 512; // 头像目标尺寸 512x512
    private static final double OUTPUT_QUALITY = 0.8d; // 压缩质量



    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EducationExperienceMapper educationExperienceMapper;
    @Autowired
    private TagMapper tagMapper;

    @Value("${app.storage.student-avatar-dir}")
    private String avatarDir;

    @Value("${app.storage.student-avatar-url-prefix}")
    private String avatarUrlPrefix;

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
        basic.setStudentId(student.getStudentId());
        vo.setBasicInfo(basic);

        // Education List (修改点)
        List<StudentProfileVO.Education> eduVos = eduList.stream()
                // [新增] 排序逻辑：按学历等级升序 (0本科 -> 1硕士 -> 2博士)
                // 使用 lambda 表达式处理可能的 null 值，将其视为 0 (本科)
                .sorted(Comparator.comparing(edu -> edu.getDegreeLevel() == null ? 0 : edu.getDegreeLevel()))

                // 原有的映射逻辑保持不变
                .map(edu -> {
                    StudentProfileVO.Education eduVo = new StudentProfileVO.Education();
                    eduVo.setId(edu.getId());
                    eduVo.setSchoolName(edu.getSchoolName());
                    eduVo.setDegreeLevel(mapDegreeToString(edu.getDegreeLevel()));
                    eduVo.setMajor(edu.getMajor());
                    eduVo.setMajorRank(edu.getMajorRank());
                    eduVo.setStartDate(edu.getStartDate() != null ? edu.getStartDate().format(MONTH_FMT) : "");
                    eduVo.setEndDate(edu.getEndDate() != null ? edu.getEndDate().format(MONTH_FMT) : "");
                    return eduVo;
                })
                .collect(Collectors.toList());

        vo.setEducationExperiences(eduVos);

        // Expected Job
        StudentProfileVO.ExpectedJob jobVo = new StudentProfileVO.ExpectedJob();
        jobVo.setExpectedPosition(student.getExpectedPosition());
        jobVo.setExpectedMinSalary(student.getExpectedMinSalary());
        jobVo.setExpectedMaxSalary(student.getExpectedMaxSalary());
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
        student.setStudentId(basic.getStudentId());

        student.setGender("男".equals(basic.getGender()) ? 0 : 1);
        try {
            if (basic.getDateOfBirth() != null && !basic.getDateOfBirth().isEmpty()) {
                student.setDateOfBirth(LocalDate.parse(basic.getDateOfBirth(), DATE_FMT));
            }
        } catch (Exception e) {
            logger.warn("日期解析失败: {}", basic.getDateOfBirth());
        }
        student.setJobSeekingStatus(mapStringToJobStatus(basic.getJobSeekingStatus()));

        // Expected Job - [修改点：直接赋值 min/max 字段]
        var job = dto.getExpectedJob();
        student.setExpectedPosition(job.getExpectedPosition());
        student.setExpectedMinSalary(job.getExpectedMinSalary());
        student.setExpectedMaxSalary(job.getExpectedMaxSalary());


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

        // 3. 更新 Education (列表处理逻辑)
        List<StudentProfileUpdateDTO.EducationDTO> eduDtos = dto.getEducationExperiences();
        if (eduDtos != null) {
            // 获取数据库中现有的教育经历
            List<EducationExperience> existingEdus = educationExperienceMapper.findByStudentUserId(userId);
            Set<Long> existingIds = existingEdus.stream().map(EducationExperience::getId).collect(Collectors.toSet());
            Set<Long> incomingIds = eduDtos.stream()
                    .map(StudentProfileUpdateDTO.EducationDTO::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            // 3.1 删除不在新列表中的旧记录
            for (Long id : existingIds) {
                if (!incomingIds.contains(id)) {
                    educationExperienceMapper.deleteById(id);
                }
            }

            // 3.2 插入或更新
            for (StudentProfileUpdateDTO.EducationDTO eduDto : eduDtos) {
                EducationExperience edu = new EducationExperience();
                edu.setStudentUserId(userId);
                edu.setSchoolName(eduDto.getSchoolName());
                edu.setMajor(eduDto.getMajor());
                edu.setMajorRank(eduDto.getMajorRank());
                edu.setDegreeLevel(mapStringToDegree(eduDto.getDegreeLevel()));

                // 处理日期
                try {
                    if (eduDto.getStartDate() != null && !eduDto.getStartDate().isEmpty()) {
                        edu.setStartDate(LocalDate.parse(eduDto.getStartDate() , DATE_FMT));
                    }
                    if (eduDto.getEndDate() != null && !eduDto.getEndDate().isEmpty()) {
                        edu.setEndDate(LocalDate.parse(eduDto.getEndDate() , DATE_FMT));
                    }
                } catch (Exception e) {
                    logger.warn("教育经历日期解析失败", e);
                }

                if (eduDto.getId() != null && existingIds.contains(eduDto.getId())) {
                    // 更新
                    edu.setId(eduDto.getId());
                    educationExperienceMapper.update(edu);
                } else {
                    // 新增
                    educationExperienceMapper.insert(edu);
                }
            }
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
    @Transactional
    public String uploadAvatar(Integer userId, MultipartFile file) {
        logger.info("上传学生头像，userId={}，文件名={}", userId, file != null ? file.getOriginalFilename() : "null");

        // 基础校验
        if (userId == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户未登录");
        }
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "文件不能为空");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "文件大小不能超过5MB");
        }

        // 读取并校验图片格式
        byte[] originalBytes;
        try {
            originalBytes = file.getBytes();
        } catch (IOException e) {
            logger.error("读取文件失败", e);
            throw new BusinessException(ErrorCode.INTERNAL_ERROR, "读取文件失败");
        }

        if (detectImageType(originalBytes) == null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "仅支持 JPEG/PNG/WebP 图片格式");
        }

        // 处理图片 (裁剪为正方形并压缩)
        // 尝试使用WebP格式输出以节省空间，如果不支持则回退到JPG
        String outputFormat = supportsWebp() ? "webp" : "jpg";
        byte[] processedBytes = processImage(originalBytes, outputFormat);

        // 保存文件
        String filename = buildFilename(outputFormat);
        saveFile(processedBytes, filename);

        // 生成 URL 并更新数据库
        String url = buildAccessibleUrl(filename);

        Student student = studentMapper.findByUserId(userId).orElse(new Student());
        student.setUserId(userId);
        student.setAvatarUrl(url);

        if (studentMapper.findByUserId(userId).isPresent()) {
            studentMapper.update(student);
        } else {
            studentMapper.insert(student);
        }

        logger.info("学生头像上传成功，userId={}，url={}", userId, url);
        return url;
    }
    // 图片处理辅助方法 (复用自 CompanyLogoServiceImpl) ---
    private byte[] processImage(byte[] source, String outputFormat) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // 自动裁剪为正方形中心区域
            Thumbnails.of(new ByteArrayInputStream(source))
                    .size(TARGET_SIZE, TARGET_SIZE)
                    .crop(Positions.CENTER)
                    .outputQuality(OUTPUT_QUALITY)
                    .outputFormat(outputFormat)
                    .toOutputStream(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            logger.error("处理图片失败", e);
            throw new BusinessException(ErrorCode.INTERNAL_ERROR, "图片处理失败，请检查文件是否损坏");
        }
    }

    private void saveFile(byte[] bytes, String filename) {
        try {
            Path dir = Paths.get(avatarDir).toAbsolutePath().normalize();
            Files.createDirectories(dir); // 自动创建目录
            Path filePath = dir.resolve(filename);
            Files.write(filePath, bytes);
        } catch (IOException e) {
            logger.error("保存头像文件失败", e);
            throw new BusinessException(ErrorCode.INTERNAL_ERROR, "存储图片失败");
        }
    }

    private String buildFilename(String extension) {
        // 使用 UUID 生成随机文件名，避免中文乱码和冲突
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid + "." + extension.toLowerCase(Locale.ROOT);
    }

    private String buildAccessibleUrl(String filename) {
        String prefix = avatarUrlPrefix.endsWith("/") ? avatarUrlPrefix : avatarUrlPrefix + "/";
        return prefix + filename;
    }

    // 简单的魔数检查
    private String detectImageType(byte[] data) {
        if (data.length < 12) return null;
        if ((data[0] & 0xFF) == 0xFF && (data[1] & 0xFF) == 0xD8) return "jpg";
        if ((data[0] & 0xFF) == 0x89 && data[1] == 0x50 && data[2] == 0x4E && data[3] == 0x47) return "png";
        if (data[0] == 'R' && data[1] == 'I' && data[2] == 'F' && data[3] == 'F' && data[8] == 'W' && data[9] == 'E' && data[10] == 'B' && data[11] == 'P') return "webp";
        return null;
    }

    private boolean supportsWebp() {
        return ImageIO.getImageWritersByFormatName("webp").hasNext();
    }
    // --- 辅助转换方法 ---

    // [修改点] 将数据库 INT 转换为 指定中文文本
    private String mapJobStatusToString(Integer status) {
        if (status == null) return "";
        return switch (status) {
            case 0 -> "在校-暂不考虑";
            case 1 -> "在校-寻求实习";
            case 2 -> "应届-寻求实习";
            case 3 -> "应届-寻求校招";
            default -> "";
        };
    }

    // [修改点] 将前端传来的中文文本 转换为 数据库 INT
    private Integer mapStringToJobStatus(String status) {
        if (status == null) return 0;
        if (status.contains("暂不考虑")) return 0;
        if (status.contains("在校") && status.contains("实习")) return 1;
        if (status.contains("应届") && status.contains("实习")) return 2;
        if (status.contains("校招") || (status.contains("应届") && !status.contains("实习"))) return 3;
        return 0; // 默认值
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
