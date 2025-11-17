ALTER TABLE jobs
ADD COLUMN `tech_requirements` TEXT NULL COMMENT '岗位要求' AFTER `description`;
