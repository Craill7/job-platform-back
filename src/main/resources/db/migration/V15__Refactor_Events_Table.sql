-- V15__Update_Events_Schema.sql

-- 1. 将 event_description 重命名为 event_summary
ALTER TABLE events CHANGE COLUMN `event_description` `event_summary` VARCHAR(500) NULL COMMENT '活动详情';

-- 2. 确保 event_type 是 VARCHAR(50)
ALTER TABLE events MODIFY COLUMN `event_type` VARCHAR(50) NULL COMMENT '类型';

-- 3. [重点修改] target_audience 改为 VARCHAR，内容自由，移除默认值
ALTER TABLE events MODIFY COLUMN `target_audience` VARCHAR(255) NULL COMMENT '面向对象';