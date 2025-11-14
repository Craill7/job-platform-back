package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.server.service.RegisterService;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.User;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Student;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Company;
import cn.sysu.sse.recruitment.job_platform_api.common.enums.UserRole;
import cn.sysu.sse.recruitment.job_platform_api.common.enums.UserStatus;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.RegisterVO;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.UserMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.StudentMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.CompanyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 注册服务实现
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用户注册
     * @param email 用户邮箱
     * @param password 用户密码
     * @param verificationCode 验证码
     * @param roleStr 角色字符串
     * @return 注册结果
     */
    @Override
    @Transactional
    public RegisterVO register(String email, String password, String verificationCode, String roleStr) {
        logger.info("开始处理注册请求，邮箱：{}，角色：{}", email, roleStr);
        // 验证码校验（占位实现：验证码为123456）
        if (!"123456".equals(verificationCode)) {
            logger.warn("注册失败，验证码错误：{}", email);
            return RegisterVO.of(400, "验证码错误");
        }

        roleStr = roleStr == null ? "" : roleStr.trim().toLowerCase();
        if (!"student".equals(roleStr) && !"hr".equals(roleStr)) {
            logger.warn("注册失败，角色参数错误：{}，角色：{}", email, roleStr);
            return RegisterVO.of(400, "参数错误：role 仅支持 student/hr");
        }
        UserRole role = "hr".equals(roleStr) ? UserRole.HR : UserRole.STUDENT;

        // 对于企业用户(hr)，需要验证邮箱格式
        if (role == UserRole.HR) {
            if (!isValidEmail(email)) {
                logger.warn("注册失败，邮箱格式不正确：{}", email);
                return RegisterVO.of(400, "邮箱格式不正确");
            }
        }
        // 学生邮箱自动拼接后缀（若未包含 @）
        String processedEmail = email.trim();
        if (role == UserRole.STUDENT && !processedEmail.contains("@")) {
            processedEmail = processedEmail + "@mail2.sysu.edu.cn";
        }

        // 邮箱唯一性
        Optional<User> existing = userMapper.findByEmail(processedEmail);
        if (existing.isPresent()) {
            logger.warn("注册失败，邮箱已被注册：{}", processedEmail);
            return RegisterVO.of(400, "邮箱已被注册");
        }

        User user = new User();
        user.setEmail(processedEmail);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole(role);
        user.setStatus(role == UserRole.STUDENT ? UserStatus.ACTIVE : UserStatus.PENDING);
        userMapper.insert(user);

        // 如果是学生用户，自动创建 students 记录
        if (role == UserRole.STUDENT) {
            Student student = new Student();
            student.setUserId(user.getId());
            // student_id 现在可以为空，用户后续可以填写
            studentMapper.insert(student);
            logger.info("学生账户注册成功，邮箱：{}，用户ID：{}", processedEmail, user.getId());
            return RegisterVO.of(201, "学生账户注册成功");
        } else {
            // 如果是企业用户，自动创建 companies 记录
            Company company = new Company();
            company.setUserId(user.getId());
            // company_name 现在可以为空，企业后续可以填写
            companyMapper.insert(company);
            logger.info("企业账户注册成功，等待审核，邮箱：{}，用户ID：{}", processedEmail, user.getId());
            return RegisterVO.of(202, "企业账户注册成功，请等待管理员审核");
        }
    }

    /**
     * 简单的邮箱格式验证
     * @param email 邮箱地址
     * @return 是否为有效的邮箱格式
     */
    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
}
