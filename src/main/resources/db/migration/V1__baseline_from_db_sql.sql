-- Baseline schema imported from db.sql

-- 1. 用户与权限模块
-- 1.1 users (用户主表)
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '用户唯一ID',
    email VARCHAR(255) NOT NULL UNIQUE COMMENT '登录邮箱',
    password_hash VARCHAR(255) NOT NULL COMMENT '加密后的密码',
    role INT NOT NULL COMMENT '用户角色 1=student, 2=hr',
    status INT NOT NULL DEFAULT 0 COMMENT '账户状态 0=pending, 1=active, 2=disabled',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login_at DATETIME NULL COMMENT '最近登录时间'
) COMMENT '用户主表，存储通用登录信息';

-- 1.2 students (学生信息表)
CREATE TABLE students (
    user_id INT PRIMARY KEY COMMENT '关联 users.id',
    student_id VARCHAR(50) NULL UNIQUE COMMENT '学号 (唯一，可为空)',
    avatar_url VARCHAR(1024) NULL COMMENT '头像(主档案)',
    full_name VARCHAR(100) NULL COMMENT '(主档案) 姓名',
    phone_number VARCHAR(20) NULL COMMENT '(主档案) 手机号',
    gender INT NULL COMMENT '(主档案) 性别, 0=男, 1=女',
    date_of_birth DATE NULL COMMENT '(主档案) 出生日期',
    job_seeking_status INT NULL COMMENT '求职状态 0=在校-暂不考虑, 1=在校-寻求实习, 2=应届-寻求实习, 3=应届-寻求校招',
    expected_position VARCHAR(100) NULL COMMENT '(主档案) 期望岗位',
    expected_min_salary INT NULL COMMENT '期望最少薪资(单位k)',
    expected_max_salary INT NULL COMMENT '期望最多薪资(单位k)',
    skills_summary TEXT NULL COMMENT '技能掌握',
    current_template_id BIGINT NULL COMMENT '当前选用的模板ID',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) COMMENT '学生信息表，与 users 表一对一关联';

-- 1.3 education_experiences (教育经历表)
CREATE TABLE education_experiences (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '唯一的经历ID',
    student_user_id INT NOT NULL COMMENT '关联的学生用户ID (users.id)',
    school_name VARCHAR(255) NULL COMMENT '学校名称',
    degree_level INT NULL COMMENT '层次 0=本科, 1=硕士, 2=博士',
    major VARCHAR(100) NULL COMMENT '专业',
    start_date DATE NULL COMMENT '入学日期',
    end_date DATE NULL COMMENT '毕业日期',
    major_rank VARCHAR(50) NULL COMMENT '专业排名',
    FOREIGN KEY (student_user_id) REFERENCES users(id) ON DELETE CASCADE
) COMMENT '教育经历表，学生主档案的一部分';

-- 1.4 companies (公司信息表)
CREATE TABLE companies (
    company_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '公司唯一ID (作为主键)',
    user_id INT NOT NULL UNIQUE COMMENT '关联 users.id (用于登录)',
    company_name VARCHAR(255) NOT NULL COMMENT '公司名称(唯一)',
    description TEXT NULL COMMENT '公司介绍',
    logo_url VARCHAR(255) NULL COMMENT '公司Logo图片地址',
    industry_id INT NULL COMMENT '行业领域ID (关联 t_industries.id)',
    nature_id INT NULL COMMENT '企业性质ID (关联 t_company_natures.id)',
    company_scale_id INT NULL COMMENT '公司规模ID (关联 t_company_scales.id)',
    company_address VARCHAR(255) NULL COMMENT '公司地址',
    contact_person_name VARCHAR(100) NULL COMMENT '联系人姓名',
    contact_person_phone VARCHAR(50) NULL COMMENT '联系人电话',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) COMMENT '存储企业的基本信息';

-- 1.5 company_links (企业链接表)
CREATE TABLE company_links (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '链接记录唯一ID',
    company_id INT NOT NULL COMMENT '关联 companies.company_id',
    link_name VARCHAR(100) NOT NULL COMMENT '链接名称 (如: "企业官网")',
    link_url VARCHAR(255) NOT NULL COMMENT '链接地址',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES companies(company_id) ON DELETE CASCADE
) COMMENT '企业链接表';


-- 2. 学生与求职模块
-- 2.1 work_experiences (实习/工作经历表)
CREATE TABLE work_experiences (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '唯一的经历ID',
    student_user_id INT NOT NULL COMMENT '关联的学生ID (users.id)',
    company_name VARCHAR(255) NULL COMMENT '公司名称',
    position_title VARCHAR(100) NULL COMMENT '职位名称',
    start_date DATE NULL COMMENT '(存YYYY-MM)',
    end_date DATE NULL COMMENT '(存YYYY-MM)',
    description TEXT NULL COMMENT '工作内容',
    FOREIGN KEY (student_user_id) REFERENCES users(id) ON DELETE CASCADE
) COMMENT '实习/工作经历表';

