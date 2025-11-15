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