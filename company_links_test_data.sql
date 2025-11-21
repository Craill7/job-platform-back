-- 为 company_links 表添加测试数据
-- 基于现有的公司数据生成

-- 公司1: 测试科技有限公司 (company_id = 1)
INSERT INTO company_links (company_id, link_name, link_url, created_at) VALUES
(1, '企业官网', 'https://www.test-company.com', NOW()),
(1, '招聘官网', 'https://careers.test-company.com', NOW()),
(1, '官方微博', 'https://weibo.com/testcompany', NOW()),
(1, '官方微信公众号', 'https://mp.weixin.qq.com/testcompany', NOW());

-- 公司2: 示例科技有限公司 (company_id = 2)
INSERT INTO company_links (company_id, link_name, link_url, created_at) VALUES
(2, '企业官网', 'https://www.sample-company.com', NOW()),
(2, '招聘官网', 'https://jobs.sample-company.com', NOW()),
(2, 'GitHub', 'https://github.com/sample-company', NOW()),
(2, '官方博客', 'https://blog.sample-company.com', NOW());

-- 公司4: 示例科技有限公司 (company_id = 4)
INSERT INTO company_links (company_id, link_name, link_url, created_at) VALUES
(4, '企业官网', 'https://www.example-tech.com', NOW()),
(4, '招聘官网', 'https://careers.example-tech.com', NOW()),
(4, 'LinkedIn', 'https://www.linkedin.com/company/example-tech', NOW()),
(4, '官方微博', 'https://weibo.com/exampletech', NOW()),
(4, '官方微信公众号', 'https://mp.weixin.qq.com/exampletech', NOW()),
(4, 'GitHub', 'https://github.com/example-tech', NOW());

