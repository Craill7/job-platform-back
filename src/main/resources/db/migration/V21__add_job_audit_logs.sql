DROP TABLE IF EXISTS `job_audit_logs`;
CREATE TABLE `job_audit_logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `job_id` int(11) NOT NULL COMMENT '关联 jobs.id',
  `operator_id` bigint(20) NOT NULL COMMENT '审核人ID (关联 sys_user.user_id)',
  `operator_contact` varchar(100) NULL DEFAULT NULL COMMENT '审核人联系方式 (手动输入，格式不限)',
  `audit_status` int(11) NOT NULL COMMENT '审核结果状态 (20=通过, 30=拒绝)',
  `remark` varchar(500) NULL DEFAULT NULL COMMENT '审核备注/拒绝原因',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审核时间',
  PRIMARY KEY (`id`),
  INDEX `idx_log_job_id`(`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位审核历史记录表';