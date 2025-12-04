
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `tpl_web_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '前端模板类型（element-ui模版 element-plus模版）',
  `package_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成业务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table
-- ----------------------------
INSERT INTO `gen_table` VALUES (1, 'jobs', '岗位信息表，草稿也存在该表中', NULL, NULL, 'Jobs', 'crud', 'element-plus', 'com.ruoyi.recruit', 'recruit', 'jobs', '岗位管理', 'Yihan', '0', '/', '{\"parentMenuId\":2000}', 'admin', '2025-12-01 20:22:35', '', '2025-12-01 20:49:37', NULL);
INSERT INTO `gen_table` VALUES (2, 'companies', '存储企业的基本信息', NULL, NULL, 'Companies', 'crud', 'element-plus', 'com.ruoyi.recruit', 'recruit', 'companies', '企业管理', 'Yihan', '0', '/', '{\"parentMenuId\":2001}', 'admin', '2025-12-03 08:52:18', '', '2025-12-03 08:56:53', NULL);
INSERT INTO `gen_table` VALUES (3, 'users', '用户主表，存储通用登录信息', NULL, NULL, 'Users', 'crud', 'element-plus', 'com.ruoyi.recruit', 'recruit', 'allAsers', '账号管理', 'Yihan', '0', '/', '{\"parentMenuId\":2001}', 'admin', '2025-12-03 09:19:10', '', '2025-12-03 10:48:45', NULL);
INSERT INTO `gen_table` VALUES (6, 't_industries', '行业领域字典表', NULL, NULL, 'TIndustries', 'crud', 'element-plus', 'com.ruoyi.recruit', 'recruit', 'industries', '行业信息管理', 'Yihan', '0', '/', '{\"parentMenuId\":2002}', 'admin', '2025-12-03 11:29:31', '', '2025-12-03 14:34:37', NULL);
INSERT INTO `gen_table` VALUES (7, 'events', '求职活动表（宣讲会、招聘会等）', NULL, NULL, 'Events', 'crud', 'element-plus', 'com.ruoyi.recruit', 'recruit', 'events', '求职活动管理', 'Yihan', '0', '/', '{\"parentMenuId\":2000}', 'admin', '2025-12-03 12:18:12', '', '2025-12-03 12:21:30', NULL);
INSERT INTO `gen_table` VALUES (8, 't_job_categories', '岗位职能类别字典表', NULL, NULL, 'TJobCategories', 'crud', 'element-plus', 'com.ruoyi.recruit', 'recruit', 'categories', '岗位类别管理', 'Yihan', '0', '/', '{\"parentMenuId\":2002}', 'admin', '2025-12-03 14:43:22', '', '2025-12-03 14:45:41', NULL);
INSERT INTO `gen_table` VALUES (9, 't_company_natures', '企业性质字典表', NULL, NULL, 'TCompanyNatures', 'crud', 'element-plus', 'com.ruoyi.recruit', 'recruit', 'natures', '企业性质管理', 'Yihan', '0', '/', '{\"parentMenuId\":2002}', 'admin', '2025-12-03 15:50:45', '', '2025-12-03 15:57:12', NULL);
INSERT INTO `gen_table` VALUES (10, 't_company_scales', '公司规模字典表', NULL, NULL, 'TCompanyScales', 'crud', 'element-plus', 'com.ruoyi.recruit', 'recruit', 'scales', '规模信息管理', 'Yihan', '0', '/', '{\"parentMenuId\":2002}', 'admin', '2025-12-03 15:50:46', '', '2025-12-03 15:51:55', NULL);
INSERT INTO `gen_table` VALUES (11, 'tag_categories', '标签分类字典表', NULL, NULL, 'TagCategories', 'crud', 'element-plus', 'com.ruoyi.recruit', 'recruit', 'tagCategories', '标签分类管理', 'Yihan', '0', '/', '{\"parentMenuId\":2002}', 'admin', '2025-12-03 15:50:46', '', '2025-12-03 15:53:54', NULL);
INSERT INTO `gen_table` VALUES (12, 'tags', '由管理员维护的标签库', NULL, NULL, 'Tags', 'crud', 'element-plus', 'com.ruoyi.recruit', 'recruit', 'tags', '标签库', 'Yihan', '0', '/', '{\"parentMenuId\":2002}', 'admin', '2025-12-03 15:50:46', '', '2025-12-03 16:07:58', NULL);
INSERT INTO `gen_table` VALUES (13, 'applications', '核心关联表，记录学生、岗位和简历的投递关系', NULL, NULL, 'Applications', 'crud', 'element-plus', 'com.ruoyi.recruit', 'recruit', 'applications', '投递记录', 'Yihan', '0', '/', '{\"parentMenuId\":2000}', 'admin', '2025-12-04 08:45:09', '', '2025-12-04 08:57:50', NULL);
INSERT INTO `gen_table` VALUES (14, 'students', '学生信息表，与 users 表一对一关联', NULL, NULL, 'Students', 'crud', 'element-plus', 'com.ruoyi.recruit', 'recruit', 'students', '学生信息管理', 'Yihan', '0', '/', '{\"parentMenuId\":2001}', 'admin', '2025-12-04 09:06:23', '', '2025-12-04 09:19:43', NULL);

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint(20) NULL DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 114 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成业务表字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------
INSERT INTO `gen_table_column` VALUES (1, 1, 'id', '岗位ID', 'int(11)', 'Long', 'id', '1', '1', '0', '0', NULL, '1', NULL, 'EQ', 'input', '', 1, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (2, 1, 'company_id', '公司名称', 'int(11)', 'Long', 'companyId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (3, 1, 'posted_by_user_id', '关联users.id', 'int(11)', 'Long', 'postedByUserId', '0', '0', '0', '0', '0', '0', '0', 'EQ', 'input', '', 23, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (4, 1, 'title', '岗位名称', 'varchar(255)', 'String', 'title', '0', '0', '0', '1', '1', '1', '1', 'LIKE', 'input', '', 4, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (5, 1, 'description', '岗位描述', 'text', 'String', 'description', '0', '0', '0', '1', '1', '0', '0', 'EQ', 'textarea', '', 6, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (6, 1, 'tech_requirements', '岗位要求', 'text', 'String', 'techRequirements', '0', '0', '0', '1', '1', '0', '0', 'EQ', 'textarea', '', 7, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (7, 1, 'min_salary', '最少薪资(k)', 'int(11)', 'Long', 'minSalary', '0', '0', '0', '1', '1', '1', '0', 'EQ', 'input', '', 9, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (8, 1, 'max_salary', '最多薪资(k)', 'int(11)', 'Long', 'maxSalary', '0', '0', '0', '1', '1', '1', '0', 'EQ', 'input', '', 10, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (9, 1, 'province_id', '省份', 'int(11)', 'Long', 'provinceId', '0', '0', '0', '1', '1', '0', '0', 'EQ', 'input', '', 11, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (10, 1, 'city_id', '城市', 'int(11)', 'Long', 'cityId', '0', '0', '0', '1', '1', '0', '0', 'EQ', 'input', '', 12, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (11, 1, 'address_detail', '详细地址', 'varchar(255)', 'String', 'addressDetail', '0', '0', '0', '1', '1', '0', '0', 'EQ', 'input', '', 13, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (12, 1, 'work_nature', '岗位性质', 'int(11)', 'Long', 'workNature', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'select', 'biz_work_nature', 14, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (13, 1, 'deadline', '招聘截止日期', 'date', 'Date', 'deadline', '0', '0', '0', '1', '1', '0', '0', 'BETWEEN', 'datetime', '', 18, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (14, 1, 'status', '岗位状态', 'int(11)', 'Long', 'status', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'select', 'biz_job_status', 21, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (15, 1, 'created_at', '创建时间', 'datetime', 'Date', 'createdAt', '0', '0', '0', '0', '0', '0', '1', 'BETWEEN', 'datetime', '', 19, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (16, 1, 'updated_at', '更新时间', 'datetime', 'Date', 'updatedAt', '0', '0', '0', '0', '0', '0', '0', 'BETWEEN', 'datetime', '', 20, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (17, 1, 'type', '岗位类别', 'int(11)', 'Long', 'type', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'select', '', 5, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (18, 1, 'department', '所属部门', 'varchar(100)', 'String', 'department', '0', '0', '0', '1', '1', '1', '0', 'EQ', 'input', '', 3, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (19, 1, 'headcount', '招聘人数', 'int(11)', 'Long', 'headcount', '0', '0', '0', '1', '1', '1', '0', 'EQ', 'input', '', 16, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (20, 1, 'view_count', '岗位浏览次数', 'int(11)', 'Long', 'viewCount', '0', '0', '1', '1', '1', '1', '0', 'EQ', 'input', '', 22, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (21, 1, 'required_degree', '学历要求', 'int(11)', 'Long', 'requiredDegree', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'select', 'biz_required_degree_level', 15, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (22, 1, 'required_start_date', '要求入职时间', 'date', 'Date', 'requiredStartDate', '0', '0', '0', '1', '1', '0', '0', 'BETWEEN', 'datetime', '', 17, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (23, 1, 'bonus_points', '岗位加分项', 'text', 'String', 'bonusPoints', '0', '0', '0', '1', '1', '0', '0', 'EQ', 'textarea', '', 8, 'admin', '2025-12-01 20:22:36', '', '2025-12-01 20:49:37');
INSERT INTO `gen_table_column` VALUES (24, 2, 'company_id', '公司ID', 'int(11)', 'Long', 'companyId', '1', '1', '0', '0', NULL, '1', NULL, 'EQ', 'input', '', 1, 'admin', '2025-12-03 08:52:18', '', '2025-12-03 08:56:53');
INSERT INTO `gen_table_column` VALUES (25, 2, 'user_id', '关联用户ID', 'int(11)', 'Long', 'userId', '0', '0', '0', '1', '1', '0', '0', 'EQ', 'input', '', 2, 'admin', '2025-12-03 08:52:18', '', '2025-12-03 08:56:53');
INSERT INTO `gen_table_column` VALUES (26, 2, 'company_name', '公司名称', 'varchar(255)', 'String', 'companyName', '0', '0', '0', '1', '1', '1', '1', 'LIKE', 'input', '', 3, 'admin', '2025-12-03 08:52:18', '', '2025-12-03 08:56:53');
INSERT INTO `gen_table_column` VALUES (27, 2, 'description', '公司介绍', 'text', 'String', 'description', '0', '0', '0', '1', '1', '0', '0', 'EQ', 'textarea', '', 4, 'admin', '2025-12-03 08:52:18', '', '2025-12-03 08:56:53');
INSERT INTO `gen_table_column` VALUES (28, 2, 'logo_url', '公司Logo', 'varchar(255)', 'String', 'logoUrl', '0', '0', '0', '1', '1', '1', '0', 'EQ', 'imageUpload', '', 5, 'admin', '2025-12-03 08:52:18', '', '2025-12-03 08:56:53');
INSERT INTO `gen_table_column` VALUES (29, 2, 'industry_id', '行业领域', 'int(11)', 'Long', 'industryId', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'select', '', 6, 'admin', '2025-12-03 08:52:18', '', '2025-12-03 08:56:53');
INSERT INTO `gen_table_column` VALUES (30, 2, 'nature_id', '企业性质', 'int(11)', 'Long', 'natureId', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'select', '', 7, 'admin', '2025-12-03 08:52:18', '', '2025-12-03 08:56:53');
INSERT INTO `gen_table_column` VALUES (31, 2, 'company_scale_id', '公司规模', 'int(11)', 'Long', 'companyScaleId', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'select', '', 8, 'admin', '2025-12-03 08:52:18', '', '2025-12-03 08:56:53');
INSERT INTO `gen_table_column` VALUES (32, 2, 'company_address', '公司地址', 'varchar(255)', 'String', 'companyAddress', '0', '0', '0', '1', '1', '0', '0', 'EQ', 'input', '', 9, 'admin', '2025-12-03 08:52:18', '', '2025-12-03 08:56:53');
INSERT INTO `gen_table_column` VALUES (33, 2, 'contact_person_name', '联系人姓名', 'varchar(100)', 'String', 'contactPersonName', '0', '0', '0', '1', '1', '1', '0', 'LIKE', 'input', '', 10, 'admin', '2025-12-03 08:52:18', '', '2025-12-03 08:56:53');
INSERT INTO `gen_table_column` VALUES (34, 2, 'contact_person_phone', '联系人电话', 'varchar(50)', 'String', 'contactPersonPhone', '0', '0', '0', '1', '1', '1', '0', 'EQ', 'input', '', 11, 'admin', '2025-12-03 08:52:18', '', '2025-12-03 08:56:53');
INSERT INTO `gen_table_column` VALUES (35, 2, 'created_at', '创建时间', 'datetime', 'Date', 'createdAt', '0', '0', '0', '0', '0', '1', '1', 'BETWEEN', 'datetime', '', 12, 'admin', '2025-12-03 08:52:18', '', '2025-12-03 08:56:53');
INSERT INTO `gen_table_column` VALUES (36, 3, 'id', '用户ID', 'int(11)', 'Long', 'id', '1', '1', '0', '0', NULL, '1', '1', 'EQ', 'input', '', 1, 'admin', '2025-12-03 09:19:10', '', '2025-12-03 10:48:45');
INSERT INTO `gen_table_column` VALUES (37, 3, 'email', '用户邮箱', 'varchar(255)', 'String', 'email', '0', '0', '0', '1', '1', '1', '1', 'LIKE', 'input', '', 2, 'admin', '2025-12-03 09:19:10', '', '2025-12-03 10:48:45');
INSERT INTO `gen_table_column` VALUES (38, 3, 'password_hash', '加密后的密码', 'varchar(255)', 'String', 'passwordHash', '0', '0', '0', '0', '0', '0', '0', 'EQ', 'input', '', 3, 'admin', '2025-12-03 09:19:10', '', '2025-12-03 10:48:45');
INSERT INTO `gen_table_column` VALUES (39, 3, 'role', '用户角色', 'int(11)', 'Long', 'role', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'select', 'biz_user_role', 4, 'admin', '2025-12-03 09:19:10', '', '2025-12-03 10:48:45');
INSERT INTO `gen_table_column` VALUES (40, 3, 'status', '账户状态', 'int(11)', 'Long', 'status', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'select', 'biz_user_status', 5, 'admin', '2025-12-03 09:19:10', '', '2025-12-03 10:48:45');
INSERT INTO `gen_table_column` VALUES (41, 3, 'created_at', '创建时间', 'datetime', 'Date', 'createdAt', '0', '0', '0', '0', '0', '1', '1', 'BETWEEN', 'datetime', '', 6, 'admin', '2025-12-03 09:19:10', '', '2025-12-03 10:48:45');
INSERT INTO `gen_table_column` VALUES (42, 3, 'updated_at', '修改时间', 'datetime', 'Date', 'updatedAt', '0', '0', '0', '0', '0', '1', '0', 'EQ', 'datetime', '', 7, 'admin', '2025-12-03 09:19:10', '', '2025-12-03 10:48:45');
INSERT INTO `gen_table_column` VALUES (43, 3, 'last_login_at', '最近登录时间', 'datetime', 'Date', 'lastLoginAt', '0', '0', '0', '0', '0', '1', '0', 'EQ', 'datetime', '', 8, 'admin', '2025-12-03 09:19:10', '', '2025-12-03 10:48:45');
INSERT INTO `gen_table_column` VALUES (65, 6, 'id', '行业ID', 'int(11)', 'Long', 'id', '1', '1', '0', '0', NULL, '1', '1', 'EQ', 'input', '', 1, 'admin', '2025-12-03 11:29:32', '', '2025-12-03 14:34:37');
INSERT INTO `gen_table_column` VALUES (66, 6, 'name', '行业名称', 'varchar(100)', 'String', 'name', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 2, 'admin', '2025-12-03 11:29:32', '', '2025-12-03 14:34:37');
INSERT INTO `gen_table_column` VALUES (67, 7, 'id', '活动ID', 'bigint(20)', 'Long', 'id', '1', '1', '0', '0', NULL, '1', NULL, 'EQ', 'input', '', 1, 'admin', '2025-12-03 12:18:12', '', '2025-12-03 12:21:30');
INSERT INTO `gen_table_column` VALUES (68, 7, 'admin_user_id', '发布管理员ID', 'int(11)', 'Long', 'adminUserId', '0', '0', '0', '0', '0', '0', '0', 'EQ', 'input', '', 2, 'admin', '2025-12-03 12:18:12', '', '2025-12-03 12:21:30');
INSERT INTO `gen_table_column` VALUES (69, 7, 'event_title', '活动标题', 'varchar(255)', 'String', 'eventTitle', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 3, 'admin', '2025-12-03 12:18:12', '', '2025-12-03 12:21:30');
INSERT INTO `gen_table_column` VALUES (70, 7, 'event_summary', '活动详情', 'text', 'String', 'eventSummary', '0', '0', '0', '1', '1', '0', '0', 'EQ', 'editor', '', 4, 'admin', '2025-12-03 12:18:12', '', '2025-12-03 12:21:30');
INSERT INTO `gen_table_column` VALUES (71, 7, 'event_start_time', '活动开始时间', 'datetime', 'Date', 'eventStartTime', '0', '0', '1', '1', '1', '1', '1', 'BETWEEN', 'datetime', '', 5, 'admin', '2025-12-03 12:18:12', '', '2025-12-03 12:21:30');
INSERT INTO `gen_table_column` VALUES (72, 7, 'event_end_time', '活动结束时间', 'datetime', 'Date', 'eventEndTime', '0', '0', '0', '1', '1', '1', '1', 'BETWEEN', 'datetime', '', 6, 'admin', '2025-12-03 12:18:12', '', '2025-12-03 12:21:30');
INSERT INTO `gen_table_column` VALUES (73, 7, 'event_location', '活动地点', 'varchar(255)', 'String', 'eventLocation', '0', '0', '0', '1', '1', '1', '0', 'EQ', 'input', '', 7, 'admin', '2025-12-03 12:18:12', '', '2025-12-03 12:21:30');
INSERT INTO `gen_table_column` VALUES (74, 7, 'target_audience', '面向对象', 'varchar(255)', 'String', 'targetAudience', '0', '0', '0', '1', '1', '1', '0', 'EQ', 'input', '', 8, 'admin', '2025-12-03 12:18:12', '', '2025-12-03 12:21:30');
INSERT INTO `gen_table_column` VALUES (75, 7, 'created_at', '创建时间', 'datetime', 'Date', 'createdAt', '0', '0', '1', '0', '0', '1', '1', 'BETWEEN', 'datetime', '', 9, 'admin', '2025-12-03 12:18:12', '', '2025-12-03 12:21:30');
INSERT INTO `gen_table_column` VALUES (76, 7, 'updated_at', '更新时间', 'datetime', 'Date', 'updatedAt', '0', '0', '1', '0', '0', '1', '0', 'EQ', 'datetime', '', 10, 'admin', '2025-12-03 12:18:13', '', '2025-12-03 12:21:30');
INSERT INTO `gen_table_column` VALUES (77, 8, 'id', '岗位类别ID', 'int(11)', 'Long', 'id', '1', '1', '0', '0', NULL, NULL, '1', 'EQ', 'input', '', 1, 'admin', '2025-12-03 14:43:22', '', '2025-12-03 14:45:41');
INSERT INTO `gen_table_column` VALUES (78, 8, 'name', '岗位类别名称', 'varchar(50)', 'String', 'name', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 2, 'admin', '2025-12-03 14:43:22', '', '2025-12-03 14:45:41');
INSERT INTO `gen_table_column` VALUES (79, 9, 'id', '企业性质ID', 'int(11)', 'Long', 'id', '1', '1', '0', '0', NULL, '1', '1', 'EQ', 'input', '', 1, 'admin', '2025-12-03 15:50:46', '', '2025-12-03 15:57:12');
INSERT INTO `gen_table_column` VALUES (80, 9, 'name', '企业性质名称', 'varchar(100)', 'String', 'name', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 2, 'admin', '2025-12-03 15:50:46', '', '2025-12-03 15:57:12');
INSERT INTO `gen_table_column` VALUES (81, 10, 'id', '公司规模', 'int(11)', 'Long', 'id', '1', '1', '0', '0', NULL, '1', '1', 'EQ', 'input', '', 1, 'admin', '2025-12-03 15:50:46', '', '2025-12-03 15:51:55');
INSERT INTO `gen_table_column` VALUES (82, 10, 'scale', '规模范围', 'varchar(50)', 'String', 'scale', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 2, 'admin', '2025-12-03 15:50:46', '', '2025-12-03 15:51:55');
INSERT INTO `gen_table_column` VALUES (83, 11, 'id', '分类ID', 'int(11)', 'Long', 'id', '1', '1', '0', '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2025-12-03 15:50:46', '', '2025-12-03 15:53:54');
INSERT INTO `gen_table_column` VALUES (84, 11, 'code', '分类短码', 'varchar(50)', 'String', 'code', '0', '0', '1', '1', '1', '1', '0', 'EQ', 'input', '', 2, 'admin', '2025-12-03 15:50:46', '', '2025-12-03 15:53:54');
INSERT INTO `gen_table_column` VALUES (85, 11, 'name', '分类名称', 'varchar(100)', 'String', 'name', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 3, 'admin', '2025-12-03 15:50:46', '', '2025-12-03 15:53:55');
INSERT INTO `gen_table_column` VALUES (86, 11, 'description', '分类描述', 'varchar(255)', 'String', 'description', '0', '0', '0', '1', '1', '0', '0', 'EQ', 'input', '', 4, 'admin', '2025-12-03 15:50:46', '', '2025-12-03 15:53:55');
INSERT INTO `gen_table_column` VALUES (87, 11, 'created_at', '创建时间', 'datetime', 'Date', 'createdAt', '0', '0', '1', '0', '0', '1', '1', 'BETWEEN', 'datetime', '', 5, 'admin', '2025-12-03 15:50:46', '', '2025-12-03 15:53:55');
INSERT INTO `gen_table_column` VALUES (88, 11, 'updated_at', '更新时间', 'datetime', 'Date', 'updatedAt', '0', '0', '1', '0', '0', '1', '1', 'BETWEEN', 'datetime', '', 6, 'admin', '2025-12-03 15:50:46', '', '2025-12-03 15:53:55');
INSERT INTO `gen_table_column` VALUES (89, 12, 'id', '标签ID', 'int(11)', 'Long', 'id', '1', '1', '0', '0', NULL, '1', NULL, 'EQ', 'input', '', 1, 'admin', '2025-12-03 15:50:46', '', '2025-12-03 16:07:58');
INSERT INTO `gen_table_column` VALUES (90, 12, 'name', '标签名称', 'varchar(100)', 'String', 'name', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 2, 'admin', '2025-12-03 15:50:46', '', '2025-12-03 16:07:58');
INSERT INTO `gen_table_column` VALUES (91, 12, 'created_at', '创建时间', 'datetime', 'Date', 'createdAt', '0', '0', '1', '0', '0', '1', '1', 'BETWEEN', 'datetime', '', 5, 'admin', '2025-12-03 15:50:46', '', '2025-12-03 16:07:58');
INSERT INTO `gen_table_column` VALUES (92, 12, 'created_by', '创建人ID', 'int(11)', 'Long', 'createdBy', '0', '0', '0', '0', '0', '1', '0', 'EQ', 'input', '', 4, 'admin', '2025-12-03 15:50:46', '', '2025-12-03 16:07:58');
INSERT INTO `gen_table_column` VALUES (93, 12, 'category_id', '所属分类', 'int(11)', 'Long', 'categoryId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2025-12-03 15:50:46', '', '2025-12-03 16:07:58');
INSERT INTO `gen_table_column` VALUES (94, 13, 'id', '投递记录ID', 'int(11)', 'Long', 'id', '1', '1', '0', '0', NULL, '1', NULL, 'EQ', 'input', '', 1, 'admin', '2025-12-04 08:45:09', '', '2025-12-04 08:57:50');
INSERT INTO `gen_table_column` VALUES (95, 13, 'job_id', '投递职位', 'int(11)', 'Long', 'jobId', '0', '0', '1', '0', '0', '1', '1', 'EQ', 'input', '', 2, 'admin', '2025-12-04 08:45:09', '', '2025-12-04 08:57:50');
INSERT INTO `gen_table_column` VALUES (96, 13, 'student_user_id', '投递人', 'int(11)', 'Long', 'studentUserId', '0', '0', '1', '0', '0', '1', '0', 'EQ', 'input', '', 3, 'admin', '2025-12-04 08:45:09', '', '2025-12-04 08:57:50');
INSERT INTO `gen_table_column` VALUES (97, 13, 'resume_id', '简历', 'bigint(20)', 'Long', 'resumeId', '0', '0', '1', '0', '0', '0', '0', 'EQ', 'input', '', 4, 'admin', '2025-12-04 08:45:09', '', '2025-12-04 08:57:50');
INSERT INTO `gen_table_column` VALUES (98, 13, 'status', '投递状态', 'int(11)', 'Long', 'status', '0', '0', '1', '0', '0', '1', '1', 'EQ', 'select', 'biz_application_status', 5, 'admin', '2025-12-04 08:45:09', '', '2025-12-04 08:57:50');
INSERT INTO `gen_table_column` VALUES (99, 13, 'submitted_at', '投递时间', 'datetime', 'Date', 'submittedAt', '0', '0', '1', '0', '0', '1', '1', 'BETWEEN', 'datetime', '', 6, 'admin', '2025-12-04 08:45:09', '', '2025-12-04 08:57:50');
INSERT INTO `gen_table_column` VALUES (100, 13, 'updated_at', '状态更新时间', 'datetime', 'Date', 'updatedAt', '0', '0', '1', '0', '0', '1', '1', 'BETWEEN', 'datetime', '', 7, 'admin', '2025-12-04 08:45:09', '', '2025-12-04 08:57:50');
INSERT INTO `gen_table_column` VALUES (101, 14, 'user_id', '用户ID', 'int(11)', 'Long', 'userId', '1', '0', '0', '0', NULL, '1', '0', 'EQ', 'input', '', 1, 'admin', '2025-12-04 09:06:23', '', '2025-12-04 09:19:43');
INSERT INTO `gen_table_column` VALUES (102, 14, 'student_id', '学号', 'varchar(50)', 'String', 'studentId', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', '', 5, 'admin', '2025-12-04 09:06:23', '', '2025-12-04 09:19:43');
INSERT INTO `gen_table_column` VALUES (103, 14, 'avatar_url', '头像', 'varchar(1024)', 'String', 'avatarUrl', '0', '0', '0', '1', '1', '1', '0', 'EQ', 'imageUpload', '', 2, 'admin', '2025-12-04 09:06:23', '', '2025-12-04 09:19:43');
INSERT INTO `gen_table_column` VALUES (104, 14, 'full_name', '姓名', 'varchar(100)', 'String', 'fullName', '0', '0', '0', '1', '1', '1', '1', 'LIKE', 'input', '', 3, 'admin', '2025-12-04 09:06:23', '', '2025-12-04 09:19:43');
INSERT INTO `gen_table_column` VALUES (105, 14, 'phone_number', '电话', 'varchar(20)', 'String', 'phoneNumber', '0', '0', '0', '1', '1', '1', '0', 'EQ', 'input', '', 6, 'admin', '2025-12-04 09:06:23', '', '2025-12-04 09:19:43');
INSERT INTO `gen_table_column` VALUES (106, 14, 'gender', '性别', 'int(11)', 'Long', 'gender', '0', '0', '0', '1', '1', '1', '0', 'EQ', 'select', 'biz_gender', 4, 'admin', '2025-12-04 09:06:23', '', '2025-12-04 09:19:43');
INSERT INTO `gen_table_column` VALUES (107, 14, 'date_of_birth', '生日', 'date', 'Date', 'dateOfBirth', '0', '0', '0', '1', '1', '0', '0', 'EQ', 'datetime', '', 7, 'admin', '2025-12-04 09:06:23', '', '2025-12-04 09:19:43');
INSERT INTO `gen_table_column` VALUES (108, 14, 'job_seeking_status', '求职状态', 'int(11)', 'Long', 'jobSeekingStatus', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'select', 'biz_seek_status', 8, 'admin', '2025-12-04 09:06:23', '', '2025-12-04 09:19:43');
INSERT INTO `gen_table_column` VALUES (109, 14, 'expected_position', '期望职位', 'varchar(100)', 'String', 'expectedPosition', '0', '0', '0', '1', '1', '1', '0', 'EQ', 'input', '', 9, 'admin', '2025-12-04 09:06:23', '', '2025-12-04 09:19:43');
INSERT INTO `gen_table_column` VALUES (110, 14, 'expected_min_salary', '期望最少薪资', 'int(11)', 'Long', 'expectedMinSalary', '0', '0', '0', '1', '1', '0', '0', 'EQ', 'input', '', 10, 'admin', '2025-12-04 09:06:23', '', '2025-12-04 09:19:43');
INSERT INTO `gen_table_column` VALUES (111, 14, 'expected_max_salary', '期望最多薪资', 'int(11)', 'Long', 'expectedMaxSalary', '0', '0', '0', '1', '1', '0', '0', 'EQ', 'input', '', 11, 'admin', '2025-12-04 09:06:23', '', '2025-12-04 09:19:43');
INSERT INTO `gen_table_column` VALUES (112, 14, 'skills_summary', '技能掌握', 'text', 'String', 'skillsSummary', '0', '0', '0', '1', '1', '0', '0', 'EQ', 'textarea', '', 12, 'admin', '2025-12-04 09:06:23', '', '2025-12-04 09:19:43');
INSERT INTO `gen_table_column` VALUES (113, 14, 'current_template_id', '当前选用的模板ID', 'bigint(20)', 'Long', 'currentTemplateId', '0', '0', '0', '0', '0', '0', '0', 'EQ', 'input', '', 13, 'admin', '2025-12-04 09:06:23', '', '2025-12-04 09:19:43');

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob NULL COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Blob类型的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`, `calendar_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '日历信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Cron类型的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint(13) NOT NULL COMMENT '触发的时间',
  `sched_time` bigint(13) NOT NULL COMMENT '定时器制定的时间',
  `priority` int(11) NOT NULL COMMENT '优先级',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否接受恢复执行',
  PRIMARY KEY (`sched_name`, `entry_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '已触发的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务组名',
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob NULL COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '任务详细信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`, `lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '存储的悲观锁信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`, `trigger_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '暂停的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint(13) NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint(13) NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`, `instance_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '调度器状态表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint(7) NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint(12) NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint(10) NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '简单触发器的信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int(11) NULL DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int(11) NULL DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint(20) NULL DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint(20) NULL DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '同步机制的行锁表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint(13) NULL DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint(13) NULL DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int(11) NULL DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '触发器的类型',
  `start_time` bigint(13) NOT NULL COMMENT '开始时间',
  `end_time` bigint(13) NULL DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint(2) NULL DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob NULL COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  INDEX `sched_name`(`sched_name` ASC, `job_name` ASC, `job_group` ASC) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '触发器详细信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2025-12-01 19:33:15', '', NULL, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2025-12-01 19:33:15', '', NULL, '初始化密码 123456');
INSERT INTO `sys_config` VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2025-12-01 19:33:15', '', NULL, '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `sys_config` VALUES (4, '账号自助-验证码开关', 'sys.account.captchaEnabled', 'true', 'Y', 'admin', '2025-12-01 19:33:15', '', NULL, '是否开启验证码功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (5, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 'Y', 'admin', '2025-12-01 19:33:15', '', NULL, '是否开启注册用户功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (6, '用户登录-黑名单列表', 'sys.login.blackIPList', '', 'Y', 'admin', '2025-12-01 19:33:15', '', NULL, '设置登录IP黑名单限制，多个匹配项以;分隔，支持匹配（*通配、网段）');
INSERT INTO `sys_config` VALUES (7, '用户管理-初始密码修改策略', 'sys.account.initPasswordModify', '1', 'Y', 'admin', '2025-12-01 19:33:15', '', NULL, '0：初始密码修改策略关闭，没有任何提示，1：提醒用户，如果未修改初始密码，则在登录时就会提醒修改密码对话框');
INSERT INTO `sys_config` VALUES (8, '用户管理-账号密码更新周期', 'sys.account.passwordValidateDays', '0', 'Y', 'admin', '2025-12-01 19:33:15', '', NULL, '密码更新周期（填写数字，数据初始化值为0不限制，若修改必须为大于0小于365的正整数），如果超过这个周期登录系统时，则在登录时就会提醒修改密码对话框');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 200 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 0, '0', '若依科技', 0, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-12-01 19:33:15', '', NULL);
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', '深圳总公司', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-12-01 19:33:15', '', NULL);
INSERT INTO `sys_dept` VALUES (102, 100, '0,100', '长沙分公司', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-12-01 19:33:15', '', NULL);
INSERT INTO `sys_dept` VALUES (103, 101, '0,100,101', '研发部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-12-01 19:33:15', '', NULL);
INSERT INTO `sys_dept` VALUES (104, 101, '0,100,101', '市场部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-12-01 19:33:15', '', NULL);
INSERT INTO `sys_dept` VALUES (105, 101, '0,100,101', '测试部门', 3, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-12-01 19:33:15', '', NULL);
INSERT INTO `sys_dept` VALUES (106, 101, '0,100,101', '财务部门', 4, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-12-01 19:33:15', '', NULL);
INSERT INTO `sys_dept` VALUES (107, 101, '0,100,101', '运维部门', 5, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-12-01 19:33:15', '', NULL);
INSERT INTO `sys_dept` VALUES (108, 102, '0,100,102', '市场部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-12-01 19:33:15', '', NULL);
INSERT INTO `sys_dept` VALUES (109, 102, '0,100,102', '财务部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-12-01 19:33:15', '', NULL);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 129 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '性别未知');
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '显示菜单');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '默认分组');
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '系统分组');
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '系统默认是');
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '系统默认否');
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '通知');
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '公告');
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '关闭状态');
INSERT INTO `sys_dict_data` VALUES (18, 99, '其他', '0', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '其他操作');
INSERT INTO `sys_dict_data` VALUES (19, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '新增操作');
INSERT INTO `sys_dict_data` VALUES (20, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '修改操作');
INSERT INTO `sys_dict_data` VALUES (21, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '删除操作');
INSERT INTO `sys_dict_data` VALUES (22, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '授权操作');
INSERT INTO `sys_dict_data` VALUES (23, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '导出操作');
INSERT INTO `sys_dict_data` VALUES (24, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '导入操作');
INSERT INTO `sys_dict_data` VALUES (25, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '强退操作');
INSERT INTO `sys_dict_data` VALUES (26, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '生成操作');
INSERT INTO `sys_dict_data` VALUES (27, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '清空操作');
INSERT INTO `sys_dict_data` VALUES (28, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (29, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (100, 0, '学生', '1', 'biz_user_role', NULL, 'primary', 'N', '0', 'admin', '2025-12-01 19:50:13', 'admin', '2025-12-01 19:56:57', NULL);
INSERT INTO `sys_dict_data` VALUES (101, 1, '企业HR', '2', 'biz_user_role', NULL, 'warning', 'N', '0', 'admin', '2025-12-01 19:50:22', 'admin', '2025-12-01 19:57:08', NULL);
INSERT INTO `sys_dict_data` VALUES (102, 0, '待审核', '0', 'biz_user_status', NULL, 'warning', 'N', '0', 'admin', '2025-12-01 19:50:47', 'admin', '2025-12-01 19:56:33', NULL);
INSERT INTO `sys_dict_data` VALUES (103, 1, '正常', '1', 'biz_user_status', NULL, 'success', 'N', '0', 'admin', '2025-12-01 19:50:53', 'admin', '2025-12-01 19:56:39', NULL);
INSERT INTO `sys_dict_data` VALUES (104, 2, '封禁', '2', 'biz_user_status', NULL, 'danger', 'N', '0', 'admin', '2025-12-01 19:51:01', 'admin', '2025-12-01 19:56:48', NULL);
INSERT INTO `sys_dict_data` VALUES (105, 0, '草稿', '1', 'biz_job_status', NULL, 'info', 'N', '0', 'admin', '2025-12-01 19:51:24', 'admin', '2025-12-01 19:57:20', NULL);
INSERT INTO `sys_dict_data` VALUES (106, 1, '待审核', '10', 'biz_job_status', NULL, 'warning', 'N', '0', 'admin', '2025-12-01 19:51:32', 'admin', '2025-12-01 19:57:26', NULL);
INSERT INTO `sys_dict_data` VALUES (107, 2, '已发布', '20', 'biz_job_status', NULL, 'success', 'N', '0', 'admin', '2025-12-01 19:51:42', 'admin', '2025-12-01 19:57:30', NULL);
INSERT INTO `sys_dict_data` VALUES (108, 3, '已驳回', '30', 'biz_job_status', NULL, 'danger', 'N', '0', 'admin', '2025-12-01 19:51:54', 'admin', '2025-12-01 19:57:36', NULL);
INSERT INTO `sys_dict_data` VALUES (109, 4, '已下线', '40', 'biz_job_status', NULL, 'primary', 'N', '0', 'admin', '2025-12-01 19:52:03', 'admin', '2025-12-01 19:57:43', NULL);
INSERT INTO `sys_dict_data` VALUES (110, 0, '已投递', '10', 'biz_application_status', NULL, 'info', 'N', '0', 'admin', '2025-12-01 19:52:22', 'admin', '2025-12-01 19:57:54', NULL);
INSERT INTO `sys_dict_data` VALUES (111, 1, '候选人', '20', 'biz_application_status', NULL, 'primary', 'N', '0', 'admin', '2025-12-01 19:52:27', 'admin', '2025-12-01 19:57:59', NULL);
INSERT INTO `sys_dict_data` VALUES (112, 2, '面试邀请', '30', 'biz_application_status', NULL, 'warning', 'N', '0', 'admin', '2025-12-01 19:52:36', 'admin', '2025-12-01 19:58:07', NULL);
INSERT INTO `sys_dict_data` VALUES (113, 3, '录用', '40', 'biz_application_status', NULL, 'success', 'N', '0', 'admin', '2025-12-01 19:52:49', 'admin', '2025-12-01 19:58:13', NULL);
INSERT INTO `sys_dict_data` VALUES (114, 4, '拒绝', '50', 'biz_application_status', NULL, 'danger', 'N', '0', 'admin', '2025-12-01 19:53:00', 'admin', '2025-12-01 19:58:18', NULL);
INSERT INTO `sys_dict_data` VALUES (115, 0, '本科', '0', 'biz_degree_level', NULL, 'default', 'N', '0', 'admin', '2025-12-01 19:53:24', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (116, 1, '硕士', '1', 'biz_degree_level', NULL, 'default', 'N', '0', 'admin', '2025-12-01 19:53:34', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (117, 2, '博士', '2', 'biz_degree_level', NULL, 'default', 'N', '0', 'admin', '2025-12-01 19:53:41', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (118, 0, '实习', '1', 'biz_work_nature', NULL, 'warning', 'N', '0', 'admin', '2025-12-01 19:53:59', 'admin', '2025-12-01 19:58:57', NULL);
INSERT INTO `sys_dict_data` VALUES (119, 1, '全职/校招', '2', 'biz_work_nature', NULL, 'primary', 'N', '0', 'admin', '2025-12-01 19:54:05', 'admin', '2025-12-01 19:58:54', NULL);
INSERT INTO `sys_dict_data` VALUES (120, 0, '暂不考虑', '0', 'biz_seek_status', NULL, 'info', 'N', '0', 'admin', '2025-12-01 19:54:53', 'admin', '2025-12-01 19:59:08', NULL);
INSERT INTO `sys_dict_data` VALUES (121, 1, '实习', '1', 'biz_seek_status', NULL, 'primary', 'N', '0', 'admin', '2025-12-01 19:55:01', 'admin', '2025-12-01 19:59:12', NULL);
INSERT INTO `sys_dict_data` VALUES (122, 2, '应届实习', '2', 'biz_seek_status', NULL, 'warning', 'N', '0', 'admin', '2025-12-01 19:55:08', 'admin', '2025-12-01 19:59:15', NULL);
INSERT INTO `sys_dict_data` VALUES (123, 3, '应届校招', '3', 'biz_seek_status', NULL, 'success', 'N', '0', 'admin', '2025-12-01 19:55:15', 'admin', '2025-12-01 19:59:20', NULL);
INSERT INTO `sys_dict_data` VALUES (124, 0, '本科及以上', '0', 'biz_required_degree_level', NULL, 'default', 'N', '0', 'admin', '2025-12-01 19:55:58', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (125, 1, '硕士及以上', '1', 'biz_required_degree_level', NULL, 'default', 'N', '0', 'admin', '2025-12-01 19:56:06', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (126, 2, '博士及以上', '2', 'biz_required_degree_level', NULL, 'default', 'N', '0', 'admin', '2025-12-01 19:56:14', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (127, 0, '男', '0', 'biz_gender', NULL, 'default', 'N', '0', 'admin', '2025-12-04 09:08:36', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (128, 1, '女', '1', 'biz_gender', NULL, 'default', 'N', '0', 'admin', '2025-12-04 09:08:42', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '任务状态列表');
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '任务分组列表');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '通知类型列表');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '通知状态列表');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '登录状态列表');
INSERT INTO `sys_dict_type` VALUES (100, '业务用户角色', 'biz_user_role', '0', 'admin', '2025-12-01 19:50:00', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (101, '业务账号状态', 'biz_user_status', '0', 'admin', '2025-12-01 19:50:34', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (102, '职位审核状态', 'biz_job_status', '0', 'admin', '2025-12-01 19:51:14', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (103, '简历投递状态', 'biz_application_status', '0', 'admin', '2025-12-01 19:52:15', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (104, '学历等级', 'biz_degree_level', '0', 'admin', '2025-12-01 19:53:15', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (105, '工作性质', 'biz_work_nature', '0', 'admin', '2025-12-01 19:53:52', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (106, '学生求职状态', 'biz_seek_status', '0', 'admin', '2025-12-01 19:54:14', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (107, '学历要求', 'biz_required_degree_level', '0', 'admin', '2025-12-01 19:55:48', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (108, '性别', 'biz_gender', '0', 'admin', '2025-12-04 09:08:24', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '定时任务调度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES (1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', '1', '1', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_job` VALUES (2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')', '0/15 * * * * ?', '3', '1', '1', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_job` VALUES (3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3', '1', '1', 'admin', '2025-12-01 19:33:15', '', NULL, '');

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '异常信息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE,
  INDEX `idx_sys_logininfor_s`(`status` ASC) USING BTREE,
  INDEX `idx_sys_logininfor_lt`(`login_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 110 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
INSERT INTO `sys_logininfor` VALUES (100, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Windows 10', '0', '登录成功', '2025-12-01 19:35:54');
INSERT INTO `sys_logininfor` VALUES (101, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Windows 10', '0', '登录成功', '2025-12-02 15:38:49');
INSERT INTO `sys_logininfor` VALUES (102, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Windows 10', '0', '登录成功', '2025-12-03 08:20:10');
INSERT INTO `sys_logininfor` VALUES (103, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Windows 10', '0', '退出成功', '2025-12-03 08:50:54');
INSERT INTO `sys_logininfor` VALUES (104, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Windows 10', '0', '登录成功', '2025-12-03 08:51:51');
INSERT INTO `sys_logininfor` VALUES (105, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Windows 10', '0', '登录成功', '2025-12-03 10:44:50');
INSERT INTO `sys_logininfor` VALUES (106, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Windows 10', '0', '登录成功', '2025-12-03 14:28:20');
INSERT INTO `sys_logininfor` VALUES (107, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Windows 10', '1', '验证码已失效', '2025-12-03 14:31:11');
INSERT INTO `sys_logininfor` VALUES (108, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Windows 10', '0', '登录成功', '2025-12-03 14:31:24');
INSERT INTO `sys_logininfor` VALUES (109, 'admin', '127.0.0.1', '内网IP', 'Chrome 14', 'Windows 10', '0', '登录成功', '2025-12-04 08:42:29');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由参数',
  `route_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '路由名称',
  `is_frame` int(1) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2078 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 4, 'system', NULL, '', '', 1, 0, 'M', '0', '0', '', 'system', 'admin', '2025-12-01 19:33:15', 'admin', '2025-12-01 20:02:12', '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 6, 'monitor', NULL, '', '', 1, 0, 'M', '1', '0', '', 'monitor', 'admin', '2025-12-01 19:33:15', 'admin', '2025-12-04 09:36:12', '系统监控目录');
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 5, 'tool', NULL, '', '', 1, 0, 'M', '1', '0', '', 'tool', 'admin', '2025-12-01 19:33:15', 'admin', '2025-12-04 09:47:59', '系统工具目录');
INSERT INTO `sys_menu` VALUES (4, '若依官网', 0, 4, 'http://ruoyi.vip', NULL, '', '', 0, 0, 'M', '1', '0', '', 'guide', 'admin', '2025-12-01 19:33:15', 'admin', '2025-12-01 20:01:37', '若依官网地址');
INSERT INTO `sys_menu` VALUES (100, '管理员列表', 1, 1, 'user', 'system/user/index', '', '', 1, 0, 'C', '0', '0', 'system:user:list', '#', 'admin', '2025-12-01 19:33:15', 'admin', '2025-12-01 20:03:31', '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '', '', 1, 0, 'C', '0', '0', 'system:role:list', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', '', 1, 0, 'C', '0', '0', 'system:menu:list', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', '', '', 1, 0, 'C', '0', '1', 'system:dept:list', '#', 'admin', '2025-12-01 19:33:15', 'admin', '2025-12-01 20:03:40', '部门管理菜单');
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 1, 5, 'post', 'system/post/index', '', '', 1, 0, 'C', '0', '1', 'system:post:list', '#', 'admin', '2025-12-01 19:33:15', 'admin', '2025-12-01 20:04:13', '岗位管理菜单');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', '', '', 1, 0, 'C', '0', '1', 'system:dict:list', '#', 'admin', '2025-12-01 19:33:15', 'admin', '2025-12-04 09:48:32', '字典管理菜单');
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', '', '', 1, 0, 'C', '0', '0', 'system:config:list', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '参数设置菜单');
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', '', '', 1, 0, 'C', '0', '1', 'system:notice:list', '#', 'admin', '2025-12-01 19:33:15', 'admin', '2025-12-04 09:48:49', '通知公告菜单');
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 9, 'log', '', '', '', 1, 0, 'M', '0', '0', '', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '日志管理菜单');
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', '', '', 1, 0, 'C', '0', '0', 'monitor:online:list', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '在线用户菜单');
INSERT INTO `sys_menu` VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', '', '', 1, 0, 'C', '0', '0', 'monitor:job:list', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '定时任务菜单');
INSERT INTO `sys_menu` VALUES (111, '数据监控', 2, 3, 'druid', 'monitor/druid/index', '', '', 1, 0, 'C', '0', '0', 'monitor:druid:list', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '数据监控菜单');
INSERT INTO `sys_menu` VALUES (112, '服务监控', 2, 4, 'server', 'monitor/server/index', '', '', 1, 0, 'C', '0', '0', 'monitor:server:list', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '服务监控菜单');
INSERT INTO `sys_menu` VALUES (113, '缓存监控', 2, 5, 'cache', 'monitor/cache/index', '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '缓存监控菜单');
INSERT INTO `sys_menu` VALUES (114, '缓存列表', 2, 6, 'cacheList', 'monitor/cache/list', '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '缓存列表菜单');
INSERT INTO `sys_menu` VALUES (115, '表单构建', 3, 1, 'build', 'tool/build/index', '', '', 1, 0, 'C', '0', '1', 'tool:build:list', '#', 'admin', '2025-12-01 19:33:15', 'admin', '2025-12-01 20:01:58', '表单构建菜单');
INSERT INTO `sys_menu` VALUES (116, '代码生成', 3, 2, 'gen', 'tool/gen/index', '', '', 1, 0, 'C', '0', '0', 'tool:gen:list', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '代码生成菜单');
INSERT INTO `sys_menu` VALUES (117, '系统接口', 3, 3, 'swagger', 'tool/swagger/index', '', '', 1, 0, 'C', '0', '1', 'tool:swagger:list', '#', 'admin', '2025-12-01 19:33:15', 'admin', '2025-12-01 20:02:01', '系统接口菜单');
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', '', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '操作日志菜单');
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', '', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '登录日志菜单');
INSERT INTO `sys_menu` VALUES (1000, '用户查询', 100, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1001, '用户新增', 100, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1002, '用户修改', 100, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1003, '用户删除', 100, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1004, '用户导出', 100, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1005, '用户导入', 100, 6, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1006, '重置密码', 100, 7, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1007, '角色查询', 101, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1008, '角色新增', 101, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1009, '角色修改', 101, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1010, '角色删除', 101, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1011, '角色导出', 101, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1012, '菜单查询', 102, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1013, '菜单新增', 102, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1014, '菜单修改', 102, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1015, '菜单删除', 102, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1016, '部门查询', 103, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1017, '部门新增', 103, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1018, '部门修改', 103, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1019, '部门删除', 103, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1020, '岗位查询', 104, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1021, '岗位新增', 104, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1022, '岗位修改', 104, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1023, '岗位删除', 104, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1024, '岗位导出', 104, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1025, '字典查询', 105, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1026, '字典新增', 105, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1027, '字典修改', 105, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1028, '字典删除', 105, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1029, '字典导出', 105, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1030, '参数查询', 106, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1031, '参数新增', 106, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1032, '参数修改', 106, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1033, '参数删除', 106, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1034, '参数导出', 106, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1035, '公告查询', 107, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1036, '公告新增', 107, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1037, '公告修改', 107, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1038, '公告删除', 107, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1039, '操作查询', 500, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1040, '操作删除', 500, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1041, '日志导出', 500, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1042, '登录查询', 501, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1043, '登录删除', 501, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1044, '日志导出', 501, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1045, '账户解锁', 501, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1046, '在线查询', 109, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1047, '批量强退', 109, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1048, '单条强退', 109, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1049, '任务查询', 110, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1050, '任务新增', 110, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1051, '任务修改', 110, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1052, '任务删除', 110, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1053, '状态修改', 110, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1054, '任务导出', 110, 6, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1055, '生成查询', 116, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1056, '生成修改', 116, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1057, '生成删除', 116, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1058, '导入代码', 116, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1059, '预览代码', 116, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1060, '生成代码', 116, 6, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2000, '招聘中心', 0, 1, 'recruit', NULL, NULL, '', 1, 0, 'M', '0', '0', NULL, 'guide', 'admin', '2025-12-01 20:00:40', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2001, '用户中心', 0, 2, 'member', NULL, NULL, '', 1, 0, 'M', '0', '0', NULL, 'user', 'admin', '2025-12-01 20:01:06', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2002, '基础数据', 0, 3, 'base', NULL, NULL, '', 1, 0, 'M', '0', '0', NULL, 'documentation', 'admin', '2025-12-01 20:01:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2003, '岗位管理', 2000, 1, 'jobs', 'recruit/jobs/index', NULL, '', 1, 0, 'C', '0', '0', 'recruit:jobs:list', '#', 'admin', '2025-12-01 20:51:07', 'admin', '2025-12-04 09:42:27', '岗位管理菜单');
INSERT INTO `sys_menu` VALUES (2004, '岗位管理查询', 2003, 1, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:jobs:query', '#', 'admin', '2025-12-01 20:51:07', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2005, '岗位管理新增', 2003, 2, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:jobs:add', '#', 'admin', '2025-12-01 20:51:07', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2006, '岗位管理修改', 2003, 3, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:jobs:edit', '#', 'admin', '2025-12-01 20:51:07', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2007, '岗位管理删除', 2003, 4, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:jobs:remove', '#', 'admin', '2025-12-01 20:51:07', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2008, '岗位管理导出', 2003, 5, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:jobs:export', '#', 'admin', '2025-12-01 20:51:07', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2009, '审核中心', 0, 0, 'judge', NULL, NULL, '', 1, 0, 'M', '0', '0', NULL, 'checkbox', 'admin', '2025-12-03 08:21:05', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2010, '企业信息管理', 2001, 2, 'companies', 'recruit/companies/index', NULL, '', 1, 0, 'C', '0', '0', 'recruit:companies:list', '#', 'admin', '2025-12-03 09:03:42', 'admin', '2025-12-04 09:43:30', '企业管理菜单');
INSERT INTO `sys_menu` VALUES (2011, '企业管理查询', 2010, 1, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:companies:query', '#', 'admin', '2025-12-03 09:03:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2012, '企业管理新增', 2010, 2, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:companies:add', '#', 'admin', '2025-12-03 09:03:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2013, '企业管理修改', 2010, 3, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:companies:edit', '#', 'admin', '2025-12-03 09:03:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2014, '企业管理删除', 2010, 4, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:companies:remove', '#', 'admin', '2025-12-03 09:03:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2015, '企业管理导出', 2010, 5, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:companies:export', '#', 'admin', '2025-12-03 09:03:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2016, '账号管理', 2001, 0, 'allAsers', 'recruit/allAsers/index', NULL, '', 1, 0, 'C', '0', '0', 'recruit:allAsers:list', '#', 'admin', '2025-12-03 10:50:15', 'admin', '2025-12-04 09:43:49', '账号管理菜单');
INSERT INTO `sys_menu` VALUES (2017, '账号管理查询', 2016, 1, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:allAsers:query', '#', 'admin', '2025-12-03 10:50:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2018, '账号管理新增', 2016, 2, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:allAsers:add', '#', 'admin', '2025-12-03 10:50:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2019, '账号管理修改', 2016, 3, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:allAsers:edit', '#', 'admin', '2025-12-03 10:50:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2020, '账号管理删除', 2016, 4, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:allAsers:remove', '#', 'admin', '2025-12-03 10:50:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2021, '账号管理导出', 2016, 5, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:allAsers:export', '#', 'admin', '2025-12-03 10:50:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2022, '求职活动管理', 2000, 1, 'events', 'recruit/events/index', NULL, '', 1, 0, 'C', '0', '0', 'recruit:events:list', '#', 'admin', '2025-12-03 11:51:45', 'admin', '2025-12-04 09:42:49', '求职活动管理菜单');
INSERT INTO `sys_menu` VALUES (2023, '求职活动管理查询', 2022, 1, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:events:query', '#', 'admin', '2025-12-03 11:51:45', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2024, '求职活动管理新增', 2022, 2, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:events:add', '#', 'admin', '2025-12-03 11:51:45', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2025, '求职活动管理修改', 2022, 3, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:events:edit', '#', 'admin', '2025-12-03 11:51:45', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2026, '求职活动管理删除', 2022, 4, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:events:remove', '#', 'admin', '2025-12-03 11:51:45', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2027, '求职活动管理导出', 2022, 5, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:events:export', '#', 'admin', '2025-12-03 11:51:45', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2028, '行业信息管理', 2002, 1, 'industries', 'recruit/industries/index', NULL, '', 1, 0, 'C', '0', '0', 'recruit:industries:list', '#', 'admin', '2025-12-03 14:35:27', '', NULL, '行业信息管理菜单');
INSERT INTO `sys_menu` VALUES (2029, '行业信息管理查询', 2028, 1, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:industries:query', '#', 'admin', '2025-12-03 14:35:27', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2030, '行业信息管理新增', 2028, 2, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:industries:add', '#', 'admin', '2025-12-03 14:35:27', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2031, '行业信息管理修改', 2028, 3, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:industries:edit', '#', 'admin', '2025-12-03 14:35:27', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2032, '行业信息管理删除', 2028, 4, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:industries:remove', '#', 'admin', '2025-12-03 14:35:27', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2033, '行业信息管理导出', 2028, 5, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:industries:export', '#', 'admin', '2025-12-03 14:35:27', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2034, '岗位类别管理', 2002, 1, 'categories', 'recruit/categories/index', NULL, '', 1, 0, 'C', '0', '0', 'recruit:categories:list', '#', 'admin', '2025-12-03 14:46:14', '', NULL, '岗位类别管理菜单');
INSERT INTO `sys_menu` VALUES (2035, '岗位类别管理查询', 2034, 1, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:categories:query', '#', 'admin', '2025-12-03 14:46:14', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2036, '岗位类别管理新增', 2034, 2, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:categories:add', '#', 'admin', '2025-12-03 14:46:14', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2037, '岗位类别管理修改', 2034, 3, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:categories:edit', '#', 'admin', '2025-12-03 14:46:14', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2038, '岗位类别管理删除', 2034, 4, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:categories:remove', '#', 'admin', '2025-12-03 14:46:14', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2039, '岗位类别管理导出', 2034, 5, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:categories:export', '#', 'admin', '2025-12-03 14:46:14', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2040, '标签分类管理', 2002, 1, 'categories', 'recruit/categories/index', NULL, '', 1, 0, 'C', '0', '0', 'recruit:categories:list', '#', 'admin', '2025-12-03 15:58:36', '', NULL, '标签分类管理菜单');
INSERT INTO `sys_menu` VALUES (2041, '标签分类管理查询', 2040, 1, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:categories:query', '#', 'admin', '2025-12-03 15:58:36', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2042, '标签分类管理新增', 2040, 2, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:categories:add', '#', 'admin', '2025-12-03 15:58:36', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2043, '标签分类管理修改', 2040, 3, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:categories:edit', '#', 'admin', '2025-12-03 15:58:36', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2044, '标签分类管理删除', 2040, 4, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:categories:remove', '#', 'admin', '2025-12-03 15:58:36', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2045, '标签分类管理导出', 2040, 5, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:categories:export', '#', 'admin', '2025-12-03 15:58:36', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2046, '企业性质管理', 2002, 1, 'natures', 'recruit/natures/index', NULL, '', 1, 0, 'C', '0', '0', 'recruit:natures:list', '#', 'admin', '2025-12-03 15:58:46', '', NULL, '企业性质管理菜单');
INSERT INTO `sys_menu` VALUES (2047, '企业性质管理查询', 2046, 1, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:natures:query', '#', 'admin', '2025-12-03 15:58:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2048, '企业性质管理新增', 2046, 2, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:natures:add', '#', 'admin', '2025-12-03 15:58:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2049, '企业性质管理修改', 2046, 3, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:natures:edit', '#', 'admin', '2025-12-03 15:58:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2050, '企业性质管理删除', 2046, 4, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:natures:remove', '#', 'admin', '2025-12-03 15:58:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2051, '企业性质管理导出', 2046, 5, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:natures:export', '#', 'admin', '2025-12-03 15:58:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2052, '规模信息管理', 2002, 1, 'scales', 'recruit/scales/index', NULL, '', 1, 0, 'C', '0', '0', 'recruit:scales:list', '#', 'admin', '2025-12-03 15:58:54', '', NULL, '规模信息管理菜单');
INSERT INTO `sys_menu` VALUES (2053, '规模信息管理查询', 2052, 1, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:scales:query', '#', 'admin', '2025-12-03 15:58:54', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2054, '规模信息管理新增', 2052, 2, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:scales:add', '#', 'admin', '2025-12-03 15:58:54', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2055, '规模信息管理修改', 2052, 3, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:scales:edit', '#', 'admin', '2025-12-03 15:58:54', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2056, '规模信息管理删除', 2052, 4, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:scales:remove', '#', 'admin', '2025-12-03 15:58:54', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2057, '规模信息管理导出', 2052, 5, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:scales:export', '#', 'admin', '2025-12-03 15:58:54', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2058, '标签库', 2002, 0, 'tags', 'recruit/tags/index', NULL, '', 1, 0, 'C', '0', '0', 'recruit:tags:list', '#', 'admin', '2025-12-03 15:59:02', 'admin', '2025-12-04 09:50:30', '标签库菜单');
INSERT INTO `sys_menu` VALUES (2059, '标签库查询', 2058, 1, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:tags:query', '#', 'admin', '2025-12-03 15:59:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2060, '标签库新增', 2058, 2, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:tags:add', '#', 'admin', '2025-12-03 15:59:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2061, '标签库修改', 2058, 3, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:tags:edit', '#', 'admin', '2025-12-03 15:59:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2062, '标签库删除', 2058, 4, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:tags:remove', '#', 'admin', '2025-12-03 15:59:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2063, '标签库导出', 2058, 5, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:tags:export', '#', 'admin', '2025-12-03 15:59:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2064, '投递记录', 2000, 0, 'applications', 'recruit/applications/index', NULL, '', 1, 0, 'C', '0', '0', 'recruit:applications:list', '#', 'admin', '2025-12-04 08:54:43', 'admin', '2025-12-04 09:50:07', '投递记录菜单');
INSERT INTO `sys_menu` VALUES (2065, '投递记录查询', 2064, 1, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:applications:query', '#', 'admin', '2025-12-04 08:54:43', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2066, '投递记录新增', 2064, 2, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:applications:add', '#', 'admin', '2025-12-04 08:54:43', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2067, '投递记录修改', 2064, 3, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:applications:edit', '#', 'admin', '2025-12-04 08:54:43', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2068, '投递记录删除', 2064, 4, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:applications:remove', '#', 'admin', '2025-12-04 08:54:43', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2069, '投递记录导出', 2064, 5, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:applications:export', '#', 'admin', '2025-12-04 08:54:43', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2070, '学生信息管理', 2001, 1, 'students', 'recruit/students/index', NULL, '', 1, 0, 'C', '0', '0', 'recruit:students:list', '#', 'admin', '2025-12-04 09:16:23', 'admin', '2025-12-04 09:44:04', '学生信息管理菜单');
INSERT INTO `sys_menu` VALUES (2071, '学生信息管理查询', 2070, 1, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:students:query', '#', 'admin', '2025-12-04 09:16:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2072, '学生信息管理新增', 2070, 2, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:students:add', '#', 'admin', '2025-12-04 09:16:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2073, '学生信息管理修改', 2070, 3, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:students:edit', '#', 'admin', '2025-12-04 09:16:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2074, '学生信息管理删除', 2070, 4, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:students:remove', '#', 'admin', '2025-12-04 09:16:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2075, '学生信息管理导出', 2070, 5, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'recruit:students:export', '#', 'admin', '2025-12-04 09:16:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2076, '企业账号审核', 2009, 1, 'account-review', NULL, NULL, '', 1, 0, 'C', '0', '0', '', '#', 'admin', '2025-12-04 09:31:26', 'admin', '2025-12-04 09:39:21', '');
INSERT INTO `sys_menu` VALUES (2077, '岗位发布审核', 2009, 1, 'job-review', NULL, NULL, '', 1, 0, 'C', '0', '0', '', '#', 'admin', '2025-12-04 09:33:51', 'admin', '2025-12-04 09:41:57', '');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob NULL COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, '温馨提醒：2018-07-01 若依新版本发布啦', '2', 0xE696B0E78988E69CACE58685E5AEB9, '0', 'admin', '2025-12-01 19:33:15', '', NULL, '管理员');
INSERT INTO `sys_notice` VALUES (2, '维护通知：2018-07-01 若依系统凌晨维护', '1', 0xE7BBB4E68AA4E58685E5AEB9, '0', 'admin', '2025-12-01 19:33:15', '', NULL, '管理员');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` int(1) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint(20) NULL DEFAULT 0 COMMENT '消耗时间',
  PRIMARY KEY (`oper_id`) USING BTREE,
  INDEX `idx_sys_oper_log_bt`(`business_type` ASC) USING BTREE,
  INDEX `idx_sys_oper_log_s`(`status` ASC) USING BTREE,
  INDEX `idx_sys_oper_log_ot`(`oper_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 260 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES (100, '字典类型', 1, 'com.ruoyi.web.controller.system.SysDictTypeController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/type', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"dictName\":\"业务用户角色\",\"dictType\":\"biz_user_role\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:50:00', 71);
INSERT INTO `sys_oper_log` VALUES (101, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"学生\",\"dictSort\":0,\"dictType\":\"biz_user_role\",\"dictValue\":\"1\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:50:13', 24);
INSERT INTO `sys_oper_log` VALUES (102, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"企业HR\",\"dictSort\":1,\"dictType\":\"biz_user_role\",\"dictValue\":\"2\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:50:22', 14);
INSERT INTO `sys_oper_log` VALUES (103, '字典类型', 1, 'com.ruoyi.web.controller.system.SysDictTypeController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/type', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"dictName\":\"业务账号状态\",\"dictType\":\"biz_user_status\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:50:34', 16);
INSERT INTO `sys_oper_log` VALUES (104, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"待审核\",\"dictSort\":0,\"dictType\":\"biz_user_status\",\"dictValue\":\"0\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:50:47', 17);
INSERT INTO `sys_oper_log` VALUES (105, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"正常\",\"dictSort\":1,\"dictType\":\"biz_user_status\",\"dictValue\":\"1\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:50:53', 14);
INSERT INTO `sys_oper_log` VALUES (106, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"封禁\",\"dictSort\":2,\"dictType\":\"biz_user_status\",\"dictValue\":\"2\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:51:01', 14);
INSERT INTO `sys_oper_log` VALUES (107, '字典类型', 1, 'com.ruoyi.web.controller.system.SysDictTypeController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/type', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"dictName\":\"职位审核状态\",\"dictType\":\"biz_job_status\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:51:14', 13);
INSERT INTO `sys_oper_log` VALUES (108, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"草稿\",\"dictSort\":0,\"dictType\":\"biz_job_status\",\"dictValue\":\"1\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:51:24', 14);
INSERT INTO `sys_oper_log` VALUES (109, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"待审核\",\"dictSort\":1,\"dictType\":\"biz_job_status\",\"dictValue\":\"10\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:51:32', 7);
INSERT INTO `sys_oper_log` VALUES (110, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"已发布\",\"dictSort\":2,\"dictType\":\"biz_job_status\",\"dictValue\":\"20\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:51:42', 8);
INSERT INTO `sys_oper_log` VALUES (111, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"已驳回\",\"dictSort\":3,\"dictType\":\"biz_job_status\",\"dictValue\":\"30\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:51:54', 14);
INSERT INTO `sys_oper_log` VALUES (112, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"已下线\",\"dictSort\":4,\"dictType\":\"biz_job_status\",\"dictValue\":\"40\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:52:03', 12);
INSERT INTO `sys_oper_log` VALUES (113, '字典类型', 1, 'com.ruoyi.web.controller.system.SysDictTypeController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/type', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"dictName\":\"简历投递状态\",\"dictType\":\"biz_application_status\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:52:15', 11);
INSERT INTO `sys_oper_log` VALUES (114, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"已投递\",\"dictSort\":0,\"dictType\":\"biz_application_status\",\"dictValue\":\"10\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:52:22', 17);
INSERT INTO `sys_oper_log` VALUES (115, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"候选人\",\"dictSort\":0,\"dictType\":\"biz_application_status\",\"dictValue\":\"20\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:52:27', 14);
INSERT INTO `sys_oper_log` VALUES (116, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"面试邀请\",\"dictSort\":2,\"dictType\":\"biz_application_status\",\"dictValue\":\"30\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:52:36', 20);
INSERT INTO `sys_oper_log` VALUES (117, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:52:27\",\"default\":false,\"dictCode\":111,\"dictLabel\":\"候选人\",\"dictSort\":1,\"dictType\":\"biz_application_status\",\"dictValue\":\"20\",\"isDefault\":\"N\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:52:40', 20);
INSERT INTO `sys_oper_log` VALUES (118, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"录用\",\"dictSort\":3,\"dictType\":\"biz_application_status\",\"dictValue\":\"40\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:52:49', 19);
INSERT INTO `sys_oper_log` VALUES (119, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"拒绝\",\"dictSort\":4,\"dictType\":\"biz_application_status\",\"dictValue\":\"50\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:53:00', 13);
INSERT INTO `sys_oper_log` VALUES (120, '字典类型', 1, 'com.ruoyi.web.controller.system.SysDictTypeController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/type', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"dictName\":\"学历等级\",\"dictType\":\"biz_degree_level\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:53:15', 14);
INSERT INTO `sys_oper_log` VALUES (121, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"本科\",\"dictSort\":0,\"dictType\":\"biz_degree_level\",\"dictValue\":\"0\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:53:24', 14);
INSERT INTO `sys_oper_log` VALUES (122, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"硕士\",\"dictSort\":1,\"dictType\":\"biz_degree_level\",\"dictValue\":\"1\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:53:34', 13);
INSERT INTO `sys_oper_log` VALUES (123, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"博士\",\"dictSort\":2,\"dictType\":\"biz_degree_level\",\"dictValue\":\"2\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:53:41', 7);
INSERT INTO `sys_oper_log` VALUES (124, '字典类型', 1, 'com.ruoyi.web.controller.system.SysDictTypeController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/type', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"dictName\":\"工作性质\",\"dictType\":\"biz_work_nature\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:53:52', 9);
INSERT INTO `sys_oper_log` VALUES (125, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"实习\",\"dictSort\":0,\"dictType\":\"biz_work_nature\",\"dictValue\":\"1\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:53:59', 14);
INSERT INTO `sys_oper_log` VALUES (126, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"全职/校招\",\"dictSort\":1,\"dictType\":\"biz_work_nature\",\"dictValue\":\"2\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:54:05', 7);
INSERT INTO `sys_oper_log` VALUES (127, '字典类型', 1, 'com.ruoyi.web.controller.system.SysDictTypeController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/type', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"dictName\":\"学生求职状态\",\"dictType\":\"biz_seek_status\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:54:14', 13);
INSERT INTO `sys_oper_log` VALUES (128, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"暂不考虑\",\"dictSort\":0,\"dictType\":\"biz_seek_status\",\"dictValue\":\"0\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:54:53', 48);
INSERT INTO `sys_oper_log` VALUES (129, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"实习\",\"dictSort\":1,\"dictType\":\"biz_seek_status\",\"dictValue\":\"1\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:55:01', 10);
INSERT INTO `sys_oper_log` VALUES (130, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"应届实习\",\"dictSort\":2,\"dictType\":\"biz_seek_status\",\"dictValue\":\"2\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:55:08', 15);
INSERT INTO `sys_oper_log` VALUES (131, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"应届校招\",\"dictSort\":3,\"dictType\":\"biz_seek_status\",\"dictValue\":\"3\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:55:15', 9);
INSERT INTO `sys_oper_log` VALUES (132, '字典类型', 1, 'com.ruoyi.web.controller.system.SysDictTypeController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/type', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"dictName\":\"学历要求\",\"dictType\":\"biz_required_degree_level\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:55:48', 13);
INSERT INTO `sys_oper_log` VALUES (133, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"本科及以上\",\"dictSort\":0,\"dictType\":\"biz_required_degree_level\",\"dictValue\":\"0\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:55:58', 14);
INSERT INTO `sys_oper_log` VALUES (134, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"硕士及以上\",\"dictSort\":1,\"dictType\":\"biz_required_degree_level\",\"dictValue\":\"1\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:56:06', 13);
INSERT INTO `sys_oper_log` VALUES (135, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"博士及以上\",\"dictSort\":2,\"dictType\":\"biz_required_degree_level\",\"dictValue\":\"2\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:56:14', 10);
INSERT INTO `sys_oper_log` VALUES (136, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:50:47\",\"default\":false,\"dictCode\":102,\"dictLabel\":\"待审核\",\"dictSort\":0,\"dictType\":\"biz_user_status\",\"dictValue\":\"0\",\"isDefault\":\"N\",\"listClass\":\"warning\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:56:33', 11);
INSERT INTO `sys_oper_log` VALUES (137, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:50:53\",\"default\":false,\"dictCode\":103,\"dictLabel\":\"正常\",\"dictSort\":1,\"dictType\":\"biz_user_status\",\"dictValue\":\"1\",\"isDefault\":\"N\",\"listClass\":\"success\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:56:39', 18);
INSERT INTO `sys_oper_log` VALUES (138, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:51:01\",\"default\":false,\"dictCode\":104,\"dictLabel\":\"封禁\",\"dictSort\":2,\"dictType\":\"biz_user_status\",\"dictValue\":\"2\",\"isDefault\":\"N\",\"listClass\":\"danger\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:56:48', 18);
INSERT INTO `sys_oper_log` VALUES (139, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:50:13\",\"default\":false,\"dictCode\":100,\"dictLabel\":\"学生\",\"dictSort\":0,\"dictType\":\"biz_user_role\",\"dictValue\":\"1\",\"isDefault\":\"N\",\"listClass\":\"primary\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:56:57', 14);
INSERT INTO `sys_oper_log` VALUES (140, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:50:22\",\"default\":false,\"dictCode\":101,\"dictLabel\":\"企业HR\",\"dictSort\":1,\"dictType\":\"biz_user_role\",\"dictValue\":\"2\",\"isDefault\":\"N\",\"listClass\":\"info\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:57:00', 15);
INSERT INTO `sys_oper_log` VALUES (141, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:50:22\",\"default\":false,\"dictCode\":101,\"dictLabel\":\"企业HR\",\"dictSort\":1,\"dictType\":\"biz_user_role\",\"dictValue\":\"2\",\"isDefault\":\"N\",\"listClass\":\"warning\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:57:08', 8);
INSERT INTO `sys_oper_log` VALUES (142, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:51:24\",\"default\":false,\"dictCode\":105,\"dictLabel\":\"草稿\",\"dictSort\":0,\"dictType\":\"biz_job_status\",\"dictValue\":\"1\",\"isDefault\":\"N\",\"listClass\":\"info\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:57:20', 14);
INSERT INTO `sys_oper_log` VALUES (143, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:51:32\",\"default\":false,\"dictCode\":106,\"dictLabel\":\"待审核\",\"dictSort\":1,\"dictType\":\"biz_job_status\",\"dictValue\":\"10\",\"isDefault\":\"N\",\"listClass\":\"warning\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:57:26', 12);
INSERT INTO `sys_oper_log` VALUES (144, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:51:42\",\"default\":false,\"dictCode\":107,\"dictLabel\":\"已发布\",\"dictSort\":2,\"dictType\":\"biz_job_status\",\"dictValue\":\"20\",\"isDefault\":\"N\",\"listClass\":\"success\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:57:30', 10);
INSERT INTO `sys_oper_log` VALUES (145, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:51:54\",\"default\":false,\"dictCode\":108,\"dictLabel\":\"已驳回\",\"dictSort\":3,\"dictType\":\"biz_job_status\",\"dictValue\":\"30\",\"isDefault\":\"N\",\"listClass\":\"danger\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:57:36', 10);
INSERT INTO `sys_oper_log` VALUES (146, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:52:03\",\"default\":false,\"dictCode\":109,\"dictLabel\":\"已下线\",\"dictSort\":4,\"dictType\":\"biz_job_status\",\"dictValue\":\"40\",\"isDefault\":\"N\",\"listClass\":\"primary\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:57:43', 10);
INSERT INTO `sys_oper_log` VALUES (147, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:52:22\",\"default\":false,\"dictCode\":110,\"dictLabel\":\"已投递\",\"dictSort\":0,\"dictType\":\"biz_application_status\",\"dictValue\":\"10\",\"isDefault\":\"N\",\"listClass\":\"info\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:57:54', 11);
INSERT INTO `sys_oper_log` VALUES (148, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:52:27\",\"default\":false,\"dictCode\":111,\"dictLabel\":\"候选人\",\"dictSort\":1,\"dictType\":\"biz_application_status\",\"dictValue\":\"20\",\"isDefault\":\"N\",\"listClass\":\"primary\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:57:59', 14);
INSERT INTO `sys_oper_log` VALUES (149, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:52:36\",\"default\":false,\"dictCode\":112,\"dictLabel\":\"面试邀请\",\"dictSort\":2,\"dictType\":\"biz_application_status\",\"dictValue\":\"30\",\"isDefault\":\"N\",\"listClass\":\"warning\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:58:07', 8);
INSERT INTO `sys_oper_log` VALUES (150, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:52:49\",\"default\":false,\"dictCode\":113,\"dictLabel\":\"录用\",\"dictSort\":3,\"dictType\":\"biz_application_status\",\"dictValue\":\"40\",\"isDefault\":\"N\",\"listClass\":\"success\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:58:14', 12);
INSERT INTO `sys_oper_log` VALUES (151, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:53:00\",\"default\":false,\"dictCode\":114,\"dictLabel\":\"拒绝\",\"dictSort\":4,\"dictType\":\"biz_application_status\",\"dictValue\":\"50\",\"isDefault\":\"N\",\"listClass\":\"danger\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:58:18', 9);
INSERT INTO `sys_oper_log` VALUES (152, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:53:59\",\"default\":false,\"dictCode\":118,\"dictLabel\":\"实习\",\"dictSort\":0,\"dictType\":\"biz_work_nature\",\"dictValue\":\"1\",\"isDefault\":\"N\",\"listClass\":\"warning\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:58:43', 13);
INSERT INTO `sys_oper_log` VALUES (153, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:53:59\",\"default\":false,\"dictCode\":118,\"dictLabel\":\"实习\",\"dictSort\":0,\"dictType\":\"biz_work_nature\",\"dictValue\":\"1\",\"isDefault\":\"N\",\"listClass\":\"primary\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:58:46', 9);
INSERT INTO `sys_oper_log` VALUES (154, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:54:05\",\"default\":false,\"dictCode\":119,\"dictLabel\":\"全职/校招\",\"dictSort\":1,\"dictType\":\"biz_work_nature\",\"dictValue\":\"2\",\"isDefault\":\"N\",\"listClass\":\"primary\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:58:54', 9);
INSERT INTO `sys_oper_log` VALUES (155, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:53:59\",\"default\":false,\"dictCode\":118,\"dictLabel\":\"实习\",\"dictSort\":0,\"dictType\":\"biz_work_nature\",\"dictValue\":\"1\",\"isDefault\":\"N\",\"listClass\":\"warning\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:58:57', 12);
INSERT INTO `sys_oper_log` VALUES (156, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:54:53\",\"default\":false,\"dictCode\":120,\"dictLabel\":\"暂不考虑\",\"dictSort\":0,\"dictType\":\"biz_seek_status\",\"dictValue\":\"0\",\"isDefault\":\"N\",\"listClass\":\"info\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:59:08', 12);
INSERT INTO `sys_oper_log` VALUES (157, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:55:01\",\"default\":false,\"dictCode\":121,\"dictLabel\":\"实习\",\"dictSort\":1,\"dictType\":\"biz_seek_status\",\"dictValue\":\"1\",\"isDefault\":\"N\",\"listClass\":\"primary\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:59:12', 11);
INSERT INTO `sys_oper_log` VALUES (158, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:55:08\",\"default\":false,\"dictCode\":122,\"dictLabel\":\"应届实习\",\"dictSort\":2,\"dictType\":\"biz_seek_status\",\"dictValue\":\"2\",\"isDefault\":\"N\",\"listClass\":\"warning\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:59:15', 10);
INSERT INTO `sys_oper_log` VALUES (159, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2025-12-01 19:55:15\",\"default\":false,\"dictCode\":123,\"dictLabel\":\"应届校招\",\"dictSort\":3,\"dictType\":\"biz_seek_status\",\"dictValue\":\"3\",\"isDefault\":\"N\",\"listClass\":\"success\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 19:59:20', 10);
INSERT INTO `sys_oper_log` VALUES (160, '菜单管理', 1, 'com.ruoyi.web.controller.system.SysMenuController.add()', 'POST', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createBy\":\"admin\",\"icon\":\"guide\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"招聘中心\",\"menuType\":\"M\",\"orderNum\":1,\"params\":{},\"parentId\":0,\"path\":\"recruit\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 20:00:40', 38);
INSERT INTO `sys_oper_log` VALUES (161, '菜单管理', 1, 'com.ruoyi.web.controller.system.SysMenuController.add()', 'POST', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createBy\":\"admin\",\"icon\":\"user\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"用户中心\",\"menuType\":\"M\",\"orderNum\":2,\"params\":{},\"parentId\":0,\"path\":\"member\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 20:01:06', 14);
INSERT INTO `sys_oper_log` VALUES (162, '菜单管理', 1, 'com.ruoyi.web.controller.system.SysMenuController.add()', 'POST', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createBy\":\"admin\",\"icon\":\"documentation\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"基础数据\",\"menuType\":\"M\",\"orderNum\":3,\"params\":{},\"parentId\":0,\"path\":\"base\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 20:01:30', 10);
INSERT INTO `sys_oper_log` VALUES (163, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2025-12-01 19:33:15\",\"icon\":\"guide\",\"isCache\":\"0\",\"isFrame\":\"0\",\"menuId\":4,\"menuName\":\"若依官网\",\"menuType\":\"M\",\"orderNum\":4,\"params\":{},\"parentId\":0,\"path\":\"http://ruoyi.vip\",\"perms\":\"\",\"query\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"1\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 20:01:37', 12);
INSERT INTO `sys_oper_log` VALUES (164, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2025-12-01 19:33:15\",\"icon\":\"monitor\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2,\"menuName\":\"系统监控\",\"menuType\":\"M\",\"orderNum\":2,\"params\":{},\"parentId\":0,\"path\":\"monitor\",\"perms\":\"\",\"query\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"1\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 20:01:44', 13);
INSERT INTO `sys_oper_log` VALUES (165, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"tool/build/index\",\"createTime\":\"2025-12-01 19:33:15\",\"icon\":\"build\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":115,\"menuName\":\"表单构建\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":3,\"path\":\"build\",\"perms\":\"tool:build:list\",\"query\":\"\",\"routeName\":\"\",\"status\":\"1\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 20:01:58', 10);
INSERT INTO `sys_oper_log` VALUES (166, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"tool/swagger/index\",\"createTime\":\"2025-12-01 19:33:15\",\"icon\":\"swagger\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":117,\"menuName\":\"系统接口\",\"menuType\":\"C\",\"orderNum\":3,\"params\":{},\"parentId\":3,\"path\":\"swagger\",\"perms\":\"tool:swagger:list\",\"query\":\"\",\"routeName\":\"\",\"status\":\"1\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 20:02:01', 9);
INSERT INTO `sys_oper_log` VALUES (167, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2025-12-01 19:33:15\",\"icon\":\"system\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":1,\"menuName\":\"系统管理\",\"menuType\":\"M\",\"orderNum\":4,\"params\":{},\"parentId\":0,\"path\":\"system\",\"perms\":\"\",\"query\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 20:02:12', 10);
INSERT INTO `sys_oper_log` VALUES (168, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2025-12-01 19:33:15\",\"icon\":\"tool\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":3,\"menuName\":\"系统工具\",\"menuType\":\"M\",\"orderNum\":5,\"params\":{},\"parentId\":0,\"path\":\"tool\",\"perms\":\"\",\"query\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 20:02:18', 8);
INSERT INTO `sys_oper_log` VALUES (169, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"system/user/index\",\"createTime\":\"2025-12-01 19:33:15\",\"icon\":\"user\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":100,\"menuName\":\"管理员列表\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":1,\"path\":\"user\",\"perms\":\"system:user:list\",\"query\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 20:03:31', 10);
INSERT INTO `sys_oper_log` VALUES (170, '菜单管理', 3, 'com.ruoyi.web.controller.system.SysMenuController.remove()', 'DELETE', 1, 'admin', '研发部门', '/system/menu/103', '127.0.0.1', '内网IP', '103', '{\"msg\":\"存在子菜单,不允许删除\",\"code\":601}', 0, NULL, '2025-12-01 20:03:36', 10);
INSERT INTO `sys_oper_log` VALUES (171, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"system/dept/index\",\"createTime\":\"2025-12-01 19:33:15\",\"icon\":\"tree\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":103,\"menuName\":\"部门管理\",\"menuType\":\"C\",\"orderNum\":4,\"params\":{},\"parentId\":1,\"path\":\"dept\",\"perms\":\"system:dept:list\",\"query\":\"\",\"routeName\":\"\",\"status\":\"1\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 20:03:41', 12);
INSERT INTO `sys_oper_log` VALUES (172, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"system/post/index\",\"createTime\":\"2025-12-01 19:33:15\",\"icon\":\"post\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":104,\"menuName\":\"岗位管理\",\"menuType\":\"C\",\"orderNum\":5,\"params\":{},\"parentId\":1,\"path\":\"post\",\"perms\":\"system:post:list\",\"query\":\"\",\"routeName\":\"\",\"status\":\"1\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 20:04:14', 12);
INSERT INTO `sys_oper_log` VALUES (173, '代码生成', 6, 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', '研发部门', '/tool/gen/importTable', '127.0.0.1', '内网IP', '{\"tables\":\"jobs\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 20:22:36', 514);
INSERT INTO `sys_oper_log` VALUES (174, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"jobs\",\"className\":\"Jobs\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"岗位ID\",\"columnId\":1,\"columnName\":\"id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-01 20:22:36\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"CompanyId\",\"columnComment\":\"公司名称\",\"columnId\":2,\"columnName\":\"company_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-01 20:22:36\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"companyId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Department\",\"columnComment\":\"所属部门\",\"columnId\":18,\"columnName\":\"department\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-01 20:22:36\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"department\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Title\",\"columnComment\":\"岗位名称\",\"columnId\":4,\"columnName\":\"title\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-01 20:22:36\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaF', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 20:35:14', 122);
INSERT INTO `sys_oper_log` VALUES (175, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"jobs\",\"className\":\"Jobs\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"岗位ID\",\"columnId\":1,\"columnName\":\"id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-01 20:22:36\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"updateTime\":\"2025-12-01 20:35:14\",\"usableColumn\":false},{\"capJavaField\":\"CompanyId\",\"columnComment\":\"公司名称\",\"columnId\":2,\"columnName\":\"company_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-01 20:22:36\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"companyId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"updateTime\":\"2025-12-01 20:35:14\",\"usableColumn\":false},{\"capJavaField\":\"Department\",\"columnComment\":\"所属部门\",\"columnId\":18,\"columnName\":\"department\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-01 20:22:36\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"department\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"updateTime\":\"2025-12-01 20:35:14\",\"usableColumn\":false},{\"capJavaField\":\"Title\",\"columnComment\":\"岗位名称\",\"columnId\":4,\"columnName\":\"title\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-01 20:22:36\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"i', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 20:49:37', 173);
INSERT INTO `sys_oper_log` VALUES (176, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', '研发部门', '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"jobs\"}', NULL, 0, NULL, '2025-12-01 20:50:06', 437);
INSERT INTO `sys_oper_log` VALUES (177, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', '研发部门', '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"jobs\"}', NULL, 0, NULL, '2025-12-01 20:50:11', 117);
INSERT INTO `sys_oper_log` VALUES (178, '岗位管理', 2, 'com.ruoyi.recruit.controller.JobsController.edit()', 'PUT', 1, 'admin', '研发部门', '/recruit/jobs', '127.0.0.1', '内网IP', '{\"addressDetail\":\"海淀区软件园\",\"cityId\":1,\"companyId\":1,\"createdAt\":\"2025-12-01 20:49:10\",\"deadline\":\"2025-12-31 00:00:00\",\"description\":\"负责核心交易链路的开发...\",\"headcount\":5,\"id\":1,\"maxSalary\":40,\"minSalary\":25,\"params\":{},\"postedByUserId\":3,\"provinceId\":1,\"requiredDegree\":0,\"status\":30,\"techRequirements\":\"1. 精通Java; 2. 熟悉JVM; 3. 熟悉分布式系统\",\"title\":\"Java高级后端工程师\",\"type\":1,\"updatedAt\":\"2025-12-01 20:49:10\",\"viewCount\":2,\"workNature\":2}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 20:54:14', 47);
INSERT INTO `sys_oper_log` VALUES (179, '岗位管理', 2, 'com.ruoyi.recruit.controller.JobsController.edit()', 'PUT', 1, 'admin', '研发部门', '/recruit/jobs', '127.0.0.1', '内网IP', '{\"addressDetail\":\"海淀区软件园\",\"cityId\":1,\"companyId\":1,\"createdAt\":\"2025-12-01 20:49:10\",\"deadline\":\"2025-12-31 00:00:00\",\"description\":\"负责核心交易链路的开发...\",\"headcount\":5,\"id\":1,\"maxSalary\":40,\"minSalary\":25,\"params\":{},\"postedByUserId\":3,\"provinceId\":1,\"requiredDegree\":0,\"status\":20,\"techRequirements\":\"1. 精通Java; 2. 熟悉JVM; 3. 熟悉分布式系统\",\"title\":\"Java高级后端工程师\",\"type\":1,\"updatedAt\":\"2025-12-01 20:49:10\",\"viewCount\":2,\"workNature\":2}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 20:54:19', 9);
INSERT INTO `sys_oper_log` VALUES (180, '角色管理', 2, 'com.ruoyi.web.controller.system.SysRoleController.changeStatus()', 'PUT', 1, 'admin', '研发部门', '/system/role/changeStatus', '127.0.0.1', '内网IP', '{\"admin\":false,\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{},\"roleId\":2,\"status\":\"1\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 21:11:47', 124);
INSERT INTO `sys_oper_log` VALUES (181, '角色管理', 2, 'com.ruoyi.web.controller.system.SysRoleController.changeStatus()', 'PUT', 1, 'admin', '研发部门', '/system/role/changeStatus', '127.0.0.1', '内网IP', '{\"admin\":false,\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{},\"roleId\":2,\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 21:11:48', 7);
INSERT INTO `sys_oper_log` VALUES (182, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2025-12-01 19:33:15\",\"icon\":\"monitor\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2,\"menuName\":\"系统监控\",\"menuType\":\"M\",\"orderNum\":2,\"params\":{},\"parentId\":0,\"path\":\"monitor\",\"perms\":\"\",\"query\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 21:12:15', 22);
INSERT INTO `sys_oper_log` VALUES (183, '岗位管理', 2, 'com.ruoyi.recruit.controller.JobsController.edit()', 'PUT', 1, 'admin', '研发部门', '/recruit/jobs', '127.0.0.1', '内网IP', '{\"addressDetail\":\"海淀区软件园\",\"cityId\":1,\"companyId\":1,\"createdAt\":\"2025-12-01 20:49:10\",\"deadline\":\"2025-12-31 00:00:00\",\"description\":\"负责核心交易链路的开发...\",\"headcount\":5,\"id\":1,\"maxSalary\":40,\"minSalary\":20,\"params\":{},\"postedByUserId\":3,\"provinceId\":1,\"requiredDegree\":0,\"status\":20,\"techRequirements\":\"1. 精通Java; 2. 熟悉JVM; 3. 熟悉分布式系统\",\"title\":\"Java高级后端工程师\",\"type\":1,\"updatedAt\":\"2025-12-01 20:49:10\",\"viewCount\":2,\"workNature\":2}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-01 21:38:57', 35);
INSERT INTO `sys_oper_log` VALUES (184, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', '研发部门', '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"jobs\"}', NULL, 0, NULL, '2025-12-02 15:40:11', 528);
INSERT INTO `sys_oper_log` VALUES (185, '菜单管理', 1, 'com.ruoyi.web.controller.system.SysMenuController.add()', 'POST', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createBy\":\"admin\",\"icon\":\"checkbox\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"审核中心\",\"menuType\":\"M\",\"orderNum\":0,\"params\":{},\"parentId\":0,\"path\":\"judge\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 08:21:05', 99);
INSERT INTO `sys_oper_log` VALUES (186, '代码生成', 6, 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', '研发部门', '/tool/gen/importTable', '127.0.0.1', '内网IP', '{\"tables\":\"companies\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 08:52:18', 309);
INSERT INTO `sys_oper_log` VALUES (187, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"companies\",\"className\":\"Companies\",\"columns\":[{\"capJavaField\":\"CompanyId\",\"columnComment\":\"公司ID\",\"columnId\":24,\"columnName\":\"company_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 08:52:18\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"companyId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"UserId\",\"columnComment\":\"关联用户ID\",\"columnId\":25,\"columnName\":\"user_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 08:52:18\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"0\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"userId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"CompanyName\",\"columnComment\":\"公司名称\",\"columnId\":26,\"columnName\":\"company_name\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 08:52:18\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"companyName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Description\",\"columnComment\":\"公司介绍\",\"columnId\":27,\"columnName\":\"description\",\"columnType\":\"text\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 08:52:18\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"textarea\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"0\",\"isPk', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 08:56:53', 197);
INSERT INTO `sys_oper_log` VALUES (188, '代码生成', 6, 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', '研发部门', '/tool/gen/importTable', '127.0.0.1', '内网IP', '{\"tables\":\"users\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 09:19:10', 235);
INSERT INTO `sys_oper_log` VALUES (189, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"allAsers\",\"className\":\"Users\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"用户ID\",\"columnId\":36,\"columnName\":\"id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 09:19:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Email\",\"columnComment\":\"用户邮箱\",\"columnId\":37,\"columnName\":\"email\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 09:19:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"email\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"PasswordHash\",\"columnComment\":\"加密后的密码\",\"columnId\":38,\"columnName\":\"password_hash\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 09:19:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"0\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"passwordHash\",\"javaType\":\"String\",\"list\":false,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Role\",\"columnComment\":\"用户角色 1=student, 2=hr\",\"columnId\":39,\"columnName\":\"role\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 09:19:10\",\"dictType\":\"biz_user_role\",\"edit\":true,\"htmlType\":\"select\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\"', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 09:24:25', 216);
INSERT INTO `sys_oper_log` VALUES (190, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"allAsers\",\"className\":\"Users\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"用户ID\",\"columnId\":36,\"columnName\":\"id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 09:19:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2025-12-03 09:24:25\",\"usableColumn\":false},{\"capJavaField\":\"Email\",\"columnComment\":\"用户邮箱\",\"columnId\":37,\"columnName\":\"email\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 09:19:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"email\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2025-12-03 09:24:25\",\"usableColumn\":false},{\"capJavaField\":\"PasswordHash\",\"columnComment\":\"加密后的密码\",\"columnId\":38,\"columnName\":\"password_hash\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 09:19:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"0\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"passwordHash\",\"javaType\":\"String\",\"list\":false,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2025-12-03 09:24:25\",\"usableColumn\":false},{\"capJavaField\":\"Role\",\"columnComment\":\"用户角色\",\"columnId\":39,\"columnName\":\"role\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 09:19:10\",\"dictType\":\"biz_user_role\",\"edit\":true,\"htmlType\":\"selec', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 10:48:46', 324);
INSERT INTO `sys_oper_log` VALUES (191, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', '研发部门', '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"users\"}', NULL, 0, NULL, '2025-12-03 10:49:07', 551);
INSERT INTO `sys_oper_log` VALUES (192, '代码生成', 6, 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', '研发部门', '/tool/gen/importTable', '127.0.0.1', '内网IP', '{\"tables\":\"events\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 11:07:04', 797);
INSERT INTO `sys_oper_log` VALUES (193, '代码生成', 3, 'com.ruoyi.generator.controller.GenController.remove()', 'DELETE', 1, 'admin', '研发部门', '/tool/gen/4', '127.0.0.1', '内网IP', '[4]', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 11:15:27', 95);
INSERT INTO `sys_oper_log` VALUES (194, '代码生成', 6, 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', '研发部门', '/tool/gen/importTable', '127.0.0.1', '内网IP', '{\"tables\":\"events\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 11:20:03', 151);
INSERT INTO `sys_oper_log` VALUES (195, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"events\",\"className\":\"Events\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"活动ID\",\"columnId\":55,\"columnName\":\"id\",\"columnType\":\"bigint(20)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 11:20:03\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":5,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"AdminUserId\",\"columnComment\":\"发布管理员ID\",\"columnId\":56,\"columnName\":\"admin_user_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 11:20:03\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"0\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"adminUserId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":5,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"EventTitle\",\"columnComment\":\"活动标题\",\"columnId\":57,\"columnName\":\"event_title\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 11:20:03\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"eventTitle\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":5,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"EventSummary\",\"columnComment\":\"活动详情\",\"columnId\":58,\"columnName\":\"event_summary\",\"columnType\":\"varchar(500)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 11:20:03\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"textarea\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQue', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 11:28:30', 403);
INSERT INTO `sys_oper_log` VALUES (196, '代码生成', 6, 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', '研发部门', '/tool/gen/importTable', '127.0.0.1', '内网IP', '{\"tables\":\"t_industries\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 11:29:32', 116);
INSERT INTO `sys_oper_log` VALUES (197, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"events\",\"className\":\"Events\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"活动ID\",\"columnId\":55,\"columnName\":\"id\",\"columnType\":\"bigint(20)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 11:20:03\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":5,\"updateBy\":\"\",\"updateTime\":\"2025-12-03 11:28:30\",\"usableColumn\":false},{\"capJavaField\":\"AdminUserId\",\"columnComment\":\"发布管理员ID\",\"columnId\":56,\"columnName\":\"admin_user_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 11:20:03\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"adminUserId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":5,\"updateBy\":\"\",\"updateTime\":\"2025-12-03 11:28:30\",\"usableColumn\":false},{\"capJavaField\":\"EventTitle\",\"columnComment\":\"活动标题\",\"columnId\":57,\"columnName\":\"event_title\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 11:20:03\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"eventTitle\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":5,\"updateBy\":\"\",\"updateTime\":\"2025-12-03 11:28:30\",\"usableColumn\":false},{\"capJavaField\":\"EventSummary\",\"columnComment\":\"活动详情\",\"columnId\":58,\"columnName\":\"event_summary\",\"columnType\":\"varchar(500)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 11:20:03\",\"dictType\":\"\",\"edit\":true', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 11:50:26', 113);
INSERT INTO `sys_oper_log` VALUES (198, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', '研发部门', '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"events\"}', NULL, 0, NULL, '2025-12-03 11:50:36', 811);
INSERT INTO `sys_oper_log` VALUES (199, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.synchDb()', 'GET', 1, 'admin', '研发部门', '/tool/gen/synchDb/events', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 12:17:22', 407);
INSERT INTO `sys_oper_log` VALUES (200, '代码生成', 3, 'com.ruoyi.generator.controller.GenController.remove()', 'DELETE', 1, 'admin', '研发部门', '/tool/gen/5', '127.0.0.1', '内网IP', '[5]', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 12:18:06', 57);
INSERT INTO `sys_oper_log` VALUES (201, '代码生成', 6, 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', '研发部门', '/tool/gen/importTable', '127.0.0.1', '内网IP', '{\"tables\":\"events\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 12:18:13', 228);
INSERT INTO `sys_oper_log` VALUES (202, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"events\",\"className\":\"Events\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"活动ID\",\"columnId\":67,\"columnName\":\"id\",\"columnType\":\"bigint(20)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 12:18:12\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":7,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"AdminUserId\",\"columnComment\":\"发布管理员ID\",\"columnId\":68,\"columnName\":\"admin_user_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 12:18:12\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"0\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"adminUserId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":7,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"EventTitle\",\"columnComment\":\"活动标题\",\"columnId\":69,\"columnName\":\"event_title\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 12:18:12\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"eventTitle\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":7,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"EventSummary\",\"columnComment\":\"活动详情\",\"columnId\":70,\"columnName\":\"event_summary\",\"columnType\":\"text\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 12:18:12\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"editor\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"0\",\"isPk\":\"0\",\"is', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 12:21:30', 133);
INSERT INTO `sys_oper_log` VALUES (203, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', '研发部门', '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"events\"}', NULL, 0, NULL, '2025-12-03 12:21:35', 633);
INSERT INTO `sys_oper_log` VALUES (204, '求职活动管理', 2, 'com.ruoyi.recruit.controller.EventsController.edit()', 'PUT', 1, 'admin', '研发部门', '/recruit/events', '127.0.0.1', '内网IP', '{\"adminUserId\":101,\"createdAt\":\"2025-12-03\",\"eventEndTime\":\"2025-03-15\",\"eventLocation\":\"北京大学 - 第二教学楼 305 室\",\"eventStartTime\":\"2025-03-15\",\"eventSummary\":\"<p>面向计算机、软件工程及相关专业的应届毕业生，现场接收简历并安排面试。届时会有技术总监分享行业趋势。</p><p><img src=\\\"/dev-api/profile/upload/2025/12/03/v2-70119c437cff012b67f2bcc385f4180e_r_20251203122426A001.jpg\\\"></p>\",\"eventTitle\":\"2025年春季校园招聘宣讲会 - 科技专场\",\"id\":6,\"params\":{},\"targetAudience\":\"2025届毕业生\",\"updatedAt\":\"2025-12-03\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 12:24:28', 114);
INSERT INTO `sys_oper_log` VALUES (205, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"industries\",\"className\":\"TIndustries\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"行业ID\",\"columnId\":65,\"columnName\":\"id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 11:29:32\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"行业名称\",\"columnId\":66,\"columnName\":\"name\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 11:29:32\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"usableColumn\":false}],\"crud\":true,\"functionAuthor\":\"Yihan\",\"functionName\":\"行业信息管理\",\"genPath\":\"/\",\"genType\":\"0\",\"moduleName\":\"recruit\",\"options\":\"{\\\"parentMenuId\\\":2002}\",\"packageName\":\"com.ruoyi.recruit\",\"params\":{\"parentMenuId\":2002},\"parentMenuId\":2002,\"sub\":false,\"tableComment\":\"行业领域字典表\",\"tableId\":6,\"tableName\":\"t_industries\",\"tplCategory\":\"crud\",\"tplWebType\":\"element-plus\",\"tree\":false}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 14:34:36', 5600);
INSERT INTO `sys_oper_log` VALUES (206, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"industries\",\"className\":\"TIndustries\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"行业ID\",\"columnId\":65,\"columnName\":\"id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 11:29:32\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"行业名称\",\"columnId\":66,\"columnName\":\"name\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 11:29:32\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"usableColumn\":false}],\"crud\":true,\"functionAuthor\":\"Yihan\",\"functionName\":\"行业信息管理\",\"genPath\":\"/\",\"genType\":\"0\",\"moduleName\":\"recruit\",\"options\":\"{\\\"parentMenuId\\\":2002}\",\"packageName\":\"com.ruoyi.recruit\",\"params\":{\"parentMenuId\":2002},\"parentMenuId\":2002,\"sub\":false,\"tableComment\":\"行业领域字典表\",\"tableId\":6,\"tableName\":\"t_industries\",\"tplCategory\":\"crud\",\"tplWebType\":\"element-plus\",\"tree\":false}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 14:34:37', 324);
INSERT INTO `sys_oper_log` VALUES (207, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', '研发部门', '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"t_industries\"}', NULL, 0, NULL, '2025-12-03 14:34:45', 588);
INSERT INTO `sys_oper_log` VALUES (208, '行业信息管理', 2, 'com.ruoyi.recruit.controller.TIndustriesController.edit()', 'PUT', 1, 'admin', '研发部门', '/recruit/industries', '127.0.0.1', '内网IP', '{\"id\":14,\"name\":\"快速消费品\",\"params\":{}}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 14:40:14', 109);
INSERT INTO `sys_oper_log` VALUES (209, '代码生成', 6, 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', '研发部门', '/tool/gen/importTable', '127.0.0.1', '内网IP', '{\"tables\":\"t_job_categories\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 14:43:22', 152);
INSERT INTO `sys_oper_log` VALUES (210, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"categories\",\"className\":\"TJobCategories\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"岗位类别ID\",\"columnId\":77,\"columnName\":\"id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 14:43:22\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isPk\":\"1\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"岗位类别名称\",\"columnId\":78,\"columnName\":\"name\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 14:43:22\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"usableColumn\":false}],\"crud\":true,\"functionAuthor\":\"Yihan\",\"functionName\":\"岗位类别管理\",\"genPath\":\"/\",\"genType\":\"0\",\"moduleName\":\"recruit\",\"options\":\"{\\\"parentMenuId\\\":2002}\",\"packageName\":\"com.ruoyi.recruit\",\"params\":{\"parentMenuId\":2002},\"parentMenuId\":2002,\"sub\":false,\"tableComment\":\"岗位职能类别字典表\",\"tableId\":8,\"tableName\":\"t_job_categories\",\"tplCategory\":\"crud\",\"tplWebType\":\"element-plus\",\"tree\":false}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 14:45:41', 95);
INSERT INTO `sys_oper_log` VALUES (211, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', '研发部门', '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"t_job_categories\"}', NULL, 0, NULL, '2025-12-03 14:45:46', 631);
INSERT INTO `sys_oper_log` VALUES (212, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"natures\",\"className\":\"TCompanyNatures\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"企业性质ID\",\"columnId\":79,\"columnName\":\"id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 14:50:18\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2025-12-03 15:39:55\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"企业性质名称\",\"columnId\":80,\"columnName\":\"name\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 14:50:18\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2025-12-03 15:39:55\",\"usableColumn\":false}],\"crud\":true,\"functionAuthor\":\"Yihan\",\"functionName\":\"企业性质管理\",\"genPath\":\"/\",\"genType\":\"0\",\"moduleName\":\"recruit\",\"options\":\"{\\\"parentMenuId\\\":2002}\",\"packageName\":\"com.ruoyi.recruit\",\"params\":{\"parentMenuId\":2002},\"parentMenuId\":2002,\"sub\":false,\"tableComment\":\"企业性质字典表\",\"tableId\":9,\"tableName\":\"t_company_natures\",\"tplCategory\":\"crud\",\"tplWebType\":\"element-plus\",\"tree\":false}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 15:50:20', 1151);
INSERT INTO `sys_oper_log` VALUES (213, '代码生成', 6, 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', '研发部门', '/tool/gen/importTable', '127.0.0.1', '内网IP', '{\"tables\":\"t_company_natures,t_company_scales,tag_categories,tags\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 15:50:46', 310);
INSERT INTO `sys_oper_log` VALUES (214, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"scales\",\"className\":\"TCompanyScales\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"公司规模\",\"columnId\":81,\"columnName\":\"id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":10,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Scale\",\"columnComment\":\"规模范围\",\"columnId\":82,\"columnName\":\"scale\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"scale\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":10,\"updateBy\":\"\",\"usableColumn\":false}],\"crud\":true,\"functionAuthor\":\"Yihan\",\"functionName\":\"规模信息管理\",\"genPath\":\"/\",\"genType\":\"0\",\"moduleName\":\"recruit\",\"options\":\"{\\\"parentMenuId\\\":2002}\",\"packageName\":\"com.ruoyi.recruit\",\"params\":{\"parentMenuId\":2002},\"parentMenuId\":2002,\"sub\":false,\"tableComment\":\"公司规模字典表\",\"tableId\":10,\"tableName\":\"t_company_scales\",\"tplCategory\":\"crud\",\"tplWebType\":\"element-plus\",\"tree\":false}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 15:51:55', 41);
INSERT INTO `sys_oper_log` VALUES (215, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"tagCategories\",\"className\":\"TagCategories\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"分类ID\",\"columnId\":83,\"columnName\":\"id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":11,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Code\",\"columnComment\":\"分类短码\",\"columnId\":84,\"columnName\":\"code\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"1\",\"javaField\":\"code\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":11,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"分类名称\",\"columnId\":85,\"columnName\":\"name\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":11,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Description\",\"columnComment\":\"分类描述\",\"columnId\":86,\"columnName\":\"description\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"0\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 15:53:55', 64);
INSERT INTO `sys_oper_log` VALUES (216, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"tags\",\"className\":\"Tags\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"标签ID\",\"columnId\":89,\"columnName\":\"id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":12,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"标签名称\",\"columnId\":90,\"columnName\":\"name\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":12,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"CategoryId\",\"columnComment\":\"所属分类\",\"columnId\":93,\"columnName\":\"category_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"select\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"categoryId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":12,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"CreatedBy\",\"columnComment\":\"创建人ID\",\"columnId\":92,\"columnName\":\"created_by\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaFi', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 15:56:18', 89);
INSERT INTO `sys_oper_log` VALUES (217, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"natures\",\"className\":\"TCompanyNatures\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"企业性质ID\",\"columnId\":79,\"columnName\":\"id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"企业性质名称\",\"columnId\":80,\"columnName\":\"name\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"usableColumn\":false}],\"crud\":true,\"functionAuthor\":\"Yihan\",\"functionName\":\"企业性质管理\",\"genPath\":\"/\",\"genType\":\"0\",\"moduleName\":\"recruit\",\"options\":\"{\\\"parentMenuId\\\":2002}\",\"packageName\":\"com.ruoyi.recruit\",\"params\":{\"parentMenuId\":2002},\"parentMenuId\":2002,\"sub\":false,\"tableComment\":\"企业性质字典表\",\"tableId\":9,\"tableName\":\"t_company_natures\",\"tplCategory\":\"crud\",\"tplWebType\":\"element-plus\",\"tree\":false}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 15:57:12', 54);
INSERT INTO `sys_oper_log` VALUES (218, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', '研发部门', '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"t_company_scales,tag_categories,tags,t_company_natures\"}', NULL, 0, NULL, '2025-12-03 15:58:09', 1025);
INSERT INTO `sys_oper_log` VALUES (219, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"tags\",\"className\":\"Tags\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"标签ID\",\"columnId\":89,\"columnName\":\"id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":12,\"updateBy\":\"\",\"updateTime\":\"2025-12-03 15:56:18\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"标签名称\",\"columnId\":90,\"columnName\":\"name\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":12,\"updateBy\":\"\",\"updateTime\":\"2025-12-03 15:56:18\",\"usableColumn\":false},{\"capJavaField\":\"CategoryId\",\"columnComment\":\"所属分类\",\"columnId\":93,\"columnName\":\"category_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"categoryId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":12,\"updateBy\":\"\",\"updateTime\":\"2025-12-03 15:56:18\",\"usableColumn\":false},{\"capJavaField\":\"CreatedBy\",\"columnComment\":\"创建人ID\",\"columnId\":92,\"columnName\":\"created_by\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isE', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 16:06:24', 194);
INSERT INTO `sys_oper_log` VALUES (220, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"tags\",\"className\":\"Tags\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"标签ID\",\"columnId\":89,\"columnName\":\"id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":12,\"updateBy\":\"\",\"updateTime\":\"2025-12-03 16:06:23\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"标签名称\",\"columnId\":90,\"columnName\":\"name\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":12,\"updateBy\":\"\",\"updateTime\":\"2025-12-03 16:06:23\",\"usableColumn\":false},{\"capJavaField\":\"CategoryId\",\"columnComment\":\"所属分类\",\"columnId\":93,\"columnName\":\"category_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"categoryId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":12,\"updateBy\":\"\",\"updateTime\":\"2025-12-03 16:06:23\",\"usableColumn\":false},{\"capJavaField\":\"CreatedBy\",\"columnComment\":\"创建人ID\",\"columnId\":92,\"columnName\":\"created_by\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-03 15:50:46\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isE', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-03 16:07:58', 114);
INSERT INTO `sys_oper_log` VALUES (221, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', '研发部门', '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"tags\"}', NULL, 0, NULL, '2025-12-03 16:08:02', 1068);
INSERT INTO `sys_oper_log` VALUES (222, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"recruit/companies/index\",\"createTime\":\"2025-12-03 09:03:42\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2010,\"menuName\":\"企业信息管理\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2001,\"path\":\"companies\",\"perms\":\"recruit:companies:list\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 08:44:31', 67);
INSERT INTO `sys_oper_log` VALUES (223, '代码生成', 6, 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', '研发部门', '/tool/gen/importTable', '127.0.0.1', '内网IP', '{\"tables\":\"applications\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 08:45:09', 180);
INSERT INTO `sys_oper_log` VALUES (224, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"applications\",\"className\":\"Applications\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"投递记录ID\",\"columnId\":94,\"columnName\":\"id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 08:45:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":13,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"JobId\",\"columnComment\":\"投递职位\",\"columnId\":95,\"columnName\":\"job_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 08:45:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"jobId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":13,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"StudentUserId\",\"columnComment\":\"投递人\",\"columnId\":96,\"columnName\":\"student_user_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 08:45:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"1\",\"javaField\":\"studentUserId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":13,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"ResumeId\",\"columnComment\":\"简历\",\"columnId\":97,\"columnName\":\"resume_id\",\"columnType\":\"bigint(20)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 08:45:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"0\",\"isPk\":\"0\",\"isQuery\":\"0\",', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 08:48:49', 114);
INSERT INTO `sys_oper_log` VALUES (225, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', '研发部门', '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"applications\"}', NULL, 0, NULL, '2025-12-04 08:49:51', 600);
INSERT INTO `sys_oper_log` VALUES (226, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"applications\",\"className\":\"Applications\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"投递记录ID\",\"columnId\":94,\"columnName\":\"id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 08:45:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":13,\"updateBy\":\"\",\"updateTime\":\"2025-12-04 08:48:49\",\"usableColumn\":false},{\"capJavaField\":\"JobId\",\"columnComment\":\"投递职位\",\"columnId\":95,\"columnName\":\"job_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 08:45:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"jobId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":13,\"updateBy\":\"\",\"updateTime\":\"2025-12-04 08:48:49\",\"usableColumn\":false},{\"capJavaField\":\"StudentUserId\",\"columnComment\":\"投递人\",\"columnId\":96,\"columnName\":\"student_user_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 08:45:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"1\",\"javaField\":\"studentUserId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":13,\"updateBy\":\"\",\"updateTime\":\"2025-12-04 08:48:49\",\"usableColumn\":false},{\"capJavaField\":\"ResumeId\",\"columnComment\":\"简历\",\"columnId\":97,\"columnName\":\"resume_id\",\"columnType\":\"bigint(20)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 08:45:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 08:57:50', 116);
INSERT INTO `sys_oper_log` VALUES (227, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', '研发部门', '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"applications\"}', NULL, 0, NULL, '2025-12-04 08:57:54', 210);
INSERT INTO `sys_oper_log` VALUES (228, '代码生成', 6, 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', '研发部门', '/tool/gen/importTable', '127.0.0.1', '内网IP', '{\"tables\":\"students\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:06:23', 201);
INSERT INTO `sys_oper_log` VALUES (229, '字典类型', 1, 'com.ruoyi.web.controller.system.SysDictTypeController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/type', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"dictName\":\"性别\",\"dictType\":\"biz_gender\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:08:24', 28);
INSERT INTO `sys_oper_log` VALUES (230, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"男\",\"dictSort\":0,\"dictType\":\"biz_gender\",\"dictValue\":\"0\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:08:36', 25);
INSERT INTO `sys_oper_log` VALUES (231, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', '研发部门', '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"女\",\"dictSort\":1,\"dictType\":\"biz_gender\",\"dictValue\":\"1\",\"listClass\":\"default\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:08:42', 38);
INSERT INTO `sys_oper_log` VALUES (232, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"students\",\"className\":\"Students\",\"columns\":[{\"capJavaField\":\"UserId\",\"columnComment\":\"用户ID\",\"columnId\":101,\"columnName\":\"user_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 09:06:23\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"userId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":14,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"StudentId\",\"columnComment\":\"学号\",\"columnId\":102,\"columnName\":\"student_id\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 09:06:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"studentId\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":14,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"AvatarUrl\",\"columnComment\":\"头像\",\"columnId\":103,\"columnName\":\"avatar_url\",\"columnType\":\"varchar(1024)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 09:06:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"imageUpload\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"avatarUrl\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":14,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"FullName\",\"columnComment\":\"姓名\",\"columnId\":104,\"columnName\":\"full_name\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 09:06:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"i', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:10:37', 153);
INSERT INTO `sys_oper_log` VALUES (233, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"students\",\"className\":\"Students\",\"columns\":[{\"capJavaField\":\"UserId\",\"columnComment\":\"用户ID\",\"columnId\":101,\"columnName\":\"user_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 09:06:23\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"userId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":14,\"updateBy\":\"\",\"updateTime\":\"2025-12-04 09:10:37\",\"usableColumn\":false},{\"capJavaField\":\"AvatarUrl\",\"columnComment\":\"头像\",\"columnId\":103,\"columnName\":\"avatar_url\",\"columnType\":\"varchar(1024)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 09:06:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"imageUpload\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"avatarUrl\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":14,\"updateBy\":\"\",\"updateTime\":\"2025-12-04 09:10:37\",\"usableColumn\":false},{\"capJavaField\":\"FullName\",\"columnComment\":\"姓名\",\"columnId\":104,\"columnName\":\"full_name\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 09:06:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"fullName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":14,\"updateBy\":\"\",\"updateTime\":\"2025-12-04 09:10:37\",\"usableColumn\":false},{\"capJavaField\":\"Gender\",\"columnComment\":\"性别\",\"columnId\":106,\"columnName\":\"gender\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 09:06:23\",\"dictType\":\"biz_gender\",\"edit\"', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:15:04', 103);
INSERT INTO `sys_oper_log` VALUES (234, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', '研发部门', '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"students\"}', NULL, 0, NULL, '2025-12-04 09:15:09', 317);
INSERT INTO `sys_oper_log` VALUES (235, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"students\",\"className\":\"Students\",\"columns\":[{\"capJavaField\":\"UserId\",\"columnComment\":\"用户ID\",\"columnId\":101,\"columnName\":\"user_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 09:06:23\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"userId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":14,\"updateBy\":\"\",\"updateTime\":\"2025-12-04 09:15:04\",\"usableColumn\":false},{\"capJavaField\":\"AvatarUrl\",\"columnComment\":\"头像\",\"columnId\":103,\"columnName\":\"avatar_url\",\"columnType\":\"varchar(1024)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 09:06:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"imageUpload\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"avatarUrl\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":14,\"updateBy\":\"\",\"updateTime\":\"2025-12-04 09:15:04\",\"usableColumn\":false},{\"capJavaField\":\"FullName\",\"columnComment\":\"姓名\",\"columnId\":104,\"columnName\":\"full_name\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 09:06:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"fullName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":14,\"updateBy\":\"\",\"updateTime\":\"2025-12-04 09:15:04\",\"usableColumn\":false},{\"capJavaField\":\"Gender\",\"columnComment\":\"性别\",\"columnId\":106,\"columnName\":\"gender\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 09:06:23\",\"dictType\":\"biz_gender\",\"edit\"', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:19:24', 145);
INSERT INTO `sys_oper_log` VALUES (236, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', '研发部门', '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"students\",\"className\":\"Students\",\"columns\":[{\"capJavaField\":\"UserId\",\"columnComment\":\"用户ID\",\"columnId\":101,\"columnName\":\"user_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 09:06:23\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"1\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"userId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":14,\"updateBy\":\"\",\"updateTime\":\"2025-12-04 09:19:24\",\"usableColumn\":false},{\"capJavaField\":\"AvatarUrl\",\"columnComment\":\"头像\",\"columnId\":103,\"columnName\":\"avatar_url\",\"columnType\":\"varchar(1024)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 09:06:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"imageUpload\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"avatarUrl\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":14,\"updateBy\":\"\",\"updateTime\":\"2025-12-04 09:19:24\",\"usableColumn\":false},{\"capJavaField\":\"FullName\",\"columnComment\":\"姓名\",\"columnId\":104,\"columnName\":\"full_name\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 09:06:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"fullName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":14,\"updateBy\":\"\",\"updateTime\":\"2025-12-04 09:19:24\",\"usableColumn\":false},{\"capJavaField\":\"Gender\",\"columnComment\":\"性别\",\"columnId\":106,\"columnName\":\"gender\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2025-12-04 09:06:23\",\"dictType\":\"biz_gender\",\"edit\"', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:19:43', 93);
INSERT INTO `sys_oper_log` VALUES (237, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', '研发部门', '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"students\"}', NULL, 0, NULL, '2025-12-04 09:20:05', 278);
INSERT INTO `sys_oper_log` VALUES (238, '菜单管理', 1, 'com.ruoyi.web.controller.system.SysMenuController.add()', 'POST', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createBy\":\"admin\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"企业账号审核\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2009,\"path\":\"account-review\",\"routeName\":\"\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:31:26', 409);
INSERT INTO `sys_oper_log` VALUES (239, '菜单管理', 1, 'com.ruoyi.web.controller.system.SysMenuController.add()', 'POST', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createBy\":\"admin\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"岗位审核\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":0,\"path\":\"job-review\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:33:51', 21);
INSERT INTO `sys_oper_log` VALUES (240, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2025-12-04 09:33:51\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2077,\"menuName\":\"岗位审核\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2009,\"path\":\"job-review\",\"perms\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:34:06', 66);
INSERT INTO `sys_oper_log` VALUES (241, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2025-12-01 19:33:15\",\"icon\":\"monitor\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2,\"menuName\":\"系统监控\",\"menuType\":\"M\",\"orderNum\":2,\"params\":{},\"parentId\":0,\"path\":\"monitor\",\"perms\":\"\",\"query\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"1\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:34:21', 27);
INSERT INTO `sys_oper_log` VALUES (242, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2025-12-04 09:33:51\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2077,\"menuName\":\"岗位发布审核\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2009,\"path\":\"job-review\",\"perms\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:35:46', 46);
INSERT INTO `sys_oper_log` VALUES (243, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2025-12-01 19:33:15\",\"icon\":\"monitor\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2,\"menuName\":\"系统监控\",\"menuType\":\"M\",\"orderNum\":6,\"params\":{},\"parentId\":0,\"path\":\"monitor\",\"perms\":\"\",\"query\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"1\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:36:12', 23);
INSERT INTO `sys_oper_log` VALUES (244, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2025-12-04 09:31:26\",\"icon\":\"switch\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2076,\"menuName\":\"企业账号审核\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2009,\"path\":\"account-review\",\"perms\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:39:21', 48);
INSERT INTO `sys_oper_log` VALUES (245, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2025-12-04 09:33:51\",\"icon\":\"edit\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2077,\"menuName\":\"岗位发布审核\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2009,\"path\":\"job-review\",\"perms\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:41:49', 25);
INSERT INTO `sys_oper_log` VALUES (246, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2025-12-04 09:33:51\",\"icon\":\"list\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2077,\"menuName\":\"岗位发布审核\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2009,\"path\":\"job-review\",\"perms\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:41:58', 42);
INSERT INTO `sys_oper_log` VALUES (247, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"recruit/jobs/index\",\"createTime\":\"2025-12-01 20:51:07\",\"icon\":\"star\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2003,\"menuName\":\"岗位管理\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2000,\"path\":\"jobs\",\"perms\":\"recruit:jobs:list\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:42:27', 20);
INSERT INTO `sys_oper_log` VALUES (248, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"recruit/events/index\",\"createTime\":\"2025-12-03 11:51:45\",\"icon\":\"color\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2022,\"menuName\":\"求职活动管理\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2000,\"path\":\"events\",\"perms\":\"recruit:events:list\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:42:49', 21);
INSERT INTO `sys_oper_log` VALUES (249, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"recruit/applications/index\",\"createTime\":\"2025-12-04 08:54:43\",\"icon\":\"more-up\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2064,\"menuName\":\"投递记录\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2000,\"path\":\"applications\",\"perms\":\"recruit:applications:list\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:43:07', 47);
INSERT INTO `sys_oper_log` VALUES (250, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"recruit/companies/index\",\"createTime\":\"2025-12-03 09:03:42\",\"icon\":\"logininfor\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2010,\"menuName\":\"企业信息管理\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2001,\"path\":\"companies\",\"perms\":\"recruit:companies:list\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:43:21', 17);
INSERT INTO `sys_oper_log` VALUES (251, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"recruit/companies/index\",\"createTime\":\"2025-12-03 09:03:42\",\"icon\":\"logininfor\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2010,\"menuName\":\"企业信息管理\",\"menuType\":\"C\",\"orderNum\":2,\"params\":{},\"parentId\":2001,\"path\":\"companies\",\"perms\":\"recruit:companies:list\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:43:30', 21);
INSERT INTO `sys_oper_log` VALUES (252, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"recruit/allAsers/index\",\"createTime\":\"2025-12-03 10:50:15\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2016,\"menuName\":\"账号管理\",\"menuType\":\"C\",\"orderNum\":0,\"params\":{},\"parentId\":2001,\"path\":\"allAsers\",\"perms\":\"recruit:allAsers:list\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:43:36', 45);
INSERT INTO `sys_oper_log` VALUES (253, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"recruit/allAsers/index\",\"createTime\":\"2025-12-03 10:50:15\",\"icon\":\"password\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2016,\"menuName\":\"账号管理\",\"menuType\":\"C\",\"orderNum\":0,\"params\":{},\"parentId\":2001,\"path\":\"allAsers\",\"perms\":\"recruit:allAsers:list\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:43:49', 24);
INSERT INTO `sys_oper_log` VALUES (254, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"recruit/students/index\",\"createTime\":\"2025-12-04 09:16:23\",\"icon\":\"people\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2070,\"menuName\":\"学生信息管理\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2001,\"path\":\"students\",\"perms\":\"recruit:students:list\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:44:04', 42);
INSERT INTO `sys_oper_log` VALUES (255, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2025-12-01 19:33:15\",\"icon\":\"tool\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":3,\"menuName\":\"系统工具\",\"menuType\":\"M\",\"orderNum\":5,\"params\":{},\"parentId\":0,\"path\":\"tool\",\"perms\":\"\",\"query\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"1\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:47:59', 47);
INSERT INTO `sys_oper_log` VALUES (256, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"system/dict/index\",\"createTime\":\"2025-12-01 19:33:15\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":105,\"menuName\":\"字典管理\",\"menuType\":\"C\",\"orderNum\":6,\"params\":{},\"parentId\":1,\"path\":\"dict\",\"perms\":\"system:dict:list\",\"query\":\"\",\"routeName\":\"\",\"status\":\"1\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:48:32', 43);
INSERT INTO `sys_oper_log` VALUES (257, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"system/notice/index\",\"createTime\":\"2025-12-01 19:33:15\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":107,\"menuName\":\"通知公告\",\"menuType\":\"C\",\"orderNum\":8,\"params\":{},\"parentId\":1,\"path\":\"notice\",\"perms\":\"system:notice:list\",\"query\":\"\",\"routeName\":\"\",\"status\":\"1\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:48:49', 35);
INSERT INTO `sys_oper_log` VALUES (258, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"recruit/applications/index\",\"createTime\":\"2025-12-04 08:54:43\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2064,\"menuName\":\"投递记录\",\"menuType\":\"C\",\"orderNum\":0,\"params\":{},\"parentId\":2000,\"path\":\"applications\",\"perms\":\"recruit:applications:list\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:50:07', 42);
INSERT INTO `sys_oper_log` VALUES (259, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"recruit/tags/index\",\"createTime\":\"2025-12-03 15:59:02\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2058,\"menuName\":\"标签库\",\"menuType\":\"C\",\"orderNum\":0,\"params\":{},\"parentId\":2002,\"path\":\"tags\",\"perms\":\"recruit:tags:list\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-12-04 09:50:30', 17);

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, '0', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_post` VALUES (2, 'se', '项目经理', 2, '0', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_post` VALUES (3, 'hr', '人力资源', 3, '0', 'admin', '2025-12-01 19:33:15', '', NULL, '');
INSERT INTO `sys_post` VALUES (4, 'user', '普通员工', 4, '0', 'admin', '2025-12-01 19:33:15', '', NULL, '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', 1, 1, '0', '0', 'admin', '2025-12-01 19:33:15', '', NULL, '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, '2', 0, 0, '0', '0', 'admin', '2025-12-01 19:33:15', 'admin', '2025-12-01 21:11:48', '普通角色');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES (2, 100);
INSERT INTO `sys_role_dept` VALUES (2, 101);
INSERT INTO `sys_role_dept` VALUES (2, 105);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 101);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 103);
INSERT INTO `sys_role_menu` VALUES (2, 104);
INSERT INTO `sys_role_menu` VALUES (2, 105);
INSERT INTO `sys_role_menu` VALUES (2, 106);
INSERT INTO `sys_role_menu` VALUES (2, 107);
INSERT INTO `sys_role_menu` VALUES (2, 108);
INSERT INTO `sys_role_menu` VALUES (2, 109);
INSERT INTO `sys_role_menu` VALUES (2, 110);
INSERT INTO `sys_role_menu` VALUES (2, 111);
INSERT INTO `sys_role_menu` VALUES (2, 112);
INSERT INTO `sys_role_menu` VALUES (2, 113);
INSERT INTO `sys_role_menu` VALUES (2, 114);
INSERT INTO `sys_role_menu` VALUES (2, 115);
INSERT INTO `sys_role_menu` VALUES (2, 116);
INSERT INTO `sys_role_menu` VALUES (2, 117);
INSERT INTO `sys_role_menu` VALUES (2, 500);
INSERT INTO `sys_role_menu` VALUES (2, 501);
INSERT INTO `sys_role_menu` VALUES (2, 1000);
INSERT INTO `sys_role_menu` VALUES (2, 1001);
INSERT INTO `sys_role_menu` VALUES (2, 1002);
INSERT INTO `sys_role_menu` VALUES (2, 1003);
INSERT INTO `sys_role_menu` VALUES (2, 1004);
INSERT INTO `sys_role_menu` VALUES (2, 1005);
INSERT INTO `sys_role_menu` VALUES (2, 1006);
INSERT INTO `sys_role_menu` VALUES (2, 1007);
INSERT INTO `sys_role_menu` VALUES (2, 1008);
INSERT INTO `sys_role_menu` VALUES (2, 1009);
INSERT INTO `sys_role_menu` VALUES (2, 1010);
INSERT INTO `sys_role_menu` VALUES (2, 1011);
INSERT INTO `sys_role_menu` VALUES (2, 1012);
INSERT INTO `sys_role_menu` VALUES (2, 1013);
INSERT INTO `sys_role_menu` VALUES (2, 1014);
INSERT INTO `sys_role_menu` VALUES (2, 1015);
INSERT INTO `sys_role_menu` VALUES (2, 1016);
INSERT INTO `sys_role_menu` VALUES (2, 1017);
INSERT INTO `sys_role_menu` VALUES (2, 1018);
INSERT INTO `sys_role_menu` VALUES (2, 1019);
INSERT INTO `sys_role_menu` VALUES (2, 1020);
INSERT INTO `sys_role_menu` VALUES (2, 1021);
INSERT INTO `sys_role_menu` VALUES (2, 1022);
INSERT INTO `sys_role_menu` VALUES (2, 1023);
INSERT INTO `sys_role_menu` VALUES (2, 1024);
INSERT INTO `sys_role_menu` VALUES (2, 1025);
INSERT INTO `sys_role_menu` VALUES (2, 1026);
INSERT INTO `sys_role_menu` VALUES (2, 1027);
INSERT INTO `sys_role_menu` VALUES (2, 1028);
INSERT INTO `sys_role_menu` VALUES (2, 1029);
INSERT INTO `sys_role_menu` VALUES (2, 1030);
INSERT INTO `sys_role_menu` VALUES (2, 1031);
INSERT INTO `sys_role_menu` VALUES (2, 1032);
INSERT INTO `sys_role_menu` VALUES (2, 1033);
INSERT INTO `sys_role_menu` VALUES (2, 1034);
INSERT INTO `sys_role_menu` VALUES (2, 1035);
INSERT INTO `sys_role_menu` VALUES (2, 1036);
INSERT INTO `sys_role_menu` VALUES (2, 1037);
INSERT INTO `sys_role_menu` VALUES (2, 1038);
INSERT INTO `sys_role_menu` VALUES (2, 1039);
INSERT INTO `sys_role_menu` VALUES (2, 1040);
INSERT INTO `sys_role_menu` VALUES (2, 1041);
INSERT INTO `sys_role_menu` VALUES (2, 1042);
INSERT INTO `sys_role_menu` VALUES (2, 1043);
INSERT INTO `sys_role_menu` VALUES (2, 1044);
INSERT INTO `sys_role_menu` VALUES (2, 1045);
INSERT INTO `sys_role_menu` VALUES (2, 1046);
INSERT INTO `sys_role_menu` VALUES (2, 1047);
INSERT INTO `sys_role_menu` VALUES (2, 1048);
INSERT INTO `sys_role_menu` VALUES (2, 1049);
INSERT INTO `sys_role_menu` VALUES (2, 1050);
INSERT INTO `sys_role_menu` VALUES (2, 1051);
INSERT INTO `sys_role_menu` VALUES (2, 1052);
INSERT INTO `sys_role_menu` VALUES (2, 1053);
INSERT INTO `sys_role_menu` VALUES (2, 1054);
INSERT INTO `sys_role_menu` VALUES (2, 1055);
INSERT INTO `sys_role_menu` VALUES (2, 1056);
INSERT INTO `sys_role_menu` VALUES (2, 1057);
INSERT INTO `sys_role_menu` VALUES (2, 1058);
INSERT INTO `sys_role_menu` VALUES (2, 1059);
INSERT INTO `sys_role_menu` VALUES (2, 1060);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `pwd_update_date` datetime NULL DEFAULT NULL COMMENT '密码最后更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 103, 'admin', '若依', '00', 'ry@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2025-12-04 08:42:29', '2025-12-01 19:33:15', 'admin', '2025-12-01 19:33:15', '', NULL, '管理员');
INSERT INTO `sys_user` VALUES (2, 105, 'ry', '若依', '00', 'ry@qq.com', '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2025-12-01 19:33:15', '2025-12-01 19:33:15', 'admin', '2025-12-01 19:33:15', '', NULL, '测试员');

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (1, 1);
INSERT INTO `sys_user_post` VALUES (2, 2);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);

