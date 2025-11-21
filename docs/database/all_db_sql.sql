-- Consolidated SQL from src/main/resources/db/migration
-- Generated on 2025-11-19 for database documentation support

-- ========== V1__baseline_from_db_sql.sql ==========
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

-- ========== V2__make_company_name_nullable.sql ==========
ALTER TABLE companies
    MODIFY company_name VARCHAR(255) NULL;

-- ========== V3__add_position_center_indexes.sql ==========
-- 为求职中心功能添加性能优化索引

-- 1. 为jobs表的type字段添加索引（用于职能类别筛选）
CREATE INDEX idx_jobs_type ON jobs(type);

-- 2. 为job_tags表的tag_id添加索引（用于技能标签筛选）
CREATE INDEX idx_job_tags_tag_id ON job_tags(tag_id);

-- 3. 为companies表的company_name添加索引（用于关键词搜索）
CREATE INDEX idx_companies_company_name ON companies(company_name);

-- 4. 为job_favorites表的student_user_id添加索引（用于查询用户收藏列表）
-- 注意：虽然已有唯一键uk_student_job，但单独索引student_user_id可以优化按用户查询收藏列表的性能
CREATE INDEX idx_job_favorites_student_user_id ON job_favorites(student_user_id);

-- 5. 为job_favorites表的saved_at添加索引（用于按收藏时间排序）
CREATE INDEX idx_job_favorites_saved_at ON job_favorites(saved_at);

-- 6. 为applications表添加复合索引（用于查询学生是否已投递某岗位）
CREATE INDEX idx_applications_job_student ON applications(job_id, student_user_id);

-- 7. 为resume表的student_user_id添加索引（用于查询用户的简历列表）
CREATE INDEX idx_resume_student_user_id ON resume(student_user_id);

-- ========== V4__add_resume_center_indexes.sql ==========
-- 为简历中心功能添加性能优化索引

-- 1. 工作经历表索引
-- 优化按学生用户ID查询（用于获取用户的所有工作经历）
CREATE INDEX idx_work_experiences_student_user_id ON work_experiences(student_user_id);

-- 优化按学生用户ID查询并按开始日期排序（用于按时间倒序显示）
CREATE INDEX idx_work_experiences_student_start_date ON work_experiences(student_user_id, start_date DESC);

-- 2. 项目经历表索引
CREATE INDEX idx_project_experiences_student_user_id ON project_experiences(student_user_id);
CREATE INDEX idx_project_experiences_student_start_date ON project_experiences(student_user_id, start_date DESC);

-- 3. 组织经历表索引
CREATE INDEX idx_organization_experiences_student_user_id ON organization_experiences(student_user_id);
CREATE INDEX idx_organization_experiences_student_start_date ON organization_experiences(student_user_id, start_date DESC);

-- 4. 竞赛经历表索引
CREATE INDEX idx_competition_experiences_student_user_id ON competition_experiences(student_user_id);
-- 竞赛经历按date字段排序，但date是VARCHAR类型，索引效果有限，但仍可优化查询
CREATE INDEX idx_competition_experiences_student_date ON competition_experiences(student_user_id, date DESC);

-- 5. 教育经历表索引
CREATE INDEX idx_education_experiences_student_user_id ON education_experiences(student_user_id);
CREATE INDEX idx_education_experiences_student_start_date ON education_experiences(student_user_id, start_date DESC);

-- 6. 简历文件表索引（补充）
-- V3中已经添加了student_user_id索引，这里添加uploaded_at索引用于排序优化
CREATE INDEX idx_resume_uploaded_at ON resume(uploaded_at DESC);

-- ========== V5__add_company_lookup_tables.sql ==========
-- Flyway Migration Script
-- Version: 5
-- Description: Add lookup tables for company industry, nature, and scale, and link to companies table.

-- ----------------------------
-- 1. Table structure for t_industries (行业领域表)
-- ----------------------------
CREATE TABLE t_industries (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '行业唯一ID',
    name VARCHAR(100) NOT NULL UNIQUE COMMENT '行业名称'
) COMMENT='行业领域字典表';

