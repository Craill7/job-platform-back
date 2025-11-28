-- 1. 先删除外键约束
-- 注意：companies_ibfk_1 是你 job.sql 里定义的外键名，如果报错说不存在，去数据库里查一下
ALTER TABLE companies DROP FOREIGN KEY companies_ibfk_1;

-- 2. 删除那个“唯一索引”
ALTER TABLE companies DROP INDEX user_id;

-- 3. 修改字段允许为空
ALTER TABLE companies MODIFY COLUMN user_id int(11) NULL COMMENT '关联 users.id (管理员代录入可为空)';

