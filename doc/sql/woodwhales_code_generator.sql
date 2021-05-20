-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.27 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 woodwhales_code_generator 的数据库结构
CREATE DATABASE IF NOT EXISTS `woodwhales_code_generator` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `woodwhales_code_generator`;

-- 导出  表 woodwhales_code_generator.code_database_config 结构
CREATE TABLE IF NOT EXISTS `code_database_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '数据库连接配置表主键',
  `config_code` varchar(80) NOT NULL COMMENT '配置编号',
  `config_name` varchar(150) NOT NULL COMMENT '配置名称',
  `config_memo` varchar(300) DEFAULT NULL COMMENT '配置备注',
  `database_type` varchar(50) NOT NULL COMMENT '数据库类型',
  `database_driver_class_name` varchar(50) NOT NULL COMMENT '驱动类名',
  `config_ip` varchar(50) NOT NULL COMMENT 'ip地址',
  `config_port` int(10) unsigned NOT NULL COMMENT '端口号',
  `config_sid` varchar(50) DEFAULT NULL COMMENT 'sid',
  `config_username` varchar(50) NOT NULL COMMENT '用户名称',
  `config_password` varchar(50) NOT NULL COMMENT '用户密码',
  `config_schema` varchar(50) DEFAULT NULL COMMENT 'schema',
  `generate_code_content` varchar(1024) NOT NULL COMMENT '代码配置',
  `generate_file_content` varchar(1024) NOT NULL COMMENT '文件配置',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态码：-1 删除，0 启用，1 禁用',
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `config_code` (`config_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据库连接配置表';

-- 正在导出表  woodwhales_code_generator.code_database_config 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `code_database_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `code_database_config` ENABLE KEYS */;

-- 导出  表 woodwhales_code_generator.code_list_page_config 结构
CREATE TABLE IF NOT EXISTS `code_list_page_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '模板配置表主键',
  `config_name` varchar(100) NOT NULL COMMENT '配置名称',
  `description` varchar(300) DEFAULT NULL COMMENT '备注',
  `code_navigation_config_id` int(10) unsigned NOT NULL COMMENT '导航配置表主键',
  `db_table_name` varchar(100) DEFAULT NULL COMMENT '数据库表名称',
  `config_content` text COMMENT '配置内容：json字符串格式',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态码：-1 删除，0 启用，1 禁用',
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_code_navigation_config_id` (`code_navigation_config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='模板配置表';

-- 正在导出表  woodwhales_code_generator.code_list_page_config 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `code_list_page_config` DISABLE KEYS */;
INSERT INTO `code_list_page_config` (`id`, `config_name`, `description`, `code_navigation_config_id`, `db_table_name`, `config_content`, `status`, `gmt_created`, `gmt_modified`) VALUES
	(1, 'ncov_news', 'ncov_news', 1, 'ncov_news', '{"navName":"新闻表","dbTableName":"ncov_news","searchInputs":[{"name":"id","id":"id","placeholder":"请输入id","sort":1},{"name":"code","id":"code","placeholder":"请输入code","sort":2},{"name":"title","id":"title","placeholder":"请输入title","sort":3}],"tableConfig":{"requestUrl":"/news/","cols":[{"field":"id","title":"主键","sort":1},{"field":"code","title":"新闻编号","sort":2},{"field":"title","title":"新闻标题名","sort":3},{"field":"newsType","title":"新闻类型","sort":4},{"field":"content","title":"新闻内容","sort":5},{"field":"publishTime","title":"新闻发布时间","sort":6},{"field":"fromMedia","title":"媒体","sort":7},{"field":"fromUrl","title":"源文链接","sort":8},{"field":"gmtCreated","title":"创建时间","sort":9},{"field":"gmtModified","title":"更新时间","sort":10}]}}', 0, '2020-10-08 19:46:36', '2020-10-08 19:46:36'),
	(2, 'ncov_permission', 'ncov_permission', 1, 'ncov_permission', '{"navName":"权限表","dbTableName":"ncov_permission","searchInputs":[{"name":"id","id":"id","placeholder":"请输入id","sort":1},{"name":"code","id":"code","placeholder":"请输入code","sort":2},{"name":"name","id":"name","placeholder":"请输入name","sort":3}],"tableConfig":{"requestUrl":"/permission/","cols":[{"field":"id","title":"主键","sort":1},{"field":"code","title":"权限编号","sort":2},{"field":"name","title":"权限名","sort":3},{"field":"description","title":"权限说明","sort":4},{"field":"gmtCreated","title":"创建时间","sort":5},{"field":"gmtModified","title":"更新时间","sort":6}]}}', 0, '2020-10-08 21:45:53', '2020-10-08 21:45:53');
/*!40000 ALTER TABLE `code_list_page_config` ENABLE KEYS */;

-- 导出  表 woodwhales_code_generator.code_navigation_config 结构
CREATE TABLE IF NOT EXISTS `code_navigation_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '导航配置表主键',
  `config_name` varchar(100) NOT NULL COMMENT '配置名称',
  `description` varchar(300) DEFAULT NULL COMMENT '备注',
  `config_content` text NOT NULL,
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态码：-1 删除，0 启用，1 禁用',
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='导航配置表';

-- 正在导出表  woodwhales_code_generator.code_navigation_config 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `code_navigation_config` DISABLE KEYS */;
INSERT INTO `code_navigation_config` (`id`, `config_name`, `description`, `config_content`, `status`, `gmt_created`, `gmt_modified`) VALUES
	(1, '2019_ncov菜单配置', '2019_ncov菜单配置', '[{"dbTableName":"ncov_news","tab":{"addTabTitle":"ncov_news","addTabUrl":"/custom/list/"},"cite":{"name":"ncov_news"},"sort":1},{"dbTableName":"ncov_permission","tab":{"addTabTitle":"ncov_permission","addTabUrl":"/custom/list/"},"cite":{"name":"ncov_permission"},"sort":2},{"dbTableName":"ncov_production_info","tab":{"addTabTitle":"ncov_production_info","addTabUrl":"/custom/list/"},"cite":{"name":"ncov_production_info"},"sort":3},{"dbTableName":"ncov_real_time_data","tab":{"addTabTitle":"ncov_real_time_data","addTabUrl":"/custom/list/"},"cite":{"name":"ncov_real_time_data"},"sort":4},{"dbTableName":"ncov_role","tab":{"addTabTitle":"ncov_role","addTabUrl":"/custom/list/"},"cite":{"name":"ncov_role"},"sort":5},{"dbTableName":"ncov_role_permission","tab":{"addTabTitle":"ncov_role_permission","addTabUrl":"/custom/list/"},"cite":{"name":"ncov_role_permission"},"sort":6},{"dbTableName":"ncov_user","tab":{"addTabTitle":"ncov_user","addTabUrl":"/custom/list/"},"cite":{"name":"ncov_user"},"sort":7},{"dbTableName":"ncov_user_permission","tab":{"addTabTitle":"ncov_user_permission","addTabUrl":"/custom/list/"},"cite":{"name":"ncov_user_permission"},"sort":8},{"dbTableName":"ncov_user_role","tab":{"addTabTitle":"ncov_user_role","addTabUrl":"/custom/list/"},"cite":{"name":"ncov_user_role"},"sort":9}]', 0, '2020-10-08 19:45:40', '2020-10-08 19:45:40');
/*!40000 ALTER TABLE `code_navigation_config` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
