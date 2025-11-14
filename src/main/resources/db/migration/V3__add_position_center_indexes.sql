-- V3__add_position_center_indexes.sql
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

