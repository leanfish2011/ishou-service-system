drop database if exists ishou_system; -- 直接删除数据库，不提醒
create database ishou_system; -- 创建数据库
use ishou_system; -- 选择数据库

DROP TABLE IF EXISTS `site_sub`;
CREATE TABLE `site_sub` (
  `id` varchar(60) NOT NULL COMMENT '主键',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页网站订阅表';