-- 2.2 project_experiences (项目经历表)
CREATE TABLE project_experiences (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '唯一的经历ID',
    student_user_id INT NOT NULL COMMENT '关联的学生ID (users.id)',
    project_name VARCHAR(255) NULL COMMENT '项目名称',
    role VARCHAR(100) NULL COMMENT '项目角色',
    project_link VARCHAR(1024) NULL COMMENT '项目链接',
    start_date DATE NULL COMMENT '(存YYYY-MM)',
    end_date DATE NULL COMMENT '(存YYYY-MM)',
    description TEXT NULL COMMENT '项目描述',
    FOREIGN KEY (student_user_id) REFERENCES users(id) ON DELETE CASCADE
) COMMENT '项目经历表';

-- 2.3 organization_experiences (社团组织经历表)
CREATE TABLE organization_experiences (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '唯一的经历ID',
    student_user_id INT NOT NULL COMMENT '关联的学生ID (users.id)',
    organization_name VARCHAR(255) NULL COMMENT '组织名称',
    role VARCHAR(100) NULL COMMENT '担任角色',
    start_date DATE NULL COMMENT '(存YYYY-MM)',
    end_date DATE NULL COMMENT '(存YYYY-MM)',
    description TEXT NULL COMMENT '经历描述',
    FOREIGN KEY (student_user_id) REFERENCES users(id) ON DELETE CASCADE
) COMMENT '社团组织经历表';

-- 2.4 competition_experiences (竞赛经历表)
CREATE TABLE competition_experiences (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '唯一的经历ID',
    student_user_id INT NOT NULL COMMENT '关联的学生ID (users.id)',
    competition_name VARCHAR(255) NULL COMMENT '竞赛名称',
    role VARCHAR(100) NULL COMMENT '担任角色',
    award VARCHAR(100) NULL COMMENT '获得奖项',
    date VARCHAR(20) NULL COMMENT '获奖日期(存 YYYY-MM)',
    FOREIGN KEY (student_user_id) REFERENCES users(id) ON DELETE CASCADE
) COMMENT '竞赛经历表';

-- 2.6 resume (pdf简历表)
CREATE TABLE resume (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '唯一的附件ID',
    student_user_id INT NOT NULL COMMENT '关联的学生ID (users.id)',
    file_name VARCHAR(255) NULL COMMENT '文件名(例如: "周意简历-前端.pdf")',
    file_url VARCHAR(1024) NOT NULL COMMENT '存储在云上的真实路径或 Key',
    file_size BIGINT NULL COMMENT '文件大小(单位: bytes)',
    usage_type VARCHAR(50) NULL COMMENT '用途(例如: "resume_pdf", "portfolio")',
    uploaded_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_user_id) REFERENCES users(id) ON DELETE CASCADE
) COMMENT 'pdf简历表';

-- 2.6 job_favorites (岗位收藏表)
CREATE TABLE job_favorites (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '唯一的收藏ID',
    student_user_id INT NOT NULL COMMENT '关联的学生ID (users.id)',
    job_id INT NOT NULL COMMENT '关联的职位ID (jobs.id)',
    saved_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_student_job (student_user_id, job_id)
    -- 外键约束将在 jobs 表创建后添加
) COMMENT '岗位收藏表（学生-岗位多对多）';

-- 2.7 events (求职活动表)
CREATE TABLE events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '唯一的活动ID',
    admin_user_id INT NOT NULL COMMENT '发布的管理员/HR ID(关联 users.id)',
    event_title VARCHAR(255) NOT NULL COMMENT '活动标题',
    event_description TEXT NULL COMMENT '活动详情',
    event_start_time DATETIME NOT NULL COMMENT '活动开始时间',
    event_end_time DATETIME NULL COMMENT '活动结束时间',
    event_location VARCHAR(255) NULL COMMENT '活动地点',
    event_type VARCHAR(50) NULL COMMENT '活动类型(例如:"宣讲会", "招聘会")',
    target_audience INT NOT NULL DEFAULT 0 COMMENT '目标受众 0=all, 1=students, 2=companies',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (admin_user_id) REFERENCES users(id) ON DELETE CASCADE
) COMMENT '求职活动表（宣讲会、招聘会等）';


