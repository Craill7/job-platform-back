-- 删除 jobs.work_address，保留 address_detail 作为唯一详细地址字段
UPDATE jobs
SET address_detail = work_address
WHERE address_detail IS NULL AND work_address IS NOT NULL;

ALTER TABLE jobs
DROP COLUMN work_address;
