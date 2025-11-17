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