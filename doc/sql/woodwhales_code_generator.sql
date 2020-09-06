-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.27 - MySQL Community Server (GPL)
-- 服务器OS:                        Win64
-- HeidiSQL 版本:                  10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for woodwhales_code_generator
CREATE DATABASE IF NOT EXISTS `woodwhales_code_generator` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `woodwhales_code_generator`;

-- Dumping structure for table woodwhales_code_generator.code_list_page_config
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

-- Dumping data for table woodwhales_code_generator.code_list_page_config: ~2 rows (大约)
/*!40000 ALTER TABLE `code_list_page_config` DISABLE KEYS */;
INSERT INTO `code_list_page_config` (`id`, `config_name`, `description`, `code_navigation_config_id`, `config_content`, `status`, `gmt_created`, `gmt_modified`) VALUES
	(1, '模板1', '111', 1, '', 0, '2020-09-05 14:51:33', '2020-09-05 14:51:34'),
	(2, '模板2', '222', 1, '', 0, '2020-09-05 16:19:49', '2020-09-05 16:19:49');
/*!40000 ALTER TABLE `code_list_page_config` ENABLE KEYS */;

-- Dumping structure for table woodwhales_code_generator.code_navigation_config
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

-- Dumping data for table woodwhales_code_generator.code_navigation_config: ~0 rows (大约)
/*!40000 ALTER TABLE `code_navigation_config` DISABLE KEYS */;
INSERT INTO `code_navigation_config` (`id`, `config_name`, `description`, `config_content`, `status`, `gmt_created`, `gmt_modified`) VALUES
	(1, '菜单名称', '菜单备注', '[{"tab":{"addTabTitle":"权限","addTabUrl":"2/2"},"cite":{"name":"权限"},"sort":2},{"tab":{"addTabTitle":"权限","addTabUrl":"2/2"},"cite":{"name":"权限"},"sort":2}]', 0, '2020-09-06 21:30:20', '2020-09-06 21:30:20');
/*!40000 ALTER TABLE `code_navigation_config` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
