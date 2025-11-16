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
