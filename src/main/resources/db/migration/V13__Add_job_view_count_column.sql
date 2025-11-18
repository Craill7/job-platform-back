-- Version: 13
-- Description: Add view_count column to jobs table

ALTER TABLE jobs
    ADD COLUMN view_count INT NOT NULL DEFAULT 0 COMMENT '岗位浏览次数' AFTER headcount;

CREATE INDEX idx_jobs_view_count ON jobs(view_count);

