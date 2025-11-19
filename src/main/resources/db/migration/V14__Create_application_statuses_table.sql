-- Flyway Migration Script
-- Version: 14
-- Description: 创建投递状态字典表 (application_statuses) 并初始化数据

-- ----------------------------
-- 1. 创建 application_statuses 表
-- ----------------------------
CREATE TABLE application_statuses (
    `id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '状态唯一ID',
    `code` INT NOT NULL UNIQUE COMMENT '状态代码 (对应 ApplicationStatus enum code)',
    `name` VARCHAR(50) NOT NULL COMMENT '状态名称 (如: 已投递, 面试邀请)',
    `detail` TEXT NOT NULL COMMENT '状态详情描述',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='投递状态字典表';

-- ----------------------------
-- 2. 插入初始数据
-- ----------------------------
INSERT INTO application_statuses (code, name, detail) VALUES 
(10, '已投递', '您的简历已成功投递至企业，请耐心等待企业审核，后续通知将通过平台或预留联系方式发送。'),
(20, '候选人', '企业已将您加入候选人名单。若后续岗位匹配，将会通过平台或联系方式继续与您沟通。'),
(30, '面试邀请', '您的简历已通过初筛，请留意平台及预留的联系方式，企业将向您发送具体的面试安排和通知。'),
(40, '通过', '恭喜您已通过本次招聘流程！企业将通过平台或您的联系方式与您进一步沟通入职相关事宜。'),
(50, '拒绝', '很遗憾，您的简历未能通过本次筛选。建议您继续关注其他岗位或优化简历后再次申请，祝您求职顺利。');

