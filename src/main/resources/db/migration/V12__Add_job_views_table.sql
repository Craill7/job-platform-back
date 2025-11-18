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

