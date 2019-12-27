/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : hcms

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2019-12-26 14:54:08
*/


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_article_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_article_data`;
CREATE TABLE `sys_article_data` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `title` varchar(100) NOT NULL DEFAULT '' COMMENT '文章资料标题',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '类型',
  `description` varchar(1000) NOT NULL DEFAULT '' COMMENT '描述 长度1000',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT '' COMMENT '创建人id',
  `edit_time` datetime NOT NULL COMMENT '更新时间',
  `editor_id` varchar(32) DEFAULT '' COMMENT '修改人id',
  `data_status` int(1) NOT NULL DEFAULT '1' COMMENT '1:可用 0：不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='文章资料 带附件 分类查询 可存储一切文件';

-- ----------------------------
-- Records of sys_article_data
-- ----------------------------
INSERT INTO `sys_article_data` VALUES ('01880a577e7e40b3ad00b8c12faa7874', 'Chome调试工具及渲染机制', '8', '***-Chome调试工具及渲染机制（第五期）', '2019-12-20 18:03:03', '3e79872d256a4ccca05fc598bfb6fa46', '2019-12-20 18:07:37', '3e79872d256a4ccca05fc598bfb6fa46', '1');

-- ----------------------------
-- Table structure for sys_article_data_files
-- ----------------------------
DROP TABLE IF EXISTS `sys_article_data_files`;
CREATE TABLE `sys_article_data_files` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `sys_article_data_id` varchar(32) NOT NULL DEFAULT '' COMMENT '文章资料id',
  `source_name` varchar(100) NOT NULL DEFAULT '' COMMENT '原文件名称',
  `target_name` varchar(40) NOT NULL DEFAULT '' COMMENT '新文件名称UUID 库中唯一',
  `preview_name` varchar(40) DEFAULT '' COMMENT '文档类文件 转换 另存为pdf预览文件 存储到本字段',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT '' COMMENT '创建人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='文章资料附件表';

-- ----------------------------
-- Records of sys_article_data_files
-- ----------------------------

-- ----------------------------
-- Table structure for sys_data_source
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_source`;
CREATE TABLE `sys_data_source` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键id',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '数据库名称大小写一致',
  `url` varchar(500) NOT NULL DEFAULT '' COMMENT '数据源连接url 阿里德鲁伊 可自动匹配driverClassName',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '数据库用户名',
  `password` varchar(50) NOT NULL DEFAULT '' COMMENT '数据库密码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT '' COMMENT '创建人id',
  `edit_time` datetime NOT NULL COMMENT '更新时间',
  `editor_id` varchar(32) DEFAULT '' COMMENT '修改人id',
  `data_status` int(1) NOT NULL DEFAULT '1' COMMENT '1:可用 0：不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='数据源配置表';

-- ----------------------------
-- Records of sys_data_source
-- ----------------------------

-- ----------------------------
-- Table structure for sys_data_source_files
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_source_files`;
CREATE TABLE `sys_data_source_files` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `sys_data_source_id` varchar(32) NOT NULL DEFAULT '' COMMENT '数据源id',
  `source_name` varchar(100) NOT NULL DEFAULT '' COMMENT '原文件名称',
  `target_name` varchar(40) NOT NULL DEFAULT '' COMMENT '新文件名称UUID 库中唯一',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT '' COMMENT '创建人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='数据源sql文件表';

-- ----------------------------
-- Records of sys_data_source_files
-- ----------------------------

-- ----------------------------
-- Table structure for sys_enum
-- ----------------------------
DROP TABLE IF EXISTS `sys_enum`;
CREATE TABLE `sys_enum` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `key` int(1) NOT NULL DEFAULT '0' COMMENT '枚举key值',
  `value` varchar(100) NOT NULL DEFAULT '' COMMENT '枚举名称',
  `type` varchar(100) NOT NULL DEFAULT '' COMMENT '类别',
  `description` varchar(200) DEFAULT '' COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT '' COMMENT '创建人id',
  `edit_time` datetime NOT NULL COMMENT '更新时间',
  `editor_id` varchar(32) DEFAULT '' COMMENT '修改人id',
  `data_status` int(1) NOT NULL DEFAULT '1' COMMENT '1:可用 0：不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统枚举表';

-- ----------------------------
-- Records of sys_enum
-- ----------------------------
INSERT INTO `sys_enum` VALUES ('132', '1', '目录', 'menu_type', null, '2019-10-30 09:58:00', '', '2019-10-30 09:58:00', '', '1');
INSERT INTO `sys_enum` VALUES ('133', '2', '菜单', 'menu_type', null, '2019-10-30 09:58:00', '', '2019-11-19 18:22:19', 'user_001', '1');
INSERT INTO `sys_enum` VALUES ('134', '3', '按钮/接口', 'menu_type', null, '2019-10-30 09:58:00', '', '2019-11-19 18:22:15', 'user_001', '1');
INSERT INTO `sys_enum` VALUES ('8e69d7ada4e344218358fe78e072c545', '6', '开发规范', 'sys_article_data_type', null, '2019-11-12 18:03:25', 'user_001', '2019-11-19 18:33:50', 'user_001', '1');
INSERT INTO `sys_enum` VALUES ('9c47551d12f74f0faeef136d3a780d4a', '8', '技术分享', 'sys_article_data_type', '', '2019-11-12 18:15:01', 'user_001', '2019-11-19 18:33:56', 'user_001', '1');
INSERT INTO `sys_enum` VALUES ('b2861fcb1c6b416dacab2d6edc5a32ac', '7', '共享空间', 'sys_article_data_type', null, '2019-11-12 18:03:20', 'user_001', '2019-11-19 18:33:53', 'user_001', '1');
INSERT INTO `sys_enum` VALUES ('c4fd48454368473c81b760cd353bd096', '5', '开发软件', 'sys_article_data_type', null, '2019-11-12 18:02:41', 'user_001', '2019-11-19 18:33:46', 'user_001', '1');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `user_account` varchar(50) DEFAULT '' COMMENT '用户登录名',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户姓名',
  `product` varchar(30) NOT NULL DEFAULT '' COMMENT '产品名称/业务名称',
  `operation` varchar(30) NOT NULL DEFAULT '' COMMENT '操作描述',
  `method` varchar(200) NOT NULL DEFAULT '' COMMENT '方法全路径包括包名',
  `params` varchar(2000) DEFAULT '' COMMENT '接受参数',
  `ip` varchar(15) DEFAULT '' COMMENT '请求ip',
  `attribution` varchar(50) DEFAULT '' COMMENT '归属地',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT '' COMMENT '创建人id',
  PRIMARY KEY (`id`),
  KEY `idx_user_name` (`user_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统日志记录表';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('9a5968dfc1724db6a6be16bc8a73ce56', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-21 16:18:48', 'user_001');
