/*
 Navicat Premium Data Transfer

 Source Server         : localhost-root-root1234
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : woodwhales_code_generator

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 08/10/2020 22:47:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for code_list_page_config
-- ----------------------------
DROP TABLE IF EXISTS `code_list_page_config`;
CREATE TABLE `code_list_page_config`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '模板配置表主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置名称',
  `description` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `code_navigation_config_id` int(10) UNSIGNED NOT NULL COMMENT '导航配置表主键',
  `db_table_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据库表名称',
  `config_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '配置内容：json字符串格式',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态码：-1 删除，0 启用，1 禁用',
  `gmt_created` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_code_navigation_config_id`(`code_navigation_config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '模板配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of code_list_page_config
-- ----------------------------
INSERT INTO `code_list_page_config` VALUES (1, 'ncov_news', 'ncov_news', 1, 'ncov_news', '{\"navName\":\"新闻表\",\"dbTableName\":\"ncov_news\",\"searchInputs\":[{\"name\":\"id\",\"id\":\"id\",\"placeholder\":\"请输入id\",\"sort\":1},{\"name\":\"code\",\"id\":\"code\",\"placeholder\":\"请输入code\",\"sort\":2},{\"name\":\"title\",\"id\":\"title\",\"placeholder\":\"请输入title\",\"sort\":3}],\"tableConfig\":{\"requestUrl\":\"/news/\",\"cols\":[{\"field\":\"id\",\"title\":\"主键\",\"sort\":1},{\"field\":\"code\",\"title\":\"新闻编号\",\"sort\":2},{\"field\":\"title\",\"title\":\"新闻标题名\",\"sort\":3},{\"field\":\"newsType\",\"title\":\"新闻类型\",\"sort\":4},{\"field\":\"content\",\"title\":\"新闻内容\",\"sort\":5},{\"field\":\"publishTime\",\"title\":\"新闻发布时间\",\"sort\":6},{\"field\":\"fromMedia\",\"title\":\"媒体\",\"sort\":7},{\"field\":\"fromUrl\",\"title\":\"源文链接\",\"sort\":8},{\"field\":\"gmtCreated\",\"title\":\"创建时间\",\"sort\":9},{\"field\":\"gmtModified\",\"title\":\"更新时间\",\"sort\":10}]}}', 0, '2020-10-08 19:46:36', '2020-10-08 19:46:36');
INSERT INTO `code_list_page_config` VALUES (2, 'ncov_permission', 'ncov_permission', 1, 'ncov_permission', '{\"navName\":\"权限表\",\"dbTableName\":\"ncov_permission\",\"searchInputs\":[{\"name\":\"id\",\"id\":\"id\",\"placeholder\":\"请输入id\",\"sort\":1},{\"name\":\"code\",\"id\":\"code\",\"placeholder\":\"请输入code\",\"sort\":2},{\"name\":\"name\",\"id\":\"name\",\"placeholder\":\"请输入name\",\"sort\":3}],\"tableConfig\":{\"requestUrl\":\"/permission/\",\"cols\":[{\"field\":\"id\",\"title\":\"主键\",\"sort\":1},{\"field\":\"code\",\"title\":\"权限编号\",\"sort\":2},{\"field\":\"name\",\"title\":\"权限名\",\"sort\":3},{\"field\":\"description\",\"title\":\"权限说明\",\"sort\":4},{\"field\":\"gmtCreated\",\"title\":\"创建时间\",\"sort\":5},{\"field\":\"gmtModified\",\"title\":\"更新时间\",\"sort\":6}]}}', 0, '2020-10-08 21:45:53', '2020-10-08 21:45:53');

-- ----------------------------
-- Table structure for code_navigation_config
-- ----------------------------
DROP TABLE IF EXISTS `code_navigation_config`;
CREATE TABLE `code_navigation_config`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '导航配置表主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置名称',
  `description` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `config_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态码：-1 删除，0 启用，1 禁用',
  `gmt_created` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '导航配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of code_navigation_config
-- ----------------------------
INSERT INTO `code_navigation_config` VALUES (1, '2019_ncov菜单配置', '2019_ncov菜单配置', '[{\"dbTableName\":\"ncov_news\",\"tab\":{\"addTabTitle\":\"ncov_news\",\"addTabUrl\":\"/custom/list/\"},\"cite\":{\"name\":\"ncov_news\"},\"sort\":1},{\"dbTableName\":\"ncov_permission\",\"tab\":{\"addTabTitle\":\"ncov_permission\",\"addTabUrl\":\"/custom/list/\"},\"cite\":{\"name\":\"ncov_permission\"},\"sort\":2},{\"dbTableName\":\"ncov_production_info\",\"tab\":{\"addTabTitle\":\"ncov_production_info\",\"addTabUrl\":\"/custom/list/\"},\"cite\":{\"name\":\"ncov_production_info\"},\"sort\":3},{\"dbTableName\":\"ncov_real_time_data\",\"tab\":{\"addTabTitle\":\"ncov_real_time_data\",\"addTabUrl\":\"/custom/list/\"},\"cite\":{\"name\":\"ncov_real_time_data\"},\"sort\":4},{\"dbTableName\":\"ncov_role\",\"tab\":{\"addTabTitle\":\"ncov_role\",\"addTabUrl\":\"/custom/list/\"},\"cite\":{\"name\":\"ncov_role\"},\"sort\":5},{\"dbTableName\":\"ncov_role_permission\",\"tab\":{\"addTabTitle\":\"ncov_role_permission\",\"addTabUrl\":\"/custom/list/\"},\"cite\":{\"name\":\"ncov_role_permission\"},\"sort\":6},{\"dbTableName\":\"ncov_user\",\"tab\":{\"addTabTitle\":\"ncov_user\",\"addTabUrl\":\"/custom/list/\"},\"cite\":{\"name\":\"ncov_user\"},\"sort\":7},{\"dbTableName\":\"ncov_user_permission\",\"tab\":{\"addTabTitle\":\"ncov_user_permission\",\"addTabUrl\":\"/custom/list/\"},\"cite\":{\"name\":\"ncov_user_permission\"},\"sort\":8},{\"dbTableName\":\"ncov_user_role\",\"tab\":{\"addTabTitle\":\"ncov_user_role\",\"addTabUrl\":\"/custom/list/\"},\"cite\":{\"name\":\"ncov_user_role\"},\"sort\":9}]', 0, '2020-10-08 19:45:40', '2020-10-08 19:45:40');

SET FOREIGN_KEY_CHECKS = 1;
