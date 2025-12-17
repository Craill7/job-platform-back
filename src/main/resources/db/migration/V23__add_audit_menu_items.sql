-- Flyway migration script V23
-- Update audit menu items component field

UPDATE `sys_menu`
SET `component` = 'recruit/audit/companyAudit',
    `update_by` = 'admin',
    `update_time` = NOW()
WHERE `menu_id` = 2076;

UPDATE `sys_menu`
SET `component` = 'recruit/audit/jobAudit',
    `update_by` = 'admin',
    `update_time` = NOW()
WHERE `menu_id` = 2077;