-- ----------------------------
-- 2. Records for t_industries
-- ----------------------------
INSERT INTO t_industries (id, name) VALUES
(1, '互联网/电子商务'),
(2, '计算机软件'),
(3, 'IT服务/系统集成'),
(4, '通信/电信运营'),
(5, '电子/半导体/集成电路'),
(6, '硬件/智能设备'),
(7, '金融(银行/证券/保险)'),
(8, '房地产/建筑/物业'),
(9, '制造业/工业自动化'),
(10, '广告/传媒/公关'),
(11, '教育/培训/科研'),
(12, '医疗/制药/生物工程'),
(13, '专业服务(咨询/法律/会计)'),
(14, '快速消费品(FMCG)'),
(15, '零售/批发'),
(16, '交通/物流/仓储'),
(17, '能源/化工/环保'),
(18, '汽车及零部件'),
(19, '服务业(生活/娱乐/餐饮)'),
(20, '政府/非营利组织'),
(99, '其他');

-- ----------------------------
-- 3. Table structure for t_company_natures (企业性质表)
-- ----------------------------
CREATE TABLE t_company_natures (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '企业性质唯一ID',
    name VARCHAR(100) NOT NULL UNIQUE COMMENT '企业性质名称'
) COMMENT='企业性质字典表';

-- ----------------------------
-- 4. Records for t_company_natures
-- ----------------------------
INSERT INTO t_company_natures (id, name) VALUES
(1, '国有企业'),
(2, '集体企业'),
(3, '私营企业'),
(4, '联营企业'),
(5, '股份制企业'),
(6, '上市公司'),
(7, '中外合资企业'),
(8, '外商独资企业'),
(9, '港澳台商投资企业'),
(10, '事业单位'),
(11, '政府机关'),
(12, '非营利组织'),
(13, '个人独资企业'),
(14, '合伙企业'),
(99, '其他');

-- ----------------------------
-- 5. Table structure for t_company_scales (公司规模表)
-- ----------------------------
CREATE TABLE t_company_scales (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '公司规模唯一ID',
    scale VARCHAR(50) NOT NULL UNIQUE COMMENT '规模范围'
) COMMENT='公司规模字典表';

-- ----------------------------
-- 6. Records for t_company_scales
-- ----------------------------
INSERT INTO t_company_scales (id, scale) VALUES
(1, '0-20人'),
(2, '20-99人'),
(3, '100-499人'),
(4, '500-999人'),
(5, '1000-4999人'),
(6, '5000-9999人'),
(7, '10000人以上');

-- ----------------------------
-- 7. Update companies table with foreign key constraints
-- ----------------------------

-- 关联行业表
ALTER TABLE companies
ADD CONSTRAINT fk_companies_industry
FOREIGN KEY (industry_id) REFERENCES t_industries(id)
ON DELETE SET NULL ON UPDATE CASCADE;

-- 关联性质表
ALTER TABLE companies
ADD CONSTRAINT fk_companies_nature
FOREIGN KEY (nature_id) REFERENCES t_company_natures(id)
ON DELETE SET NULL ON UPDATE CASCADE;

-- 关联规模表
ALTER TABLE companies
ADD CONSTRAINT fk_companies_scale
FOREIGN KEY (company_scale_id) REFERENCES t_company_scales(id)
ON DELETE SET NULL ON UPDATE CASCADE;

-- ========== V6__Add_Tag_Categories.sql ==========
-- Flyway Migration Script
-- Version: 5
-- Description: 创建 tag_categories (标签分类) 表, 插入初始数据, 并与 tags 表建立外键关联

