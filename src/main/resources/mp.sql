/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50522
Source Host           : localhost:3306
Source Database       : mp

Target Server Type    : MYSQL
Target Server Version : 50522
File Encoding         : 65001

Date: 2019-09-28 00:57:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `question`
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `description` text,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `comment_count` int(11) DEFAULT '0',
  `view_count` int(11) DEFAULT '0',
  `like_count` int(11) DEFAULT '0',
  `tag` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES ('1', 'sdf', 'aaddd', '1569598905939', '1569598905939', '1', null, null, null, 's');
INSERT INTO `question` VALUES ('2', 'asdfffffffffffffff', 'asdssffff', '1569598925579', '1569598925579', '1', null, null, null, 'f');
INSERT INTO `question` VALUES ('3', '刀具', '耐磨性...', '1569600835224', '1569600835224', '1', '0', '0', '0', '刀');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(100) DEFAULT NULL,
  `avatar_url` varchar(100) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `token` varchar(36) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '50758458', 'https://avatars2.githubusercontent.com/u/50758458?v=4', 'showlin', 'aaf1d70a-7f0d-426b-aa6b-47f3f7b4fd99', '1569597618529', '1569599672972');
