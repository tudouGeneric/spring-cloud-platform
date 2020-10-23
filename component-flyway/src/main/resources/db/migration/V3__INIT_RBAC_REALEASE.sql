-- --------------------------------
-- 用户表新增
-- --------------------------------
CREATE TABLE IF NOT EXISTS `rbac_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `age` int(5) DEFAULT NULL COMMENT '年龄',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `sex` char(1) DEFAULT NULL COMMENT '性别',
  `birth` datetime DEFAULT NULL COMMENT '生日',
  `account` varchar(20)  NOT NULL COMMENT '账号',
  `password` varchar(500) NOT NULL COMMENT '密码',
  `nick_name` varchar(20)  COMMENT '昵称',
  `email` varchar(100)  COMMENT '邮箱',
  `mobile_phone` varchar(20) COMMENT '手机',
  `photo_url` varchar(500) COMMENT '头像地址',
  `last_password_reset_date` datetime DEFAULT NULL COMMENT '密码更新时间',
  `delete_status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '删除状态（0：未删除 [默认] ；1：已删除）',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT 0 COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
 
 
 
-- 建表sql（role）
CREATE TABLE IF NOT EXISTS `rbac_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `code` varchar(50)  NOT NULL COMMENT '代码',
  `name` varchar(100)  NOT NULL COMMENT '名称',
  `description` varchar(100)  COMMENT '描述',
  `delete_status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '删除状态（0：未删除 [默认] ；1：已删除）',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT 0 COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';
 
 
-- 建表sql（permission）
CREATE TABLE IF NOT EXISTS `rbac_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `code` varchar(50)  NOT NULL COMMENT '代码',
  `name` varchar(100)  NOT NULL COMMENT '名称',
  `description` varchar(100) COMMENT '描述',
 `delete_status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '删除状态（0：未删除 [默认] ；1：已删除）',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT 0 COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';
 
 
-- 建表sql（user_role）
CREATE TABLE IF NOT EXISTS `rbac_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';
 
 
-- 建表sql（role_permission）
CREATE TABLE IF NOT EXISTS `rbac_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';
