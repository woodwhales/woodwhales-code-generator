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
CREATE DATABASE IF NOT EXISTS `woodwhales_code_generator` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `woodwhales_code_generator`;

-- 导出  表 woodwhales_code_generator.code_list_page_config 结构
CREATE TABLE IF NOT EXISTS `code_list_page_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '模板配置表主键',
  `config_name` varchar(100) NOT NULL COMMENT '配置名称',
  `description` varchar(300) DEFAULT NULL COMMENT '备注',
  `code_navigation_config_id` int(10) unsigned NOT NULL COMMENT '导航配置表主键',
  `config_content` text COMMENT '配置内容：json字符串格式',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态码：-1 删除，0 启用，1 禁用',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模板配置表';

-- 正在导出表  woodwhales_code_generator.code_list_page_config 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `code_list_page_config` DISABLE KEYS */;
INSERT INTO `code_list_page_config` (`id`, `config_name`, `description`, `code_navigation_config_id`, `config_content`, `status`, `gmt_created`, `gmt_modified`) VALUES
	(3, '新闻表配置', '新闻表配置备注', 1, '{"navName":"新闻表","searchInputs":[{"name":"id","id":"id","placeholder":"请输入id","sort":1},{"name":"code","id":"code","placeholder":"请输入code","sort":2},{"name":"newsType","id":"newsType","placeholder":"请输入newsType","sort":3}],"tableConfig":{"requestUrl":"news/","cols":[{"field":"id","title":"主键","width":null,"sort":1},{"field":"code","title":"新闻编号","width":null,"sort":2},{"field":"title","title":"新闻标题名","width":null,"sort":3},{"field":"newsType","title":"新闻类型：1-国内新闻，2-国外新闻","width":null,"sort":4},{"field":"content","title":"新闻内容","width":null,"sort":5},{"field":"publishTime","title":"新闻发布时间","width":null,"sort":6},{"field":"fromMedia","title":"媒体","width":null,"sort":7},{"field":"fromUrl","title":"源文链接","width":null,"sort":8},{"field":"gmtCreated","title":"创建时间","width":null,"sort":9},{"field":"gmtModified","title":"更新时间","width":null,"sort":10}]}}', 0, '2020-09-08 23:14:50', '2020-09-08 23:14:50'),
	(4, '列表配置2', '列表配置2', 1, '{"navName":"权限表","searchInputs":[{"name":"id","id":"id","placeholder":"请输入id","sort":0},{"name":"code","id":"code","placeholder":"请输入code","sort":0}],"tableConfig":{"requestUrl":"/permission/","cols":[{"field":"id","title":"主键","width":null,"sort":1},{"field":"code","title":"权限编号","width":null,"sort":2},{"field":"name","title":"权限名","width":null,"sort":3},{"field":"description","title":"权限说明","width":null,"sort":4},{"field":"gmtCreated","title":"创建时间","width":null,"sort":5},{"field":"gmtModified","title":"更新时间","width":null,"sort":6}]}}', 0, '2020-09-12 15:41:38', '2020-09-12 15:41:38');
/*!40000 ALTER TABLE `code_list_page_config` ENABLE KEYS */;

-- 导出  表 woodwhales_code_generator.code_navigation_config 结构
CREATE TABLE IF NOT EXISTS `code_navigation_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '导航配置表主键',
  `config_name` varchar(100) NOT NULL COMMENT '配置名称',
  `description` varchar(300) DEFAULT NULL COMMENT '备注',
  `config_content` text NOT NULL,
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态码：-1 删除，0 启用，1 禁用',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='导航配置表';

-- 正在导出表  woodwhales_code_generator.code_navigation_config 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `code_navigation_config` DISABLE KEYS */;
INSERT INTO `code_navigation_config` (`id`, `config_name`, `description`, `config_content`, `status`, `gmt_created`, `gmt_modified`) VALUES
	(1, '菜单名称', '菜单备注', '[{"tab":{"addTabTitle":"权限","addTabUrl":"2/2"},"cite":{"name":"权限"},"sort":1},{"tab":{"addTabTitle":"权限","addTabUrl":"2/2"},"cite":{"name":"权限"},"sort":2}]', 0, '2020-09-06 21:30:20', '2020-09-06 21:30:20');
/*!40000 ALTER TABLE `code_navigation_config` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