INSERT INTO `sys_log` VALUES ('9d14ce1868e9470ba905cef161801b9a', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-21 19:15:07', 'user_001');
INSERT INTO `sys_log` VALUES ('9ebdc855fbb34867842c68e953643243', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-26 11:19:37', 'user_001');
INSERT INTO `sys_log` VALUES ('9f9d75ddc00748bdb002e2e4f9d45b94', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-21 21:47:04', 'user_001');
INSERT INTO `sys_log` VALUES ('9fd6f96b2d484975a0d9c6797bedbab0', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-22 17:18:22', 'user_001');
INSERT INTO `sys_log` VALUES ('a18e2451cf3c4af58a7637aa9d1141b1', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-21 15:31:57', 'user_001');
INSERT INTO `sys_log` VALUES ('a4538325c657457d9b0373de3c239331', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-21 15:35:28', 'user_001');
INSERT INTO `sys_log` VALUES ('a50a1af0b8434b7993a7b47e074b570e', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-26 11:07:59', 'user_001');
INSERT INTO `sys_log` VALUES ('a8d78f93bf594555b07096626bad9595', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-21 18:48:07', 'user_001');
INSERT INTO `sys_log` VALUES ('ab30783ffd904d88931dc32c1a934ce7', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-26 15:30:14', 'user_001');
INSERT INTO `sys_log` VALUES ('ac10c147d7ff4bd19307e005e7613d3b', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-22 14:28:43', 'user_001');
INSERT INTO `sys_log` VALUES ('adc3902707364432b5c6051cdfb501de', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-26 13:37:44', 'user_001');
INSERT INTO `sys_log` VALUES ('b2096442217e46a38d79474030ec6b79', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-22 16:56:45', 'user_001');
INSERT INTO `sys_log` VALUES ('b30a25acd7304c1cb68b6ed253864aa1', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-26 15:52:16', 'user_001');
INSERT INTO `sys_log` VALUES ('b8fbde0798eb4722926e4cd47af093b9', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-26 15:54:03', 'user_001');
INSERT INTO `sys_log` VALUES ('bc5382a979c443aeae990a50aa4911d1', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-26 10:45:33', 'user_001');
INSERT INTO `sys_log` VALUES ('bd19c6faab9e49c1939c3903e954d7b6', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-21 15:45:25', 'user_001');
INSERT INTO `sys_log` VALUES ('c2d5e86dcb104c42b2cebccf338a0ed6', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-26 10:49:52', 'user_001');
INSERT INTO `sys_log` VALUES ('c3824fff8e4547989f7b4ab949f0885a', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-21 15:22:53', 'user_001');
INSERT INTO `sys_log` VALUES ('c46418622afa4b01adce27134cf17f4c', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-21 17:47:37', 'user_001');
INSERT INTO `sys_log` VALUES ('c4fae5b1b96c4bf9b5ef6f2855618a46', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-22 17:17:44', 'user_001');
INSERT INTO `sys_log` VALUES ('c66547275ef8433e9ee41ed8f946cb00', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-25 17:33:14', 'user_001');
INSERT INTO `sys_log` VALUES ('c7e47a273c944fc48b254656958b5ecd', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-22 15:43:26', 'user_001');
INSERT INTO `sys_log` VALUES ('c9a121fdea464a308656cdafca53a713', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-26 15:27:32', 'user_001');
INSERT INTO `sys_log` VALUES ('cbf6602259ac49268d1043fb46bc8c34', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-20 18:40:40', 'user_001');
INSERT INTO `sys_log` VALUES ('ce55b903f85e4f56ba89aa1a10345581', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-22 16:21:13', 'user_001');
INSERT INTO `sys_log` VALUES ('cfd28f1642704965a0b47c73a03148d3', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-21 21:00:02', 'user_001');
INSERT INTO `sys_log` VALUES ('d0f2f6a33a3843bf998be5cff3a0d782', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-22 09:29:27', 'user_001');
INSERT INTO `sys_log` VALUES ('d1c1b22393a444d99374c3603621b53a', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-20 18:35:32', 'user_001');
INSERT INTO `sys_log` VALUES ('d1eed76b6477445581672fced7234b43', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-21 10:50:25', 'user_001');
INSERT INTO `sys_log` VALUES ('d6225ac711134838b13a9a20e0070bc8', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-26 15:59:22', 'user_001');
INSERT INTO `sys_log` VALUES ('d6776bb8e73f40579c5d9013ded6224d', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-25 14:24:46', 'user_001');
INSERT INTO `sys_log` VALUES ('d6b7534a68da4c2ea49083ef6d11c1bf', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-22 16:03:59', 'user_001');
INSERT INTO `sys_log` VALUES ('d6f944e34a0f4d8ba45007eb4d275580', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-26 15:51:35', 'user_001');
INSERT INTO `sys_log` VALUES ('da23a31444154cbbbe2843f6624c8e0c', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-21 17:53:37', 'user_001');
INSERT INTO `sys_log` VALUES ('dd80b22cf7824fafac4109da6c1550c4', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-21 09:13:44', 'user_001');
INSERT INTO `sys_log` VALUES ('df96e80eb0314f4abc9251d69d82dfe2', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-25 18:41:10', 'user_001');
INSERT INTO `sys_log` VALUES ('e389a6337b014415b78fd603d0c961b6', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-20 18:05:47', 'user_001');
INSERT INTO `sys_log` VALUES ('e676287909284794b8cccfd668806567', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-20 18:02:03', 'user_001');
INSERT INTO `sys_log` VALUES ('eb0439761bc4425899a40fd62142f1e9', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-26 16:04:28', 'user_001');
INSERT INTO `sys_log` VALUES ('ebbf3ed74a024bfc917753c2d847f9c8', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-22 09:33:30', 'user_001');
INSERT INTO `sys_log` VALUES ('ed031da43fe24aaab88a105c148a608a', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-25 17:45:41', 'user_001');
INSERT INTO `sys_log` VALUES ('f1efd8602d314b3b98a6c0ae5da348cb', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-21 18:03:45', 'user_001');
INSERT INTO `sys_log` VALUES ('f41237a49f204ebead21763296fb10c2', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-22 16:07:03', 'user_001');
INSERT INTO `sys_log` VALUES ('f5521a3b8c5549dcae53c1d7bd25ccb1', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-21 18:57:46', 'user_001');
INSERT INTO `sys_log` VALUES ('f96465c8655f4ed6a40475b0b9873dd0', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-26 15:51:22', 'user_001');
INSERT INTO `sys_log` VALUES ('faaf6176b445489d9fa4c7862509b4c0', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-22 16:47:24', 'user_001');
INSERT INTO `sys_log` VALUES ('ff13d73ae76b43a99381abe74270c6b4', 'admin', '管理员', 'C-[用户]-控制器', '用户登录成功', '', '', '127.0.0.1', '内网IP', '2019-11-21 18:12:07', 'user_001');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `menu_id` varchar(32) DEFAULT '' COMMENT '父菜单ID，一级菜单为空串',
  `name` varchar(50) NOT NULL COMMENT '菜单名称',
  `uri` varchar(200) DEFAULT '' COMMENT '菜单uri 类型为产品/菜单 时有效',
  `perms` varchar(255) DEFAULT '' COMMENT '授权 按钮有效',
  `type` int(1) NOT NULL COMMENT '类型   参照枚举表的key',
  `ordered` int(1) DEFAULT '0' COMMENT '排序字段',
  `data_status` int(1) NOT NULL DEFAULT '1' COMMENT '数据有效性 1有效 0无效',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT '' COMMENT '创建人ID',
  `edit_time` datetime NOT NULL COMMENT '修改时间',
  `editor_id` varchar(32) DEFAULT '' COMMENT '修改人ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='目录/菜单/按钮/表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('06c9a9697a9742feb23473e64b74d72c', 'bdc78b86199f4a1b9ccceb521f4f9956', '修改接口', '', 'Role_Put', '3', '1', '1', '2019-11-20 16:48:30', 'user_001', '2019-11-20 17:59:10', 'user_001');
INSERT INTO `sys_menu` VALUES ('0864606b2ca04bbd84dcbc90b62c333e', '296417508d2443ec96f999328c202228', '备份静态资源', '1', 'backups_post', '3', '1', '1', '2019-12-24 17:46:16', '3e79872d256a4ccca05fc598bfb6fa46', '2019-12-24 17:46:16', '3e79872d256a4ccca05fc598bfb6fa46');
INSERT INTO `sys_menu` VALUES ('0d1e97aae1584d319771eaa45e4cae2b', '23b2e9e1b10944fba0063bbe3d76ece8', '给角色分配一组菜单', '', 'Menu_Post_role', '3', '1', '1', '2019-11-20 14:43:40', 'user_001', '2019-11-20 15:09:00', 'user_001');
INSERT INTO `sys_menu` VALUES ('15c409bb4cd94541b62252b63f03d971', 'bdc78b86199f4a1b9ccceb521f4f9956', '详情按钮', '', 'Role_info_view', '3', '1', '1', '2019-11-20 18:10:25', 'user_001', '2019-11-20 18:10:25', 'user_001');
INSERT INTO `sys_menu` VALUES ('17e344ba555c4112903e83c5f7e3c93a', '71794bd088f8439a9f70518ac9faefe0', '删除按钮', '', 'Product_delete_view', '3', '0', '1', '2019-11-20 17:23:43', 'user_001', '2019-11-20 18:00:56', 'user_001');
INSERT INTO `sys_menu` VALUES ('1ae611f454a04353a4fcea52b0422228', 'menu_005', '修改接口', '', 'Enum_Put_update', '3', '1', '1', '2019-11-20 14:10:33', 'user_001', '2019-11-20 17:58:40', 'user_001');
INSERT INTO `sys_menu` VALUES ('1d35f383893a4b9f948d104df3ffe338', 'bdc78b86199f4a1b9ccceb521f4f9956', 'ID查询角色接口', '', 'Role_Get_id', '3', '1', '1', '2019-11-20 16:46:31', 'user_001', '2019-11-20 17:59:15', 'user_001');
INSERT INTO `sys_menu` VALUES ('230887e17028408791fb465bb4c3b7b3', 'menu_005', '删除按钮', '', 'Enum_delete_view', '3', '0', '1', '2019-11-20 15:04:35', 'user_001', '2019-11-20 17:58:19', 'user_001');
INSERT INTO `sys_menu` VALUES ('23b2e9e1b10944fba0063bbe3d76ece8', '', '菜单管理', '/menu', '', '2', '10', '1', '2019-11-15 13:57:36', 'user_001', '2019-11-20 11:15:25', 'user_001');
INSERT INTO `sys_menu` VALUES ('28e1d824a2eb46f4b90425b39722001c', '', '文章资料', '/article', '', '1', '4', '1', '2019-11-18 17:07:47', 'user_001', '2019-11-22 14:29:23', 'user_001');
INSERT INTO `sys_menu` VALUES ('296417508d2443ec96f999328c202228', '4d4350707bfe4c81a970eefe601b156f', '静态资源管理', '/static', '/static', '2', '16', '1', '2019-12-24 17:01:46', '3e79872d256a4ccca05fc598bfb6fa46', '2019-12-25 11:07:48', '3e79872d256a4ccca05fc598bfb6fa46');
INSERT INTO `sys_menu` VALUES ('3035b478f21d46c88a418d62e779f3fe', 'bdc78b86199f4a1b9ccceb521f4f9956', '删除接口', '', 'Role_Delete', '3', '1', '1', '2019-11-20 16:48:51', 'user_001', '2019-11-20 17:59:13', 'user_001');
INSERT INTO `sys_menu` VALUES ('30966e687e9540c5b468f2467ecf2ca5', 'ea0493b547544280a7986f7109c7ef24', '删除按钮', '', 'Work_delete_view', '3', '0', '1', '2019-11-20 17:15:24', 'user_001', '2019-11-20 17:15:24', 'user_001');
INSERT INTO `sys_menu` VALUES ('332af45a517a40b586da42b426691cc4', '47c9429fe9624859a6c544049452ed2e', '编辑按钮', '', 'User_edit_view', '3', '0', '1', '2019-11-20 17:04:07', 'user_001', '2019-11-20 17:04:07', 'user_001');
INSERT INTO `sys_menu` VALUES ('38beb6cde1d849cba7a77ac601e11841', '71794bd088f8439a9f70518ac9faefe0', '分页查询接口', '', 'PlatformProduct_Get_list_page', '3', '1', '1', '2019-11-20 11:17:01', 'user_001', '2019-11-20 18:01:15', 'user_001');
INSERT INTO `sys_menu` VALUES ('3b5e6e35c7b244d8b93e9405d17abe73', '28e1d824a2eb46f4b90425b39722001c', '创建按钮', '', 'Article_create_view', '3', '0', '1', '2019-11-20 17:32:35', 'user_001', '2019-11-20 17:32:35', 'user_001');
INSERT INTO `sys_menu` VALUES ('3de285e9beb848abb93dd2ca99f6209a', '23b2e9e1b10944fba0063bbe3d76ece8', '所有菜单树', '', 'Menu_Get_tree', '3', '1', '1', '2019-11-20 14:40:13', 'user_001', '2019-11-20 15:09:15', 'user_001');
INSERT INTO `sys_menu` VALUES ('3fc60b836a014ca98e6cb4358b6a0c36', '28e1d824a2eb46f4b90425b39722001c', '详情按钮', '', 'Article_info_view', '3', '0', '1', '2019-11-20 18:06:09', 'user_001', '2019-11-20 18:06:09', 'user_001');
INSERT INTO `sys_menu` VALUES ('401d7f72cea34d1f9122baccd13657d5', '71794bd088f8439a9f70518ac9faefe0', '编辑按钮', '', 'Product_edit_view', '3', '0', '1', '2019-11-20 17:25:25', 'user_001', '2019-11-20 18:00:58', 'user_001');
INSERT INTO `sys_menu` VALUES ('4185d27e28684ae094c7348fa29ad95b', '28e1d824a2eb46f4b90425b39722001c', '删除附件接口', '', 'ArticleData_Delete_files_id', '3', '1', '1', '2019-11-20 17:14:05', 'user_001', '2019-11-20 18:01:55', 'user_001');
INSERT INTO `sys_menu` VALUES ('462cb5fb1b4d4b7d9ed82122ba83d039', '296417508d2443ec96f999328c202228', '静态资源列表查询', '1', 'staticData_list_page', '3', '1', '1', '2019-12-24 17:40:53', '3e79872d256a4ccca05fc598bfb6fa46', '2019-12-24 17:40:53', '3e79872d256a4ccca05fc598bfb6fa46');
INSERT INTO `sys_menu` VALUES ('47c9429fe9624859a6c544049452ed2e', '', '用户管理', '/user', '', '2', '8', '1', '2019-11-18 17:05:35', 'user_001', '2019-11-20 11:04:08', 'user_001');
INSERT INTO `sys_menu` VALUES ('47fb7f41806e44fba0994eee6560cde9', '28e1d824a2eb46f4b90425b39722001c', 'ID查询接口', '', 'ArticleData_Get_id', '3', '1', '1', '2019-11-20 17:16:20', 'user_001', '2019-11-20 18:02:00', 'user_001');
INSERT INTO `sys_menu` VALUES ('48db2640e8a34a019d5c3fb365fb1283', '28e1d824a2eb46f4b90425b39722001c', '修改接口', '', 'ArticleData_Put', '3', '1', '1', '2019-11-20 17:15:44', 'user_001', '2019-11-20 18:01:58', 'user_001');
INSERT INTO `sys_menu` VALUES ('4a102ab13a6f4fdd9e6b142c462c61f7', 'menu_002', '查询', '', 'DataSource_Get_list_table', '3', '1', '1', '2019-11-20 10:57:31', 'user_001', '2019-11-20 17:57:47', 'user_001');
INSERT INTO `sys_menu` VALUES ('4a838f08c8ad46db9a90303f4bb1dfc2', 'ea0493b547544280a7986f7109c7ef24', '删除接口', '', 'WorkPlatform_Delete', '3', '1', '1', '2019-11-20 17:02:02', 'user_001', '2019-11-20 18:00:38', 'user_001');
INSERT INTO `sys_menu` VALUES ('4c39772c35884cccbd75b0c8d8d53029', '71794bd088f8439a9f70518ac9faefe0', '修改接口', '', 'PlatformProduct_Put', '3', '1', '1', '2019-11-20 17:05:10', 'user_001', '2019-11-20 18:01:11', 'user_001');
INSERT INTO `sys_menu` VALUES ('4d4350707bfe4c81a970eefe601b156f', '', '系统工具', '/util', '', '1', '3', '1', '2019-12-24 16:44:49', '3e79872d256a4ccca05fc598bfb6fa46', '2019-12-24 16:49:33', '3e79872d256a4ccca05fc598bfb6fa46');
INSERT INTO `sys_menu` VALUES ('5a0419e5db664c0e81d10ffbfbee89d9', '71794bd088f8439a9f70518ac9faefe0', '新增接口', '', 'PlatformProduct_Post', '3', '1', '1', '2019-11-20 17:04:56', 'user_001', '2019-11-20 18:01:08', 'user_001');
INSERT INTO `sys_menu` VALUES ('5bd0a6cb605347fb98a9554400aacf11', 'bdc78b86199f4a1b9ccceb521f4f9956', '编辑按钮', '', 'Role_edit_view', '3', '0', '1', '2019-11-20 17:07:20', 'user_001', '2019-11-20 17:07:20', 'user_001');
INSERT INTO `sys_menu` VALUES ('5d9a1d7441a94e4cba4246218d0915b3', 'ea0493b547544280a7986f7109c7ef24', 'ID查询接口', '', 'WorkPlatform_Get_id', '3', '1', '1', '2019-11-20 17:00:31', 'user_001', '2019-11-20 18:00:30', 'user_001');
INSERT INTO `sys_menu` VALUES ('608e81c1b83c495ea8510914608c94ed', 'menu_005', '删除接口', '', 'Enum_Delete', '3', '1', '1', '2019-11-20 14:07:02', 'user_001', '2019-11-20 17:58:34', 'user_001');
INSERT INTO `sys_menu` VALUES ('63a71ad36bc34057a5270f3d1e4b3b8f', 'ea0493b547544280a7986f7109c7ef24', '修改接口', '', 'WorkPlatform_Put', '3', '1', '1', '2019-11-20 17:01:21', 'user_001', '2019-11-20 18:00:28', 'user_001');
INSERT INTO `sys_menu` VALUES ('682fe4c989e644f8a52f1dd5d7c568ac', 'menu_005', '详情按钮', '', 'Enum_info_view', '3', '0', '1', '2019-11-20 15:05:09', 'user_001', '2019-11-20 15:17:09', 'user_001');
INSERT INTO `sys_menu` VALUES ('690e583af5fa4c4092136dc88fa68985', '28e1d824a2eb46f4b90425b39722001c', '开发规范', '/standard', '/standard', '2', '11', '1', '2019-11-21 17:08:19', 'user_001', '2019-11-27 19:36:03', '3e79872d256a4ccca05fc598bfb6fa46');
INSERT INTO `sys_menu` VALUES ('6a3256a2cd434449ba472aa4963d6f8c', 'bdc78b86199f4a1b9ccceb521f4f9956', '新增接口', '', 'Role_Post', '3', '1', '1', '2019-11-20 16:42:18', 'user_001', '2019-11-20 17:59:21', 'user_001');
INSERT INTO `sys_menu` VALUES ('6bb20e767b6448b5a65ecf18fe5f55e9', 'ea0493b547544280a7986f7109c7ef24', '分页查询接口', '', 'WorkPlatform_Get_list_page', '3', '1', '1', '2019-11-20 11:16:43', 'user_001', '2019-11-20 18:00:33', 'user_001');
INSERT INTO `sys_menu` VALUES ('6c21f31bfe044cd6a14f0dafa91ab810', '47c9429fe9624859a6c544049452ed2e', '删除按钮', '', 'User_delete_view', '3', '0', '1', '2019-11-20 16:54:08', 'user_001', '2019-11-20 17:58:27', 'user_001');
INSERT INTO `sys_menu` VALUES ('6c4b6dfe398e448f9fb447c83e514e42', 'e5dde4ea61fb494282e1065752053e16', '分页查询接口', '', 'Log_Get_list_page', '3', '1', '1', '2019-11-20 11:17:18', 'user_001', '2019-11-20 18:01:29', 'user_001');
INSERT INTO `sys_menu` VALUES ('6f2f716a7dd041818b242edbc19198ad', 'menu_005', '分页查询接口', '', 'Enum_Get_list_page', '3', '1', '1', '2019-11-20 11:11:22', 'user_001', '2019-11-20 17:58:45', 'user_001');
INSERT INTO `sys_menu` VALUES ('71794bd088f8439a9f70518ac9faefe0', '', '平台产品', '/product', '', '2', '1', '1', '2019-11-18 17:06:53', 'user_001', '2019-11-20 11:16:50', 'user_001');
INSERT INTO `sys_menu` VALUES ('72b1d4fe150946cdb845f89e2a5c57bc', '28e1d824a2eb46f4b90425b39722001c', '开发软件', '/software', '/software', '2', '12', '1', '2019-11-21 16:47:15', 'user_001', '2019-11-27 19:35:46', '3e79872d256a4ccca05fc598bfb6fa46');
INSERT INTO `sys_menu` VALUES ('751cb909e33c42f78f2f847e8df65ca7', '23b2e9e1b10944fba0063bbe3d76ece8', '删除接口', '', 'Menu_Delete', '3', '1', '1', '2019-11-20 14:50:14', 'user_001', '2019-11-20 15:09:05', 'user_001');
INSERT INTO `sys_menu` VALUES ('7612cb0eba0a400db5c2d084b222e7c7', '47c9429fe9624859a6c544049452ed2e', '详情按钮', '', 'User_info_view', '3', '0', '1', '2019-11-20 17:06:17', 'user_001', '2019-11-20 17:06:17', 'user_001');
INSERT INTO `sys_menu` VALUES ('782b0cf890b445d9bc6de655f779c942', 'e5dde4ea61fb494282e1065752053e16', 'ID查询接口', '', 'Log_Get_id', '3', '1', '1', '2019-11-20 17:10:16', 'user_001', '2019-11-20 18:01:26', 'user_001');
INSERT INTO `sys_menu` VALUES ('79d0c468c6544bd3ab74f0e55b4180b0', '23b2e9e1b10944fba0063bbe3d76ece8', '删除按钮', '', 'Menu_delete_view', '3', '0', '1', '2019-11-20 15:10:25', 'user_001', '2019-11-20 15:10:37', 'user_001');
INSERT INTO `sys_menu` VALUES ('7a59e63da05e44c5a7725e94ed2f78a1', '296417508d2443ec96f999328c202228', '删除静态资源备份', '1', 'backups_delete', '3', '1', '1', '2019-12-24 17:47:08', '3e79872d256a4ccca05fc598bfb6fa46', '2019-12-24 17:47:08', '3e79872d256a4ccca05fc598bfb6fa46');
INSERT INTO `sys_menu` VALUES ('7a68ac4d56ee4caea45de70299c63374', '23b2e9e1b10944fba0063bbe3d76ece8', '详情按钮', '', 'Menu_info_view', '3', '0', '1', '2019-11-20 15:11:30', 'user_001', '2019-11-20 15:11:30', 'user_001');
INSERT INTO `sys_menu` VALUES ('7dcdf255316d4e538350b3624762f818', '28e1d824a2eb46f4b90425b39722001c', '新增接口', '', 'ArticleData_Post', '3', '1', '1', '2019-11-20 17:16:05', 'user_001', '2019-11-20 18:02:11', 'user_001');
INSERT INTO `sys_menu` VALUES ('8511d3db30804dd98efa08008c79b03d', '23b2e9e1b10944fba0063bbe3d76ece8', '修改接口', '', 'Menu_Put', '3', '1', '1', '2019-11-20 14:48:50', 'user_001', '2019-11-20 15:09:11', 'user_001');
INSERT INTO `sys_menu` VALUES ('85f382ce8f144bd2801d7017af5a54c3', '47c9429fe9624859a6c544049452ed2e', '分页查询接口', '', 'User_Get_list_page', '3', '1', '1', '2019-11-20 11:04:28', 'user_001', '2019-11-20 17:59:53', 'user_001');
INSERT INTO `sys_menu` VALUES ('8c507b0525624842ac753c68bb22d8d6', '47c9429fe9624859a6c544049452ed2e', '新增接口', '', 'User_Post', '3', '1', '1', '2019-11-20 16:52:54', 'user_001', '2019-11-20 17:59:56', 'user_001');
INSERT INTO `sys_menu` VALUES ('8f5fd040e9ba4bd8afa8f7043f916fd0', 'menu_003', '分页查询接口', '', 'Department_Get_list_page', '3', '1', '1', '2019-11-20 11:03:56', 'user_001', '2019-11-20 17:58:10', 'user_001');
INSERT INTO `sys_menu` VALUES ('923b0c527a184629b1aec983a82d1864', 'bdc78b86199f4a1b9ccceb521f4f9956', '分页查询接口', '', 'Role_Get_list_page', '3', '1', '1', '2019-11-20 11:15:55', 'user_001', '2019-11-20 17:59:24', 'user_001');
INSERT INTO `sys_menu` VALUES ('928b84dd9f15457a90ca4816f622d20e', 'menu_005', '编辑按钮', '', 'Enum_edit_view', '3', '0', '1', '2019-11-20 14:56:17', 'user_001', '2019-11-20 17:58:27', 'user_001');
INSERT INTO `sys_menu` VALUES ('930a525af1414109b39b96f53910f310', 'ea0493b547544280a7986f7109c7ef24', '新增接口', '', 'WorkPlatform_Post', '3', '1', '1', '2019-11-20 16:56:46', 'user_001', '2019-11-20 18:00:24', 'user_001');
INSERT INTO `sys_menu` VALUES ('9318e83069c3420995a1468003c5ade4', '28e1d824a2eb46f4b90425b39722001c', '分页查询接口', '', 'ArticleData_Get_list_page', '3', '1', '1', '2019-11-20 11:17:34', 'user_001', '2019-11-20 18:02:03', 'user_001');
INSERT INTO `sys_menu` VALUES ('95bf8e580fab4de6886930745c2f8912', '23b2e9e1b10944fba0063bbe3d76ece8', '分页查询', '', 'Menu_Get_list_page', '3', '1', '1', '2019-11-20 11:14:33', 'user_001', '2019-11-20 15:09:19', 'user_001');
INSERT INTO `sys_menu` VALUES ('9747f2ff53ce4de296a199d118cabcf4', '28e1d824a2eb46f4b90425b39722001c', '查询附件组接口', '', 'ArticleData_Get_files_id', '3', '1', '1', '2019-11-20 17:13:41', 'user_001', '2019-11-20 18:02:13', 'user_001');
INSERT INTO `sys_menu` VALUES ('99dfea5f86184fe79a5a6eb0713aa42d', 'menu_005', '增加接口', '', 'Enum_Post_insert', '3', '1', '1', '2019-11-20 14:07:46', 'user_001', '2019-11-20 17:58:37', 'user_001');
INSERT INTO `sys_menu` VALUES ('9becd8977cb040d9a3fdf8dfdf44990e', '23b2e9e1b10944fba0063bbe3d76ece8', '创建按钮', '', 'Menu_create_view', '3', '0', '1', '2019-11-20 14:47:32', 'user_001', '2019-11-20 15:10:33', 'user_001');
INSERT INTO `sys_menu` VALUES ('9d321f9eae9142fca03b4d2c98838100', '47c9429fe9624859a6c544049452ed2e', 'ID查询接口', '', 'User_Get_id', '3', '1', '1', '2019-11-20 16:52:29', 'user_001', '2019-11-20 17:59:58', 'user_001');
INSERT INTO `sys_menu` VALUES ('9f9afa78992540dc90e88ce17a3dd153', 'menu_005', 'ID查询接口', '', 'Enum_Get_id', '3', '1', '1', '2019-11-20 11:24:45', 'user_001', '2019-11-20 17:58:42', 'user_001');
INSERT INTO `sys_menu` VALUES ('a0fbbf760aaf4a21965e2a9af271abca', 'menu_002', '生成', '', 'CodeGenerator_Get_generate', '3', '1', '1', '2019-11-20 10:49:49', 'user_001', '2019-11-20 17:57:53', 'user_001');
INSERT INTO `sys_menu` VALUES ('a131a56f8d2048ffa095ede8b767deea', '23b2e9e1b10944fba0063bbe3d76ece8', '编辑按钮', '', 'Menu_edit_view', '3', '0', '1', '2019-11-20 15:10:50', 'user_001', '2019-11-20 15:10:56', 'user_001');
INSERT INTO `sys_menu` VALUES ('a28ee6251e5c4196bef28db50fd1e312', 'ea0493b547544280a7986f7109c7ef24', '编辑按钮', '', 'Work_edit_view', '3', '0', '1', '2019-11-20 17:15:07', 'user_001', '2019-11-20 17:15:07', 'user_001');
INSERT INTO `sys_menu` VALUES ('a7bd4a816bb24997a51d4c6424e168ab', '71794bd088f8439a9f70518ac9faefe0', '详情按钮', '', 'Product_info_view', '3', '1', '1', '2019-11-20 18:09:06', 'user_001', '2019-11-20 18:09:06', 'user_001');
INSERT INTO `sys_menu` VALUES ('a9e061261b16422c8a4180dae380a0c4', '71794bd088f8439a9f70518ac9faefe0', '创建按钮', '', 'Product_create_view', '3', '0', '1', '2019-11-20 17:21:41', 'user_001', '2019-11-20 18:00:54', 'user_001');
INSERT INTO `sys_menu` VALUES ('b1d9635e247845d3af31a0573efb4ee4', 'menu_005', '查询类型组数据-接口', '', 'Enum_Get_list_type', '3', '1', '1', '2019-11-20 11:22:48', 'user_001', '2019-11-20 17:58:31', 'user_001');
INSERT INTO `sys_menu` VALUES ('b26f404b363241e8ba881b4d30db1ecb', '71794bd088f8439a9f70518ac9faefe0', '删除接口', '', 'PlatformProduct_Delete', '3', '1', '1', '2019-11-20 17:04:45', 'user_001', '2019-11-20 18:01:03', 'user_001');
INSERT INTO `sys_menu` VALUES ('bd588f17bd094f4ca579e3d4056b6502', 'menu_002', '啊啊啊啊啊', '', '', '1', '0', '0', '2019-11-28 17:48:38', '3e79872d256a4ccca05fc598bfb6fa46', '2019-11-28 17:48:45', '3e79872d256a4ccca05fc598bfb6fa46');
INSERT INTO `sys_menu` VALUES ('bdc78b86199f4a1b9ccceb521f4f9956', '', '角色管理', '/role', '', '2', '9', '1', '2019-11-15 14:00:19', 'user_001', '2019-11-20 11:15:37', 'user_001');
INSERT INTO `sys_menu` VALUES ('c0c983cc78a34de0a97f3f0db21f9df8', '296417508d2443ec96f999328c202228', '恢复备份资源', '1', 'backups_id_put', '3', '1', '1', '2019-12-24 17:48:10', '3e79872d256a4ccca05fc598bfb6fa46', '2019-12-24 17:48:10', '3e79872d256a4ccca05fc598bfb6fa46');
INSERT INTO `sys_menu` VALUES ('c19d2431a8534e2b813ec30582372453', '23b2e9e1b10944fba0063bbe3d76ece8', 'ID查询菜单', '', 'Menu_Get_id', '3', '1', '1', '2019-11-20 14:45:15', 'user_001', '2019-11-20 15:09:27', 'user_001');
INSERT INTO `sys_menu` VALUES ('c460a02d3d874a8d8baa401a7a6a570a', 'menu_005', '创建按钮', '', 'Enum_create_view', '3', '0', '1', '2019-11-20 15:01:45', 'user_001', '2019-11-20 17:58:24', 'user_001');
INSERT INTO `sys_menu` VALUES ('c5b47c9411ec4bb9aeb5fe44c9f6cb6c', '296417508d2443ec96f999328c202228', '删除静态资源', '1', 'staticData_delete', '3', '1', '1', '2019-12-24 17:44:04', '3e79872d256a4ccca05fc598bfb6fa46', '2019-12-24 17:44:04', '3e79872d256a4ccca05fc598bfb6fa46');
INSERT INTO `sys_menu` VALUES ('c742b32ef00b4a4eb3dec25126c27dfa', '28e1d824a2eb46f4b90425b39722001c', '批量下载接口', '', 'ArticleData_Get_download_package_files', '3', '1', '1', '2019-11-22 14:31:26', 'user_001', '2019-11-22 14:31:26', 'user_001');
INSERT INTO `sys_menu` VALUES ('c75cf11bc92d452aa55d9feecd164d05', 'bdc78b86199f4a1b9ccceb521f4f9956', '删除按钮', '', 'Role_delete_view', '3', '0', '1', '2019-11-20 17:07:44', 'user_001', '2019-11-20 17:07:44', 'user_001');
INSERT INTO `sys_menu` VALUES ('c94ce63a64824bf7af43d5bda879af9a', '28e1d824a2eb46f4b90425b39722001c', '删除按钮', '', 'Article_delete_view', '3', '0', '1', '2019-11-20 17:32:53', 'user_001', '2019-11-20 17:32:53', 'user_001');
INSERT INTO `sys_menu` VALUES ('cbc62aa0ce4d48bbbebc5fa7d52d0828', 'menu_002', '代码生成按钮', '', 'Code_Put_view', '3', '0', '1', '2019-11-20 16:43:48', 'user_001', '2019-11-21 10:27:20', 'user_001');
INSERT INTO `sys_menu` VALUES ('ce56eab421c74ce9b3424fa3611c220d', '47c9429fe9624859a6c544049452ed2e', '修改接口', '', 'User_Put', '3', '1', '1', '2019-11-20 16:53:24', 'user_001', '2019-11-20 18:00:01', 'user_001');
INSERT INTO `sys_menu` VALUES ('d22e72ca84394281ae6132a5588996df', 'bdc78b86199f4a1b9ccceb521f4f9956', '创建按钮', '', 'Role_create_view', '3', '0', '1', '2019-11-20 17:06:56', 'user_001', '2019-11-20 17:59:36', 'user_001');
INSERT INTO `sys_menu` VALUES ('d3996758a3214f3b90a9e8413ffc3fdb', '28e1d824a2eb46f4b90425b39722001c', '共享空间', '/toolExplain', '/toolExplain', '2', '14', '1', '2019-11-21 17:13:52', 'user_001', '2019-12-02 16:14:52', '3e79872d256a4ccca05fc598bfb6fa46');
INSERT INTO `sys_menu` VALUES ('d440a01db2ce48efbd5ddd6f9baf2b2f', '28e1d824a2eb46f4b90425b39722001c', '删除接口', '', 'ArticleData_Delete', '3', '1', '1', '2019-11-20 17:14:25', 'user_001', '2019-11-20 18:02:07', 'user_001');
INSERT INTO `sys_menu` VALUES ('d55b62b4ff71472a81a3fd7b79803c38', '28e1d824a2eb46f4b90425b39722001c', '技术分享', '/share', '/share', '2', '13', '1', '2019-11-21 17:14:35', 'user_001', '2019-11-22 14:30:45', 'user_001');
INSERT INTO `sys_menu` VALUES ('d7830104628d4232b50d1eccd21f3299', '23b2e9e1b10944fba0063bbe3d76ece8', '某角色菜单树', '', 'Menu_Get_role_tree', '3', '1', '1', '2019-11-20 14:42:03', 'user_001', '2019-11-20 15:09:31', 'user_001');
INSERT INTO `sys_menu` VALUES ('d8deac5e25f649b6aaf63f55798fbd91', '296417508d2443ec96f999328c202228', '查询静态资源备份列表', '1', 'backups_list_page', '3', '1', '1', '2019-12-24 17:44:51', '3e79872d256a4ccca05fc598bfb6fa46', '2019-12-24 17:44:51', '3e79872d256a4ccca05fc598bfb6fa46');
INSERT INTO `sys_menu` VALUES ('db0bb951a5d94b1ab8cc3a2a99133f49', '47c9429fe9624859a6c544049452ed2e', '新增按钮', '', 'User_create_view', '3', '0', '1', '2019-11-20 16:53:28', 'user_001', '2019-11-20 16:53:56', 'user_001');
INSERT INTO `sys_menu` VALUES ('df8bc20096d34f86a59856b67f5b237a', '71794bd088f8439a9f70518ac9faefe0', 'ID查询接口', '', 'PlatformProduct_Get_id', '3', '1', '1', '2019-11-20 17:05:45', 'user_001', '2019-11-20 18:01:05', 'user_001');
INSERT INTO `sys_menu` VALUES ('e47b8a7e091c4fe6969e5af176110439', '47c9429fe9624859a6c544049452ed2e', '删除接口', '', 'User_Delete', '3', '1', '1', '2019-11-20 16:55:06', 'user_001', '2019-11-20 18:00:04', 'user_001');
INSERT INTO `sys_menu` VALUES ('e5dde4ea61fb494282e1065752053e16', '', '日志管理', '/log', '', '2', '7', '1', '2019-11-18 17:07:18', 'user_001', '2019-11-20 11:17:07', 'user_001');
INSERT INTO `sys_menu` VALUES ('e7145d0d46994b74bda74b5a75abf5fe', 'bdc78b86199f4a1b9ccceb521f4f9956', '给指定用户分配角色接口', '', 'Role_Post_user', '3', '1', '1', '2019-11-20 16:46:03', 'user_001', '2019-11-20 17:59:27', 'user_001');
INSERT INTO `sys_menu` VALUES ('ea0493b547544280a7986f7109c7ef24', '', '工作平台', '/work', '', '2', '2', '1', '2019-11-18 17:06:25', 'user_001', '2019-11-20 11:16:32', 'user_001');
INSERT INTO `sys_menu` VALUES ('ed5bd0f52e744891a86dcc2a4d6265d7', 'ea0493b547544280a7986f7109c7ef24', '创建按钮', '', 'Work_cearte_view', '3', '0', '1', '2019-11-20 17:14:40', 'user_001', '2019-11-20 18:00:21', 'user_001');
INSERT INTO `sys_menu` VALUES ('f19e6e7a215a4a209cd6ba4c9dc9a743', '296417508d2443ec96f999328c202228', '保存静态资源', '1', 'staticData_post', '3', '1', '1', '2019-12-24 17:42:26', '3e79872d256a4ccca05fc598bfb6fa46', '2019-12-24 17:42:26', '3e79872d256a4ccca05fc598bfb6fa46');
INSERT INTO `sys_menu` VALUES ('f72fc48322b84e1184538d7b7573a30d', '23b2e9e1b10944fba0063bbe3d76ece8', '插入接口', '', 'Menu_Post', '3', '1', '1', '2019-11-20 14:46:30', 'user_001', '2019-11-20 17:58:54', 'user_001');
INSERT INTO `sys_menu` VALUES ('f91a5a6623864577a2b0c172ab2568d3', 'bdc78b86199f4a1b9ccceb521f4f9956', '获取某个用户的角色接口', '', 'Role_Get_list_user', '3', '1', '1', '2019-11-20 16:47:58', 'user_001', '2019-11-20 17:59:30', 'user_001');
INSERT INTO `sys_menu` VALUES ('fa3bca75fd824ec5babf956a5019bc50', '28e1d824a2eb46f4b90425b39722001c', '编辑按钮', '', 'Article_edit_view', '3', '0', '1', '2019-11-20 17:33:03', 'user_001', '2019-11-20 17:33:03', 'user_001');
INSERT INTO `sys_menu` VALUES ('fb72c89ebc944c4b8a22d52659837e5d', '296417508d2443ec96f999328c202228', '更新静态资源', '1', 'staticData_put', '3', '1', '1', '2019-12-24 17:43:20', '3e79872d256a4ccca05fc598bfb6fa46', '2019-12-24 17:43:20', '3e79872d256a4ccca05fc598bfb6fa46');
INSERT INTO `sys_menu` VALUES ('menu_002', '4d4350707bfe4c81a970eefe601b156f', '代码生成器', '/code', '', '2', '15', '1', '2019-10-30 17:44:00', '', '2019-12-24 16:59:48', '3e79872d256a4ccca05fc598bfb6fa46');
INSERT INTO `sys_menu` VALUES ('menu_003', '', '部门成员', '/department', '', '2', '6', '1', '2019-10-30 17:44:00', '', '2019-11-20 11:03:25', 'user_001');
INSERT INTO `sys_menu` VALUES ('menu_005', '', '枚举管理', '/enum', '', '2', '5', '1', '2019-10-30 17:44:00', '', '2019-11-20 11:10:34', 'user_001');

-- ----------------------------
-- Table structure for sys_platform_product
-- ----------------------------
DROP TABLE IF EXISTS `sys_platform_product`;
CREATE TABLE `sys_platform_product` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `title` varchar(50) DEFAULT '' COMMENT '产品标题',
  `content` text COMMENT '产品内容',
  `data_status` int(1) NOT NULL DEFAULT '1' COMMENT '数据有效性 1有效 0无效',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT '' COMMENT '创建人ID',
  `edit_time` datetime NOT NULL COMMENT '修改时间',
  `editor_id` varchar(32) DEFAULT '' COMMENT '修改人ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='平台产品';

-- ----------------------------
-- Records of sys_platform_product
-- ----------------------------
INSERT INTO `sys_platform_product` VALUES ('1579be99116c42efa9774cd9890ec3ef', '平台1', '<span>http://www.baidu.com</span>', '1', '2019-11-22 20:16:41', 'user_001', '2019-12-04 16:52:54', '3e79872d256a4ccca05fc598bfb6fa46');
INSERT INTO `sys_platform_product` VALUES ('46be3b4b53694c1ba20ffc2366bb9a8f', '平台1', '<span>http://www.baidu.com</span>', '1', '2019-11-20 17:27:57', 'user_001', '2019-12-04 16:54:29', '3e79872d256a4ccca05fc598bfb6fa46');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '角色名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT '' COMMENT '创建人ID',
  `edit_time` datetime NOT NULL COMMENT '修改时间',
  `editor_id` varchar(32) DEFAULT '' COMMENT '修改人ID',
  `data_status` int(1) NOT NULL DEFAULT '1' COMMENT '数据有效性 1有效 0无效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('role_001', '超级管理员', '2019-11-03 11:52:00', 'user_001', '2019-12-25 10:53:55', '3e79872d256a4ccca05fc598bfb6fa46', '1');

-- ----------------------------
-- Table structure for sys_role_menu_r
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu_r`;
CREATE TABLE `sys_role_menu_r` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `role_id` varchar(32) NOT NULL DEFAULT '' COMMENT '角色ID',
  `menu_id` varchar(32) NOT NULL DEFAULT '' COMMENT '目录/菜单/按钮',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `data_status` int(1) NOT NULL DEFAULT '1' COMMENT '数据有效性 1有效 0无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色/菜单/目录/按钮/关联表';

-- ----------------------------
-- Records of sys_role_menu_r
-- ----------------------------
INSERT INTO `sys_role_menu_r` VALUES ('02a98ee6aa2249a9bb40278c48f5d593', 'role_001', '751cb909e33c42f78f2f847e8df65ca7', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('05a50aa2331e4b7ab8c7f1c4d07df931', 'role_001', 'f72fc48322b84e1184538d7b7573a30d', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('06e315dd5e6b4c7eaf36d9246331b1d3', 'role_001', 'd55b62b4ff71472a81a3fd7b79803c38', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('0e1171b1b7b44289b9f0ce911fa7969a', 'role_001', '4a838f08c8ad46db9a90303f4bb1dfc2', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('149295ceec724375ab51a2f2f48f09e1', 'role_001', '6c4b6dfe398e448f9fb447c83e514e42', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('195a42f7f9734c7e8d4d9fd93d271e50', 'role_001', '23b2e9e1b10944fba0063bbe3d76ece8', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('19f2f6bd37ac40d19fe0ff81ab6b3a4b', 'role_001', '95bf8e580fab4de6886930745c2f8912', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('1d366e63036b4a07b6d7a5cdfd9bbf15', 'role_001', 'ce56eab421c74ce9b3424fa3611c220d', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('1fe8bf328c384737ab27c7a8f7105338', 'role_001', '0d1e97aae1584d319771eaa45e4cae2b', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('21b4bd4e83f7458b8e4ef45b41fcc9af', 'role_001', '7dcdf255316d4e538350b3624762f818', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('25f420d09a9e4503b2efa3e1e191d575', 'role_001', 'a7bd4a816bb24997a51d4c6424e168ab', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('2738950061104c26bf9dd726e45e0eba', 'role_001', 'f91a5a6623864577a2b0c172ab2568d3', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('2a25f07f719a47fe8bbd35bf19d9be01', 'role_001', '6f2f716a7dd041818b242edbc19198ad', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('2d3c471361994551a1c55605eedb49b2', 'role_001', '401d7f72cea34d1f9122baccd13657d5', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('2e68faa0695d4e95a33648e98edb5d64', 'role_001', '5bd0a6cb605347fb98a9554400aacf11', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('2ed2b276be5d42e38079448b51b3f88f', 'role_001', 'cbc62aa0ce4d48bbbebc5fa7d52d0828', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('2f4baba3225641e78e02490c9bf80652', 'role_001', '923b0c527a184629b1aec983a82d1864', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('30f5e955c0eb4c6ab983b41073868b6d', 'role_001', 'bdc78b86199f4a1b9ccceb521f4f9956', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('3118210f0b6a4f6287192eac0462760b', 'role_001', '4a102ab13a6f4fdd9e6b142c462c61f7', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('3619d53fb6e04e56886f6b976300ff5c', 'role_001', '7612cb0eba0a400db5c2d084b222e7c7', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('372422f8e2424d09a121f547d8faeae0', 'role_001', '99dfea5f86184fe79a5a6eb0713aa42d', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('38b66884f32e450ea404a5a7da38d79c', 'role_001', '85f382ce8f144bd2801d7017af5a54c3', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('401e3ba8fd204c5fbd7b54d5644d0f15', 'role_001', '3035b478f21d46c88a418d62e779f3fe', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('40bfd0421b1c416ebf76419f1b920464', 'role_001', '63a71ad36bc34057a5270f3d1e4b3b8f', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('45532db9c7d64346b32a21d18e782a49', 'role_001', 'c19d2431a8534e2b813ec30582372453', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('476f43e7c02b46189aa8e4e15d85dd21', 'role_001', '6c21f31bfe044cd6a14f0dafa91ab810', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('4d64524a926b416eb252dab0b2565f98', 'role_001', 'c5b47c9411ec4bb9aeb5fe44c9f6cb6c', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('503c259c0b9f438ab56819225c626e60', 'role_001', '608e81c1b83c495ea8510914608c94ed', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('50ec46c04a8b4fbb80ee092c4bad3268', 'role_001', '7a68ac4d56ee4caea45de70299c63374', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('5554c78019e547dc847084dd627943a1', 'role_001', '682fe4c989e644f8a52f1dd5d7c568ac', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('5a47adb68dc143ccaccc6e335aa49cfe', 'role_001', 'a9e061261b16422c8a4180dae380a0c4', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('5efcc3225ca542058ee9a7858bc215d9', 'role_001', '9747f2ff53ce4de296a199d118cabcf4', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('618eb0c415fc4d018fae29d6641563e0', 'role_001', '332af45a517a40b586da42b426691cc4', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('636337c932be437c8a78af2588fe5fc0', 'role_001', 'a0fbbf760aaf4a21965e2a9af271abca', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('65e5e603727443ae99d1f3eb901c884c', 'role_001', '9318e83069c3420995a1468003c5ade4', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('68c38215fff7492e8e48003751018434', 'role_001', '47fb7f41806e44fba0994eee6560cde9', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('6c1fa172e69440278e0427d819ffa298', 'role_001', 'e7145d0d46994b74bda74b5a75abf5fe', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('6f343f480838414bbd5d6b5a3878bf6a', 'role_001', '930a525af1414109b39b96f53910f310', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('6fbd5fc6c109458ea78d4147f6ec6dbc', 'role_001', '5d9a1d7441a94e4cba4246218d0915b3', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('72eeecff49284dd3b01a761628999928', 'role_001', '38beb6cde1d849cba7a77ac601e11841', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('7612ae05560e47fcbeb5b3a76cbb66af', 'role_001', '3fc60b836a014ca98e6cb4358b6a0c36', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('79784cbe084344dd940372af01cc0a10', 'role_001', '71794bd088f8439a9f70518ac9faefe0', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('7b7f0ca3cd6f4095aea588a2aedc325b', 'role_001', 'c75cf11bc92d452aa55d9feecd164d05', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('7e2f6314ef4b41be8dc302eb87d52e24', 'role_001', 'd8deac5e25f649b6aaf63f55798fbd91', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('80ca4af82381464d83336ffdc0476126', 'role_001', 'e47b8a7e091c4fe6969e5af176110439', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('81ee0ed90d004e1e8a4a817522c385be', 'role_001', 'c94ce63a64824bf7af43d5bda879af9a', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('826d02f50ec94165b88840d619fe4134', 'role_001', 'ed5bd0f52e744891a86dcc2a4d6265d7', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('834176236c674b779e61ef40c2e52ec8', 'role_001', '9d321f9eae9142fca03b4d2c98838100', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('86c7cc5eed824a1cb73da8724c7c3c5c', 'role_001', 'b26f404b363241e8ba881b4d30db1ecb', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('875d60302e2b4c4489f6d23ac632528f', 'role_001', '28e1d824a2eb46f4b90425b39722001c', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('892b5a6f2cf1493291578ad15f75a7da', 'role_001', 'd440a01db2ce48efbd5ddd6f9baf2b2f', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('89a3c5f063784ae3aad3bfc05a1f8102', 'role_001', '3b5e6e35c7b244d8b93e9405d17abe73', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('9107ed356d8a4083a353d2236cbc9ac2', 'role_001', '06c9a9697a9742feb23473e64b74d72c', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('984c3c4657fb4f3b9964f5a1ab728993', 'role_001', '296417508d2443ec96f999328c202228', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('9884b1d1d17e49a9823238b9b7f7fbb8', 'role_001', '1d35f383893a4b9f948d104df3ffe338', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('99f13c718de24dff9be1d3428f77afae', 'role_001', '7a59e63da05e44c5a7725e94ed2f78a1', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('9a7072c3efb6420e92d4ab5b16039719', 'role_001', 'd22e72ca84394281ae6132a5588996df', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('9c829f53d7c44bc9b1aae23e580ce51f', 'role_001', 'menu_002', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('9ed3b766a6aa4ed7b9006ad2b1e11a96', 'role_001', '72b1d4fe150946cdb845f89e2a5c57bc', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('a10e00e207a9459a9b4bbe2b60a53c05', 'role_001', '8f5fd040e9ba4bd8afa8f7043f916fd0', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('a2e94382a2d84a22b98bd451fb5f5184', 'role_001', 'f19e6e7a215a4a209cd6ba4c9dc9a743', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('a60e7feeb75248288d08e0e1bb70bec2', 'role_001', '48db2640e8a34a019d5c3fb365fb1283', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('a62c9c0c71f24cfea970625d45c0b088', 'role_001', '690e583af5fa4c4092136dc88fa68985', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('a74dec02dd5f4483a94af03dfe8c19e8', 'role_001', '15c409bb4cd94541b62252b63f03d971', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('a86274722ad04fc0a6786bbca95b88de', 'role_001', '8511d3db30804dd98efa08008c79b03d', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('a9092a7db9b34fab8dd9f952ceee6f4a', 'role_001', 'fa3bca75fd824ec5babf956a5019bc50', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('b062766a9d8549b791d182c0c9dcebbf', 'role_001', 'db0bb951a5d94b1ab8cc3a2a99133f49', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('b874ee640f404a07b6a0b63e8791c790', 'role_001', '4c39772c35884cccbd75b0c8d8d53029', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('b9dd9a22846f409293b5e4472d309861', 'role_001', '4185d27e28684ae094c7348fa29ad95b', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('bc0e984010fc46468436a9a21c754b26', 'role_001', 'a28ee6251e5c4196bef28db50fd1e312', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('be40b652f7d14594ac4b6addcd09242f', 'role_001', '928b84dd9f15457a90ca4816f622d20e', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('bf7d09222667481ab2406bb732a31f91', 'role_001', 'a131a56f8d2048ffa095ede8b767deea', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('c13c337aed3040a59b7f174cb0acff18', 'role_001', 'ea0493b547544280a7986f7109c7ef24', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('c83b746d87a24ed39ac632da872deb2d', 'role_001', '3de285e9beb848abb93dd2ca99f6209a', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('cb3a1bff63314aecbdbec377bef01bbc', 'role_001', 'df8bc20096d34f86a59856b67f5b237a', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('d1eaba1d631a49d99ccc427bc46cc488', 'role_001', 'b1d9635e247845d3af31a0573efb4ee4', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('d222c2ef84294a1a9dcc7f143021d0fb', 'role_001', '5a0419e5db664c0e81d10ffbfbee89d9', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('d42fefc05fc5497ea95a46d87341e6a9', 'role_001', '9f9afa78992540dc90e88ce17a3dd153', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('d4c48490e450497ca95ba1d1aef12292', 'role_001', 'd3996758a3214f3b90a9e8413ffc3fdb', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('d6d9ed6015c64a67aa884a48d9651402', 'role_001', '79d0c468c6544bd3ab74f0e55b4180b0', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('d8591740015c46a0a9315d71f18fba93', 'role_001', '462cb5fb1b4d4b7d9ed82122ba83d039', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('d8d603bdc4e44c97bd5c3fe02585b123', 'role_001', '6a3256a2cd434449ba472aa4963d6f8c', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('d98f73d0fcb9419fabfec38a35fad733', 'role_001', 'fb72c89ebc944c4b8a22d52659837e5d', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('dd4543b6a0ac4653a4769ba83fceb31a', 'role_001', '0864606b2ca04bbd84dcbc90b62c333e', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('dec267788c9146168795193fa61798a4', 'role_001', 'c742b32ef00b4a4eb3dec25126c27dfa', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('def2ad03d75a423989899604e07e2f2f', 'role_001', 'menu_005', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('e14c562098c547329d16ad0d19ae7558', 'role_001', 'c460a02d3d874a8d8baa401a7a6a570a', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('e248ed4b4a934dacab27ed22eae241f7', 'role_001', '8c507b0525624842ac753c68bb22d8d6', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('e2eb3efb2529460180f7151887efb1e9', 'role_001', 'e5dde4ea61fb494282e1065752053e16', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('e59454ba5b0140d6bec7fd3650c8a836', 'role_001', '9becd8977cb040d9a3fdf8dfdf44990e', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('e728875715bc4dbb9dfaa6cfa4c2fca4', 'role_001', 'c0c983cc78a34de0a97f3f0db21f9df8', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('e7795122382141939848b4032147b1d3', 'role_001', '6bb20e767b6448b5a65ecf18fe5f55e9', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('ee8fed7b20e140d38ae2761be1f86832', 'role_001', '1ae611f454a04353a4fcea52b0422228', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('f25cd8052ace409fb94e0342972904bc', 'role_001', '47c9429fe9624859a6c544049452ed2e', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('f272c1aec8a7471ea934ee61328d325e', 'role_001', '230887e17028408791fb465bb4c3b7b3', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('f3c31aef3449425ba4bfc1d7511b54ae', 'role_001', '30966e687e9540c5b468f2467ecf2ca5', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('f633c133f5814f189ada687e17663375', 'role_001', '4d4350707bfe4c81a970eefe601b156f', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('f9133395790f4228b4b3ebdc60742e66', 'role_001', '17e344ba555c4112903e83c5f7e3c93a', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('fa79ca104a3f4b6c9d3b11c0b35fd381', 'role_001', 'menu_003', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('fb83eab25f394b33821dfc2f26b23593', 'role_001', '782b0cf890b445d9bc6de655f779c942', '2019-12-25 10:53:59', '1');
INSERT INTO `sys_role_menu_r` VALUES ('fe9ae5aab71e49caaf68edb64fbeece9', 'role_001', 'd7830104628d4232b50d1eccd21f3299', '2019-12-25 10:53:59', '1');

-- ----------------------------
-- Table structure for sys_static_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_static_data`;
CREATE TABLE `sys_static_data` (
  `id` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '静态资源名称',
  `path` varchar(100) NOT NULL DEFAULT '' COMMENT '静态资源路径',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `edit_time` datetime NOT NULL COMMENT '更新时间',
  `editor_id` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `data_status` int(1) NOT NULL DEFAULT '1' COMMENT '1:可用 0：不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='静态资源表(只保存静态资源路径)';

-- ----------------------------
-- Records of sys_static_data
-- ----------------------------

-- ----------------------------
-- Table structure for sys_static_data_backups
-- ----------------------------
DROP TABLE IF EXISTS `sys_static_data_backups`;
CREATE TABLE `sys_static_data_backups` (
  `id` varchar(32) NOT NULL,
  `sys_static_data_id` varchar(32) NOT NULL COMMENT '静态文件数据源ID',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '备份静态资源名称',
  `path` varchar(100) NOT NULL DEFAULT '' COMMENT '备份静态资源路径',
  `remark` varchar(50) DEFAULT '' COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `edit_time` datetime NOT NULL COMMENT '更新时间',
  `editor_id` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `recover_time` datetime DEFAULT NULL COMMENT '恢复时间',
  `recover_id` varchar(32) DEFAULT NULL COMMENT '恢复人ID',
  `data_status` int(1) NOT NULL DEFAULT '1' COMMENT '1:可用 0：不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='静态资源备份表';

-- ----------------------------
-- Records of sys_static_data_backups
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `account` varchar(50) DEFAULT '' COMMENT '用户名',
  `password` varchar(50) DEFAULT '' COMMENT '密码',
  `salt` varchar(32) NOT NULL DEFAULT '' COMMENT '盐',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户姓名',
  `phone` varchar(50) DEFAULT '' COMMENT '手机',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `picture` varchar(255) DEFAULT '' COMMENT '用户头像',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT '' COMMENT '创建人ID',
  `edit_time` datetime NOT NULL COMMENT '修改时间',
  `editor_id` varchar(32) DEFAULT '' COMMENT '修改人ID',
  `data_status` int(1) NOT NULL DEFAULT '1' COMMENT '数据有效性 1有效 0无效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('3e79872d256a4ccca05fc598bfb6fa46', 'admin', '06af0e9eae9689aa0bde2351ab98a9dd', 'CMtddGvQUf1HJp1+mYDKXg==', '杨善明', '185-0023-0366', 'yangshanming@hiynn.com', '1ea5b204f10a40e284ed23f4235b1759.jpg', '2019-11-21 09:15:33', 'user_001', '2019-12-02 15:50:24', '3e79872d256a4ccca05fc598bfb6fa46', '1');

-- ----------------------------
-- Table structure for sys_user_role_r
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_r`;
CREATE TABLE `sys_user_role_r` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '用户ID',
  `role_id` varchar(32) NOT NULL DEFAULT '' COMMENT '角色ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `data_status` int(1) NOT NULL DEFAULT '1' COMMENT '数据有效性 1有效 0无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色关联表';

-- ----------------------------
-- Records of sys_user_role_r
-- ----------------------------
INSERT INTO `sys_user_role_r` VALUES ('01c0ac455d5d4964a17a6096688a708b', 'e32f031017be4c3ab713780c1902ecbc', '5d9d694644444cf59243abbfac6a3343', '2019-12-02 14:57:03', '1');
INSERT INTO `sys_user_role_r` VALUES ('0acb7b68da0f4f759a11f2f177e86916', '28c0291a617542149193542e66ede090', '5d9d694644444cf59243abbfac6a3343', '2019-12-02 14:57:28', '1');
INSERT INTO `sys_user_role_r` VALUES ('1603135e3cab469ca50aeb192c3c0634', 'f445b66161784533b672822d56a9b507', '5d9d694644444cf59243abbfac6a3343', '2019-11-29 15:33:56', '1');
INSERT INTO `sys_user_role_r` VALUES ('1c4582f31a7f4101982176b364f5a301', '7de4065ee46246d6b7bf4d2d969376dc', '5d9d694644444cf59243abbfac6a3343', '2019-12-02 15:04:27', '1');
INSERT INTO `sys_user_role_r` VALUES ('3a0bf2c1f44e403ead96733b1b63b88c', '6a218612fb61461daaf9806bf8484d2a', '5d9d694644444cf59243abbfac6a3343', '2019-12-02 15:04:20', '1');
INSERT INTO `sys_user_role_r` VALUES ('538549383f3c467ca439001606cf46db', 'f6814841b4044d2eaafe45d618225bce', '5d9d694644444cf59243abbfac6a3343', '2019-11-29 13:49:49', '1');
INSERT INTO `sys_user_role_r` VALUES ('5469bb866c0d4998ab13a3f7b4860c24', '1f9fd85d249941649c398cc44289a079', '5d9d694644444cf59243abbfac6a3343', '2019-12-02 15:02:10', '1');
INSERT INTO `sys_user_role_r` VALUES ('646dd417313d40c1b2511d8196c410e6', '974c71d537314b49bab00c77ff66a7c6', '5d9d694644444cf59243abbfac6a3343', '2019-12-02 15:10:12', '1');
INSERT INTO `sys_user_role_r` VALUES ('67b91bfa239f494ea807198bc6b45db6', '517b37b3b4914001b276eefec6f5158e', '5d9d694644444cf59243abbfac6a3343', '2019-12-02 15:15:27', '1');
INSERT INTO `sys_user_role_r` VALUES ('67d72b1db23e4da6af5b83e68ab08d08', '3e79872d256a4ccca05fc598bfb6fa46', 'role_001', '2019-12-02 14:54:26', '1');
INSERT INTO `sys_user_role_r` VALUES ('760a2a6d74384244b28a0dd3c22108bd', '0d9e8ea4c15b4b5c8972dd0e7c328daa', '97e4f67af7e8456d8025d7709d1ae01c', '2019-11-26 17:59:46', '1');
INSERT INTO `sys_user_role_r` VALUES ('85f1e1f86e564484af532bf91e1861a4', 'd47ca4b5a6f54a6e91750700e437d7d7', '5d9d694644444cf59243abbfac6a3343', '2019-12-02 14:55:35', '1');
INSERT INTO `sys_user_role_r` VALUES ('9023d203559449109c367fe4587c6c73', 'd6d48c408cc44835ae35cbc7d5c8c9f6', '5d9d694644444cf59243abbfac6a3343', '2019-12-02 14:48:39', '1');
INSERT INTO `sys_user_role_r` VALUES ('921bf4dd69cf407f9cb506899383abea', 'ff9be1557f4241a2a6d3bee653d76cd2', '5d9d694644444cf59243abbfac6a3343', '2019-11-29 13:53:40', '1');
INSERT INTO `sys_user_role_r` VALUES ('a144cd8e58184371afd293b36bdb7682', '80ef2c6094ed44d48dfe22d48009830d', '5d9d694644444cf59243abbfac6a3343', '2019-12-02 14:56:50', '1');
INSERT INTO `sys_user_role_r` VALUES ('cd62fb58bf2b43ac8b03b0b0ae0897a0', '4d67ac7562ed4cbfa1c8b324c334bd35', '5d9d694644444cf59243abbfac6a3343', '2019-12-02 14:54:36', '1');
INSERT INTO `sys_user_role_r` VALUES ('d74058721c67451da10c74f7b02c6fd8', 'user_001', '5d9d694644444cf59243abbfac6a3343', '2019-12-02 15:04:44', '1');
INSERT INTO `sys_user_role_r` VALUES ('f3eb86530e6441d0aaaead3d11a4bd5e', '90c347d7d7114233876821e64855afa1', '5d9d694644444cf59243abbfac6a3343', '2019-12-02 14:56:56', '1');
INSERT INTO `sys_user_role_r` VALUES ('fc69faeb7d5a46e98eedc6edba23c7cd', '6aaa00b0765248568d915145d2594584', '5d9d694644444cf59243abbfac6a3343', '2019-12-02 14:57:49', '1');

-- ----------------------------
-- Table structure for sys_work_platform
-- ----------------------------
DROP TABLE IF EXISTS `sys_work_platform`;
CREATE TABLE `sys_work_platform` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `title` varchar(50) NOT NULL DEFAULT '' COMMENT '平台标题',
  `icon` varchar(50) NOT NULL DEFAULT '' COMMENT '平台图标',
  `description` varchar(500) NOT NULL DEFAULT '' COMMENT '描述',
  `url` varchar(500) NOT NULL DEFAULT '' COMMENT 'url',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT '' COMMENT '创建人ID',
  `edit_time` datetime NOT NULL COMMENT '修改时间',
  `editor_id` varchar(32) DEFAULT '' COMMENT '修改人ID',
  `data_status` int(1) NOT NULL DEFAULT '1' COMMENT '数据有效性 1有效 0无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='工作平台';

-- ----------------------------
-- Records of sys_work_platform
-- ----------------------------
INSERT INTO `sys_work_platform` VALUES ('15d7c1b97132405daf6fb290e80a1126', 'OA办公系统', '9b3a7c4a94aa4349af8e3a6b403dbdfe.png', '协同办公系统', 'http://www.baidu.com', '2019-11-25 15:24:19', 'user_001', '2019-12-05 17:45:50', '3e79872d256a4ccca05fc598bfb6fa46', '1');
INSERT INTO `sys_work_platform` VALUES ('7bcaa6e689b541618117ac6ce733b4d5', '企业邮箱', 'ac564bafde9f48fb845101fb38a345a3.png', '企业邮箱', 'http://www.baidu.com', '2019-11-21 20:29:06', 'user_001', '2019-12-02 15:46:33', '3e79872d256a4ccca05fc598bfb6fa46', '1');
