-- Flyway Migration Script
-- Version: 5
-- Description: Add lookup tables for company industry, nature, and scale, and link to companies table.

-- ----------------------------
-- 1. Table structure for t_industries (行业领域表)
-- ----------------------------
CREATE TABLE t_industries (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '行业唯一ID',
    name VARCHAR(100) NOT NULL UNIQUE COMMENT '行业名称'
) COMMENT='行业领域字典表';

-- ----------------------------
-- 2. Records for t_industries
-- ----------------------------
INSERT INTO t_industries (id, name) VALUES
(1, '互联网/电子商务'),
(2, '计算机软件'),
(3, 'IT服务/系统集成'),
(4, '通信/电信运营'),
(5, '电子/半导体/集成电路'),
(6, '硬件/智能设备'),
(7, '金融(银行/证券/保险)'),
(8, '房地产/建筑/物业'),
(9, '制造业/工业自动化'),
(10, '广告/传媒/公关'),
(11, '教育/培训/科研'),
(12, '医疗/制药/生物工程'),
(13, '专业服务(咨询/法律/会计)'),
(14, '快速消费品(FMCG)'),
(15, '零售/批发'),
(16, '交通/物流/仓储'),
(17, '能源/化工/环保'),
(18, '汽车及零部件'),
(19, '服务业(生活/娱乐/餐饮)'),
(20, '政府/非营利组织'),
(99, '其他');

-- ----------------------------
-- 3. Table structure for t_company_natures (企业性质表)
-- ----------------------------
CREATE TABLE t_company_natures (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '企业性质唯一ID',
    name VARCHAR(100) NOT NULL UNIQUE COMMENT '企业性质名称'
) COMMENT='企业性质字典表';

-- ----------------------------
-- 4. Records for t_company_natures
-- ----------------------------
INSERT INTO t_company_natures (id, name) VALUES
(1, '国有企业'),
(2, '集体企业'),
(3, '私营企业'),
(4, '联营企业'),
(5, '股份制企业'),
(6, '上市公司'),
(7, '中外合资企业'),
(8, '外商独资企业'),
(9, '港澳台商投资企业'),
(10, '事业单位'),
(11, '政府机关'),
(12, '非营利组织'),
(13, '个人独资企业'),
(14, '合伙企业'),
(99, '其他');

-- ----------------------------
-- 5. Table structure for t_company_scales (公司规模表)
-- ----------------------------
CREATE TABLE t_company_scales (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '公司规模唯一ID',
    scale VARCHAR(50) NOT NULL UNIQUE COMMENT '规模范围'
) COMMENT='公司规模字典表';

-- ----------------------------
-- 6. Records for t_company_scales
-- ----------------------------
INSERT INTO t_company_scales (id, scale) VALUES
(1, '0-20人'),
(2, '20-99人'),
(3, '100-499人'),
(4, '500-999人'),
(5, '1000-4999人'),
(6, '5000-9999人'),
(7, '10000人以上');

-- ----------------------------
-- 7. Update companies table with foreign key constraints
-- ----------------------------

-- 关联行业表
ALTER TABLE companies
ADD CONSTRAINT fk_companies_industry
FOREIGN KEY (industry_id) REFERENCES t_industries(id)
ON DELETE SET NULL ON UPDATE CASCADE;

-- 关联性质表
ALTER TABLE companies
ADD CONSTRAINT fk_companies_nature
FOREIGN KEY (nature_id) REFERENCES t_company_natures(id)
ON DELETE SET NULL ON UPDATE CASCADE;

-- 关联规模表
ALTER TABLE companies
ADD CONSTRAINT fk_companies_scale
FOREIGN KEY (company_scale_id) REFERENCES t_company_scales(id)
ON DELETE SET NULL ON UPDATE CASCADE;