-- ----------------------------
-- 1. 创建 tag_categories (标签分类表)
-- ----------------------------
CREATE TABLE tag_categories (
    `id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '分类唯一ID',
    `code` VARCHAR(50) NOT NULL UNIQUE COMMENT '分类短码, 如 \'lang\', \'skill\'',
    `name` VARCHAR(100) NOT NULL COMMENT '分类名称, 如 \'编程语言\'',
    `description` VARCHAR(255) NULL COMMENT '分类描述',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='标签分类字典表';

-- ----------------------------
-- 2. 插入初始分类数据
-- (ID 99 作为 "未分类" 的保底项)
-- ----------------------------
INSERT INTO tag_categories (id, code, name, description)
VALUES
(1, 'lang', '编程语言', '例如 C++, Java, Python 等'),
(2, 'job_function', '职能', '例如 测试工程师, 项目管理 等'),
(3, 'framework', '技术框架/库', '例如 React, SpringBoot, TensorFlow 等'),
(4, 'tool', '工具/平台', '例如 Git, Docker, K8s 等'),
(5, 'soft_skill', '软技能', '例如 沟通能力, 团队协作 等'),
(99, 'other', '未分类', '用于归档所有未分类的标签');

-- ----------------------------
-- 3. [数据完整性] 更新现有的 'tags' 表
-- 将所有在 'tag_categories' 中找不到对应 ID 的标签，全部归类到 "未分类" (ID 99)
-- 
-- (!!!) 这是一个安全步骤，防止在下一步添加外键时，
-- 因为 'tags' 表中存在无效的 category_id (如 0 或已删除的ID) 而导致迁移失败。
-- ----------------------------
UPDATE tags t
LEFT JOIN tag_categories tc ON t.category_id = tc.id
SET t.category_id = 99
WHERE tc.id IS NULL;

-- ----------------------------
-- 4. 在 'tags' 表上添加外键约束
-- ----------------------------
ALTER TABLE tags
ADD CONSTRAINT fk_tags_category
FOREIGN KEY (category_id) REFERENCES tag_categories(id)
ON DELETE RESTRICT -- 不允许删除一个正在被标签使用的分类
ON UPDATE CASCADE;  -- 如果分类的ID变更，自动更新标签表

-- ========== V7__Add_tag_created_by_audit.sql ==========
-- Flyway Migration Script
-- Version: 7
-- Description: 为 tags 表增加 created_by 字段以记录自定义标签创建人

ALTER TABLE tags
	ADD COLUMN created_by INT NULL COMMENT '自定义标签创建人 (users.id)' AFTER created_at;

ALTER TABLE tags
	ADD CONSTRAINT fk_tags_created_by
	FOREIGN KEY (created_by) REFERENCES users(id)
	ON DELETE SET NULL
	ON UPDATE CASCADE;

-- ========== V8__Add_Job_Categories.sql ==========
-- Flyway Migration Script
-- Version: 8
-- Description: 添加岗位职能类别表 (t_job_categories) 并初始化数据，建立 jobs 表外键关联

-- ----------------------------
-- 1. 创建 t_job_categories 表
-- ----------------------------
CREATE TABLE t_job_categories (
    `id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '类别唯一ID',
    `name` VARCHAR(50) NOT NULL UNIQUE COMMENT '类别名称 (如: 研发, 产品)'
) COMMENT='岗位职能类别字典表';

-- ----------------------------
-- 2. 插入初始数据 (产品、测试、开发、算法)
-- ----------------------------
INSERT INTO t_job_categories (id, name) VALUES 
(1, '产品'),
(2, '测试'),
(3, '开发'),
(4, '算法');

-- ----------------------------
-- 3. 更新 jobs 表建立外键关联
-- ----------------------------
-- 假设 jobs 表中已经存在 type 字段 (INT 类型)
-- 如果现有数据中 type 字段有无效值（不在1-4之间），可能会导致添加外键失败。
-- 为了安全起见，先将 jobs 表中无效的 type 重置为 NULL (可选步骤，视现有数据情况而定)
UPDATE jobs SET type = NULL WHERE type NOT IN (SELECT id FROM t_job_categories);

-- 添加外键约束
ALTER TABLE jobs
ADD CONSTRAINT fk_jobs_category
FOREIGN KEY (type) REFERENCES t_job_categories(id)
ON DELETE SET NULL -- 如果类别被删除，岗位的类别设为 NULL
ON UPDATE CASCADE;

-- ========== V9__Split_Location_Add_City_Dict.sql ==========
-- Flyway Migration Script
-- Version: 9
-- Description: 拆分 jobs 表地址字段，添加省份(t_provinces)和城市(t_cities)字典表并初始化完整国标数据

