ALTER TABLE jobs
MODIFY COLUMN `status` INT NULL DEFAULT 10 COMMENT '岗位状态 1=draft, 10=pending, 20=approved, 30=rejected, 40=closed';