# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 192.168.1.163 (MySQL 5.7.23)
# Database: b_project_db
# Generation Time: 2018-11-16 11:42:54 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table admin_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `admin_user`;

CREATE TABLE `admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(45) DEFAULT NULL,
  `pw` varchar(45) DEFAULT NULL,
  `power` char(5) DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table b_project
# ------------------------------------------------------------

DROP TABLE IF EXISTS `b_project`;

CREATE TABLE `b_project` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `title` varchar(11) DEFAULT NULL COMMENT '标题',
  `details` text COMMENT '详细信息',
  `province` char(10) DEFAULT NULL COMMENT '省份编码',
  `city` char(10) DEFAULT NULL COMMENT '城市编码',
  `district` char(10) DEFAULT NULL COMMENT '区域编码',
  `audit_status` char(4) DEFAULT NULL COMMENT '审核状态: 0-未审核 1-审核未通过，2-审核通过',
  `send_status` char(4) DEFAULT NULL COMMENT '是否上架0-未上架1-上架',
  `b_project_type_id` int(11) DEFAULT NULL COMMENT '类型id',
  `contacts_user` varchar(11) DEFAULT NULL COMMENT '联系人姓名',
  `contacts_mobile` varchar(11) DEFAULT NULL COMMENT '联系人手机号',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `change_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table b_project_audit_record
# ------------------------------------------------------------

DROP TABLE IF EXISTS `b_project_audit_record`;

CREATE TABLE `b_project_audit_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `b_project_id` int(11) DEFAULT NULL COMMENT '项目id',
  `admin_user_id` int(11) DEFAULT NULL COMMENT '审核人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `audit_details` varchar(11) DEFAULT NULL COMMENT '审核描述',
  `audit_status` char(11) DEFAULT NULL COMMENT '审核状态: 0-未审核 1-审核未通过，2-审核通过',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table b_project_type
# ------------------------------------------------------------

DROP TABLE IF EXISTS `b_project_type`;

CREATE TABLE `b_project_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(45) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL COMMENT '0 - 为大类\n-1 为子类菜单',
  `img` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商业项目类型';



# Dump of table pay_vip_record
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pay_vip_record`;

CREATE TABLE `pay_vip_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `total_price` int(11) DEFAULT NULL COMMENT '总金额',
  `pay_price` int(11) DEFAULT NULL COMMENT '实际支付金额',
  `doc_no` varchar(11) DEFAULT NULL COMMENT '本地账单',
  `doc_no_t` varchar(11) DEFAULT NULL COMMENT '第三方账单',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
  `doc_status` char(4) DEFAULT NULL COMMENT '订单状态0-未支付，1-已支付，2-用户取消，3-超时，4-其他',
  `doc_details` varchar(100) DEFAULT NULL COMMENT '订单详情',
  `pay_type` char(5) DEFAULT '0' COMMENT '支付类型0-微信支付',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table vip_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `vip_user`;

CREATE TABLE `vip_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(45) DEFAULT NULL COMMENT '账号',
  `pw` varchar(45) DEFAULT NULL COMMENT '密码',
  `vip_end_date` datetime DEFAULT NULL COMMENT 'vip结束时间',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `id_card` varchar(50) DEFAULT NULL COMMENT '身份证号',
  `name` varchar(10) DEFAULT NULL COMMENT '姓名',
  `nice_name` varchar(10) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(100) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `vip_user` WRITE;
/*!40000 ALTER TABLE `vip_user` DISABLE KEYS */;

INSERT INTO `vip_user` (`id`, `account`, `pw`, `vip_end_date`, `create_date`, `mobile`, `email`, `id_card`, `name`, `nice_name`, `head_img`)
VALUES
	(1,'123456','12345678',NULL,NULL,NULL,NULL,NULL,NULL,'我是大神',NULL);

/*!40000 ALTER TABLE `vip_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table wx_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `wx_user`;

CREATE TABLE `wx_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