-- ----------------------------
-- 1. 创建 t_provinces (省份表)
-- ----------------------------
CREATE TABLE t_provinces (
    `id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '省份ID',
    `code` VARCHAR(20) NULL COMMENT '行政区划代码',
    `name` VARCHAR(50) NOT NULL COMMENT '省份名称'
) COMMENT='省份字典表';

-- ----------------------------
-- 2. 创建 t_cities (城市表)
-- ----------------------------
CREATE TABLE t_cities (
    `id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '城市ID',
    `province_id` INT NOT NULL COMMENT '所属省份ID',
    `code` VARCHAR(20) NULL COMMENT '行政区划代码',
    `name` VARCHAR(50) NOT NULL COMMENT '城市名称',
    CONSTRAINT `fk_cities_province` FOREIGN KEY (`province_id`) REFERENCES `t_provinces` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT='城市字典表';

-- ----------------------------
-- 3. 初始化省份数据 (完整34个省级行政区)
-- ----------------------------
INSERT INTO t_provinces (id, code, name) VALUES 
(1, '110000', '北京市'),
(2, '120000', '天津市'),
(3, '130000', '河北省'),
(4, '140000', '山西省'),
(5, '150000', '内蒙古自治区'),
(6, '210000', '辽宁省'),
(7, '220000', '吉林省'),
(8, '230000', '黑龙江省'),
(9, '310000', '上海市'),
(10, '320000', '江苏省'),
(11, '330000', '浙江省'),
(12, '340000', '安徽省'),
(13, '350000', '福建省'),
(14, '360000', '江西省'),
(15, '370000', '山东省'),
(16, '410000', '河南省'),
(17, '420000', '湖北省'),
(18, '430000', '湖南省'),
(19, '440000', '广东省'),
(20, '450000', '广西壮族自治区'),
(21, '460000', '海南省'),
(22, '500000', '重庆市'),
(23, '510000', '四川省'),
(24, '520000', '贵州省'),
(25, '530000', '云南省'),
(26, '540000', '西藏自治区'),
(27, '610000', '陕西省'),
(28, '620000', '甘肃省'),
(29, '630000', '青海省'),
(30, '640000', '宁夏回族自治区'),
(31, '650000', '新疆维吾尔自治区'),
(32, '710000', '台湾省'),
(33, '810000', '香港特别行政区'),
(34, '820000', '澳门特别行政区');

-- ----------------------------
-- 4. 初始化城市数据 (地级市全量数据)
-- ----------------------------
INSERT INTO t_cities (province_id, code, name) VALUES 
-- 1. 北京市
(1, '110100', '北京市'),
-- 2. 天津市
(2, '120100', '天津市'),
-- 3. 河北省
(3, '130100', '石家庄市'), (3, '130200', '唐山市'), (3, '130300', '秦皇岛市'), (3, '130400', '邯郸市'), (3, '130500', '邢台市'), (3, '130600', '保定市'), (3, '130700', '张家口市'), (3, '130800', '承德市'), (3, '130900', '沧州市'), (3, '131000', '廊坊市'), (3, '131100', '衡水市'),
-- 4. 山西省
(4, '140100', '太原市'), (4, '140200', '大同市'), (4, '140300', '阳泉市'), (4, '140400', '长治市'), (4, '140500', '晋城市'), (4, '140600', '朔州市'), (4, '140700', '晋中市'), (4, '140800', '运城市'), (4, '140900', '忻州市'), (4, '141000', '临汾市'), (4, '141100', '吕梁市'),
-- 5. 内蒙古自治区
(5, '150100', '呼和浩特市'), (5, '150200', '包头市'), (5, '150300', '乌海市'), (5, '150400', '赤峰市'), (5, '150500', '通辽市'), (5, '150600', '鄂尔多斯市'), (5, '150700', '呼伦贝尔市'), (5, '150800', '巴彦淖尔市'), (5, '150900', '乌兰察布市'), (5, '152200', '兴安盟'), (5, '152500', '锡林郭勒盟'), (5, '152900', '阿拉善盟'),
-- 6. 辽宁省
(6, '210100', '沈阳市'), (6, '210200', '大连市'), (6, '210300', '鞍山市'), (6, '210400', '抚顺市'), (6, '210500', '本溪市'), (6, '210600', '丹东市'), (6, '210700', '锦州市'), (6, '210800', '营口市'), (6, '210900', '阜新市'), (6, '211000', '辽阳市'), (6, '211100', '盘锦市'), (6, '211200', '铁岭市'), (6, '211300', '朝阳市'), (6, '211400', '葫芦岛市'),
-- 7. 吉林省
(7, '220100', '长春市'), (7, '220200', '吉林市'), (7, '220300', '四平市'), (7, '220400', '辽源市'), (7, '220500', '通化市'), (7, '220600', '白山市'), (7, '220700', '松原市'), (7, '220800', '白城市'), (7, '222400', '延边朝鲜族自治州'),
-- 8. 黑龙江省
(8, '230100', '哈尔滨市'), (8, '230200', '齐齐哈尔市'), (8, '230300', '鸡西市'), (8, '230400', '鹤岗市'), (8, '230500', '双鸭山市'), (8, '230600', '大庆市'), (8, '230700', '伊春市'), (8, '230800', '佳木斯市'), (8, '230900', '七台河市'), (8, '231000', '牡丹江市'), (8, '231100', '黑河市'), (8, '231200', '绥化市'), (8, '232700', '大兴安岭地区'),
-- 9. 上海市
(9, '310100', '上海市'),
-- 10. 江苏省
(10, '320100', '南京市'), (10, '320200', '无锡市'), (10, '320300', '徐州市'), (10, '320400', '常州市'), (10, '320500', '苏州市'), (10, '320600', '南通市'), (10, '320700', '连云港市'), (10, '320800', '淮安市'), (10, '320900', '盐城市'), (10, '321000', '扬州市'), (10, '321100', '镇江市'), (10, '321200', '泰州市'), (10, '321300', '宿迁市'),
-- 11. 浙江省
(11, '330100', '杭州市'), (11, '330200', '宁波市'), (11, '330300', '温州市'), (11, '330400', '嘉兴市'), (11, '330500', '湖州市'), (11, '330600', '绍兴市'), (11, '330700', '金华市'), (11, '330800', '衢州市'), (11, '330900', '舟山市'), (11, '331000', '台州市'), (11, '331100', '丽水市'),
-- 12. 安徽省
(12, '340100', '合肥市'), (12, '340200', '芜湖市'), (12, '340300', '蚌埠市'), (12, '340400', '淮南市'), (12, '340500', '马鞍山市'), (12, '340600', '淮北市'), (12, '340700', '铜陵市'), (12, '340800', '安庆市'), (12, '341000', '黄山市'), (12, '341100', '滁州市'), (12, '341200', '阜阳市'), (12, '341300', '宿州市'), (12, '341500', '六安市'), (12, '341600', '亳州市'), (12, '341700', '池州市'), (12, '341800', '宣城市'),
-- 13. 福建省
(13, '350100', '福州市'), (13, '350200', '厦门市'), (13, '350300', '莆田市'), (13, '350400', '三明市'), (13, '350500', '泉州市'), (13, '350600', '漳州市'), (13, '350700', '南平市'), (13, '350800', '龙岩市'), (13, '350900', '宁德市'),
-- 14. 江西省
(14, '360100', '南昌市'), (14, '360200', '景德镇市'), (14, '360300', '萍乡市'), (14, '360400', '九江市'), (14, '360500', '新余市'), (14, '360600', '鹰潭市'), (14, '360700', '赣州市'), (14, '360800', '吉安市'), (14, '360900', '宜春市'), (14, '361000', '抚州市'), (14, '361100', '上饶市'),
-- 15. 山东省
(15, '370100', '济南市'), (15, '370200', '青岛市'), (15, '370300', '淄博市'), (15, '370400', '枣庄市'), (15, '370500', '东营市'), (15, '370600', '烟台市'), (15, '370700', '潍坊市'), (15, '370800', '济宁市'), (15, '370900', '泰安市'), (15, '371000', '威海市'), (15, '371100', '日照市'), (15, '371300', '临沂市'), (15, '371400', '德州市'), (15, '371500', '聊城市'), (15, '371600', '滨州市'), (15, '371700', '菏泽市'),
-- 16. 河南省
(16, '410100', '郑州市'), (16, '410200', '开封市'), (16, '410300', '洛阳市'), (16, '410400', '平顶山市'), (16, '410500', '安阳市'), (16, '410600', '鹤壁市'), (16, '410700', '新乡市'), (16, '410800', '焦作市'), (16, '410900', '濮阳市'), (16, '411000', '许昌市'), (16, '411100', '漯河市'), (16, '411200', '三门峡市'), (16, '411300', '南阳市'), (16, '411400', '商丘市'), (16, '411500', '信阳市'), (16, '411600', '周口市'), (16, '411700', '驻马店市'), (16, '419000', '省直辖县级行政区划'),
-- 17. 湖北省
(17, '420100', '武汉市'), (17, '420200', '黄石市'), (17, '420300', '十堰市'), (17, '420500', '宜昌市'), (17, '420600', '襄阳市'), (17, '420700', '鄂州市'), (17, '420800', '荆门市'), (17, '420900', '孝感市'), (17, '421000', '荆州市'), (17, '421100', '黄冈市'), (17, '421200', '咸宁市'), (17, '421300', '随州市'), (17, '422800', '恩施土家族苗族自治州'), (17, '429000', '省直辖县级行政区划'),
-- 18. 湖南省
(18, '430100', '长沙市'), (18, '430200', '株洲市'), (18, '430300', '湘潭市'), (18, '430400', '衡阳市'), (18, '430500', '邵阳市'), (18, '430600', '岳阳市'), (18, '430700', '常德市'), (18, '430800', '张家界市'), (18, '430900', '益阳市'), (18, '431000', '郴州市'), (18, '431100', '永州市'), (18, '431200', '怀化市'), (18, '431300', '娄底市'), (18, '433100', '湘西土家族苗族自治州'),
-- 19. 广东省
(19, '440100', '广州市'), (19, '440200', '韶关市'), (19, '440300', '深圳市'), (19, '440400', '珠海市'), (19, '440500', '汕头市'), (19, '440600', '佛山市'), (19, '440700', '江门市'), (19, '440800', '湛江市'), (19, '440900', '茂名市'), (19, '441200', '肇庆市'), (19, '441300', '惠州市'), (19, '441400', '梅州市'), (19, '441500', '汕尾市'), (19, '441600', '河源市'), (19, '441700', '阳江市'), (19, '441800', '清远市'), (19, '441900', '东莞市'), (19, '442000', '中山市'), (19, '445100', '潮州市'), (19, '445200', '揭阳市'), (19, '445300', '云浮市'),
-- 20. 广西壮族自治区
(20, '450100', '南宁市'), (20, '450200', '柳州市'), (20, '450300', '桂林市'), (20, '450400', '梧州市'), (20, '450500', '北海市'), (20, '450600', '防城港市'), (20, '450700', '钦州市'), (20, '450800', '贵港市'), (20, '450900', '玉林市'), (20, '451000', '百色市'), (20, '451100', '贺州市'), (20, '451200', '河池市'), (20, '451300', '来宾市'), (20, '451400', '崇左市'),
-- 21. 海南省
(21, '460100', '海口市'), (21, '460200', '三亚市'), (21, '460300', '三沙市'), (21, '460400', '儋州市'), (21, '469000', '省直辖县级行政区划'),
-- 22. 重庆市
(22, '500100', '重庆市'),
-- 23. 四川省
(23, '510100', '成都市'), (23, '510300', '自贡市'), (23, '510400', '攀枝花市'), (23, '510500', '泸州市'), (23, '510600', '德阳市'), (23, '510700', '绵阳市'), (23, '510800', '广元市'), (23, '510900', '遂宁市'), (23, '511000', '内江市'), (23, '511100', '乐山市'), (23, '511300', '南充市'), (23, '511400', '眉山市'), (23, '511500', '宜宾市'), (23, '511600', '广安市'), (23, '511700', '达州市'), (23, '511800', '雅安市'), (23, '511900', '巴中市'), (23, '512000', '资阳市'), (23, '513200', '阿坝藏族羌族自治州'), (23, '513300', '甘孜藏族自治州'), (23, '513400', '凉山彝族自治州'),
-- 24. 贵州省
(24, '520100', '贵阳市'), (24, '520200', '六盘水市'), (24, '520300', '遵义市'), (24, '520400', '安顺市'), (24, '520500', '毕节市'), (24, '520600', '铜仁市'), (24, '522300', '黔西南布依族苗族自治州'), (24, '522600', '黔东南苗族侗族自治州'), (24, '522700', '黔南布依族苗族自治州'),
-- 25. 云南省
(25, '530100', '昆明市'), (25, '530300', '曲靖市'), (25, '530400', '玉溪市'), (25, '530500', '保山市'), (25, '530600', '昭通市'), (25, '530700', '丽江市'), (25, '530800', '普洱市'), (25, '530900', '临沧市'), (25, '532300', '楚雄彝族自治州'), (25, '532500', '红河哈尼族彝族自治州'), (25, '532600', '文山壮族苗族自治州'), (25, '532800', '西双版纳傣族自治州'), (25, '532900', '大理白族自治州'), (25, '533100', '德宏傣族景颇族自治州'), (25, '533300', '怒江傈僳族自治州'), (25, '533400', '迪庆藏族自治州'),
-- 26. 西藏自治区
(26, '540100', '拉萨市'), (26, '540200', '日喀则市'), (26, '540300', '昌都市'), (26, '540400', '林芝市'), (26, '540500', '山南市'), (26, '540600', '那曲市'), (26, '542500', '阿里地区'),
-- 27. 陕西省
(27, '610100', '西安市'), (27, '610200', '铜川市'), (27, '610300', '宝鸡市'), (27, '610400', '咸阳市'), (27, '610500', '渭南市'), (27, '610600', '延安市'), (27, '610700', '汉中市'), (27, '610800', '榆林市'), (27, '610900', '安康市'), (27, '611000', '商洛市'),
-- 28. 甘肃省
(28, '620100', '兰州市'), (28, '620200', '嘉峪关市'), (28, '620300', '金昌市'), (28, '620400', '白银市'), (28, '620500', '天水市'), (28, '620600', '武威市'), (28, '620700', '张掖市'), (28, '620800', '平凉市'), (28, '620900', '酒泉市'), (28, '621000', '庆阳市'), (28, '621100', '定西市'), (28, '621200', '陇南市'), (28, '622900', '临夏回族自治州'), (28, '623000', '甘南藏族自治州'),
-- 29. 青海省
(29, '630100', '西宁市'), (29, '630200', '海东市'), (29, '632200', '海北藏族自治州'), (29, '632300', '黄南藏族自治州'), (29, '632500', '海南藏族自治州'), (29, '632600', '果洛藏族自治州'), (29, '632700', '玉树藏族自治州'), (29, '632800', '海西蒙古族藏族自治州'),
-- 30. 宁夏回族自治区
(30, '640100', '银川市'), (30, '640200', '石嘴山市'), (30, '640300', '吴忠市'), (30, '640400', '固原市'), (30, '640500', '中卫市'),
-- 31. 新疆维吾尔自治区
(31, '650100', '乌鲁木齐市'), (31, '650200', '克拉玛依市'), (31, '650400', '吐鲁番市'), (31, '650500', '哈密市'), (31, '652300', '昌吉回族自治州'), (31, '652700', '博尔塔拉蒙古自治州'), (31, '652800', '巴音郭楞蒙古自治州'), (31, '652900', '阿克苏地区'), (31, '653000', '克孜勒苏柯尔克孜自治州'), (31, '653100', '喀什地区'), (31, '653200', '和田地区'), (31, '654000', '伊犁哈萨克自治州'), (31, '654200', '塔城地区'), (31, '654300', '阿勒泰地区'), (31, '659000', '自治区直辖县级行政区划'),
-- 32. 台湾省
(32, '710000', '台湾省'),
-- 33. 香港特别行政区
(33, '810000', '香港特别行政区'),
-- 34. 澳门特别行政区
(34, '820000', '澳门特别行政区');

-- ----------------------------
-- 5. 修改 jobs 表结构
-- ----------------------------

-- 5.1 将原 location 字段重命名为 address_detail (保留旧数据作为详细地址)
ALTER TABLE jobs 
CHANGE COLUMN `location` `address_detail` VARCHAR(255) NULL COMMENT '详细地址';

-- 5.2 添加 province_id 和 city_id 字段
ALTER TABLE jobs
ADD COLUMN `province_id` INT NULL COMMENT '省份ID' AFTER `max_salary`,
ADD COLUMN `city_id` INT NULL COMMENT '城市ID' AFTER `province_id`;

-- 5.3 建立外键约束
ALTER TABLE jobs
ADD CONSTRAINT `fk_jobs_province` FOREIGN KEY (`province_id`) REFERENCES `t_provinces` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
ADD CONSTRAINT `fk_jobs_city` FOREIGN KEY (`city_id`) REFERENCES `t_cities` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

-- ========== V10__Add_Draft_Status.sql ==========
ALTER TABLE jobs
MODIFY COLUMN `status` INT NULL DEFAULT 10 COMMENT '岗位状态 1=draft, 10=pending, 20=approved, 30=rejected, 40=closed';

-- ========== V11__Add_Job_Tech_Req.sql ==========
ALTER TABLE jobs
ADD COLUMN `tech_requirements` TEXT NULL COMMENT '岗位要求' AFTER `description`;

-- ========== V12__Add_job_views_table.sql ==========
-- Version: 12
-- Description: Create job_views table for tracking job view records

CREATE TABLE job_views (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '浏览记录ID',
    job_id INT NOT NULL COMMENT '岗位ID，关联 jobs.id',
    viewer_user_id INT NULL COMMENT '浏览者用户ID（未登录可为空）',
    viewed_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '浏览时间',
    client_ip VARCHAR(64) NULL COMMENT '客户端IP',
    user_agent VARCHAR(255) NULL COMMENT '客户端UA信息',
    INDEX idx_job_views_job (job_id),
    INDEX idx_job_views_viewer (viewer_user_id),
    INDEX idx_job_views_viewed_at (viewed_at),
    CONSTRAINT fk_job_views_job FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE CASCADE,
    CONSTRAINT fk_job_views_viewer FOREIGN KEY (viewer_user_id) REFERENCES users(id) ON DELETE SET NULL
) COMMENT='岗位浏览记录表（一次浏览一条记录，不去重）';

-- ========== V13__Add_job_view_count_column.sql ==========
-- Version: 13
-- Description: Add view_count column to jobs table

ALTER TABLE jobs
    ADD COLUMN view_count INT NOT NULL DEFAULT 0 COMMENT '岗位浏览次数' AFTER headcount;

CREATE INDEX idx_jobs_view_count ON jobs(view_count);

-- ========== V14__Create_application_statuses_table.sql ==========
-- Flyway Migration Script
-- Version: 14
-- Description: 创建投递状态字典表 (application_statuses) 并初始化数据

-- ----------------------------
-- 1. 创建 application_statuses 表
-- ----------------------------
CREATE TABLE application_statuses (
    `id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '状态唯一ID',
    `code` INT NOT NULL UNIQUE COMMENT '状态代码 (对应 ApplicationStatus enum code)',
    `name` VARCHAR(50) NOT NULL COMMENT '状态名称 (如: 已投递, 面试邀请)',
    `detail` TEXT NOT NULL COMMENT '状态详情描述',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='投递状态字典表';

-- ----------------------------
-- 2. 插入初始数据
-- ----------------------------
INSERT INTO application_statuses (code, name, detail) VALUES 
(10, '已投递', '您的简历已成功投递至企业，请耐心等待企业审核，后续通知将通过平台或预留联系方式发送。'),
(20, '候选人', '企业已将您加入候选人名单。若后续岗位匹配，将会通过平台或联系方式继续与您沟通。'),
(30, '面试邀请', '您的简历已通过初筛，请留意平台及预留的联系方式，企业将向您发送具体的面试安排和通知。'),
(40, '通过', '恭喜您已通过本次招聘流程！企业将通过平台或您的联系方式与您进一步沟通入职相关事宜。'),
(50, '拒绝', '很遗憾，您的简历未能通过本次筛选。建议您继续关注其他岗位或优化简历后再次申请，祝您求职顺利。');