-- 3. 企业与招聘模块
-- 3.1 jobs (岗位信息表)
CREATE TABLE jobs (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '岗位唯一ID',
    company_id INT NOT NULL COMMENT '关联 companies.company_id',
    posted_by_user_id INT NOT NULL COMMENT '关联users.id (具体发布的HR)',
    title VARCHAR(255) NULL COMMENT '岗位名称',
    description TEXT NULL COMMENT '岗位描述',
    min_salary INT NULL COMMENT '最少薪资(单位k)',
    max_salary INT NULL COMMENT '最多薪资(单位k)',
    location VARCHAR(255) NULL COMMENT '工作地点',
    work_nature INT NULL COMMENT '岗位性质 1=internship, 2=full-time',
    deadline DATE NULL COMMENT '招聘截止日期',
    status INT NULL COMMENT '岗位状态 10=pending, 20=approved, 30=rejected, 40=closed',
    created_at DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    type INT NULL COMMENT '岗位类别id (关联 t_job_categories.id)',
    department VARCHAR(100) NULL COMMENT '所属部门',
    headcount INT NULL COMMENT '招聘人数',
    required_degree INT NULL COMMENT '学历要求 0=本科以上, 1=硕士以上, 2=博士以上',
    required_start_date DATE NULL COMMENT '岗位要求入职时间',
    bonus_points TEXT NULL COMMENT '岗位加分项, 存数组/JSON',
    work_address VARCHAR(255) NULL COMMENT '详细工作地址',
    FOREIGN KEY (company_id) REFERENCES companies(company_id) ON DELETE CASCADE,
    FOREIGN KEY (posted_by_user_id) REFERENCES users(id) ON DELETE NO ACTION
) COMMENT '岗位信息表，草稿也存在该表中';

-- 3.2 applications (投递记录表)
CREATE TABLE applications (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '投递记录唯一ID',
    job_id INT NOT NULL COMMENT '关联 jobs.id',
    student_user_id INT NOT NULL COMMENT '关联 users.id (学生)',
    resume_id BIGINT NOT NULL COMMENT '投递时使用的简历PDF ID (resume.id)',
    status INT NOT NULL COMMENT '投递状态 10=已投递, 20=设置为候选人, 30=邀请面试, 40=通过, 50=拒绝',
    submitted_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE CASCADE,
    FOREIGN KEY (student_user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (resume_id) REFERENCES resume(id) ON DELETE NO ACTION
) COMMENT '核心关联表，记录学生、岗位和简历的投递关系';

-- 2.8 event_related_jobs (活动-岗位关联表)
CREATE TABLE event_related_jobs (
    event_id BIGINT NOT NULL COMMENT '关联 events.id',
    job_id INT NOT NULL COMMENT '关联 jobs.id',
    PRIMARY KEY (event_id, job_id),
    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
    FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE CASCADE
) COMMENT '活动-岗位关联表';

-- 2.6 job_favorites (添加外键)
ALTER TABLE job_favorites
    ADD CONSTRAINT fk_jobfav_job FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_jobfav_student FOREIGN KEY (student_user_id) REFERENCES users(id) ON DELETE CASCADE;


-- 4. 平台管理模块 (共享部分)
-- 4.1 tags (技术与能力标签表)
CREATE TABLE tags (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '标签唯一ID',
    name VARCHAR(100) NOT NULL UNIQUE COMMENT '标签名称',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    category_id INT NOT NULL COMMENT '所属分类ID (关联 tag_categories.id)'
    -- FOREIGN KEY (category_id) REFERENCES tag_categories(id) -- 
) COMMENT '由管理员维护的标签库';

-- 4.2 job_tags (岗位-标签关联表)
CREATE TABLE job_tags (
    job_id INT NOT NULL COMMENT '关联 jobs.id',
    tag_id INT NOT NULL COMMENT '关联 tags.id',
    PRIMARY KEY (job_id, tag_id),
    FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
) COMMENT '岗位与技术标签的多对多关系';

-- 4.3 students_tags (个人-标签关联表)
CREATE TABLE students_tags (
    user_id INT NOT NULL COMMENT '关联 students.user_id',
    tag_id INT NOT NULL COMMENT '关联 tags.id',
    PRIMARY KEY (user_id, tag_id),
    FOREIGN KEY (user_id) REFERENCES students(user_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
) COMMENT '个人与技术标签的多对多关系';


-- 索引建议 (基于PDF P14)
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_students_student_id ON students(student_id);
CREATE INDEX idx_jobs_company_id ON jobs(company_id);
CREATE INDEX idx_jobs_status ON jobs(status);
CREATE INDEX idx_jobs_location ON jobs(location);
-- CREATE INDEX idx_jobs_job_type ON jobs(job_type); -- PDF中 jobs 表没有 job_type 字段, 而是 work_nature
CREATE INDEX idx_jobs_work_nature ON jobs(work_nature); 
CREATE INDEX idx_applications_job_id ON applications(job_id);
CREATE INDEX idx_applications_student_user_id ON applications(student_user_id);
CREATE INDEX idx_applications_status ON applications(status);
-- job_favorites 已有复合唯一键 uk_student_job, 可兼作索引

