-- V4__add_resume_center_indexes.sql
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

