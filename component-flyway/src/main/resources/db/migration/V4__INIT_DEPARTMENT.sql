-- --------------------------------
-- 部门表新增
-- --------------------------------
CREATE TABLE IF NOT EXISTS `rbac_department`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门code',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门级联路径(格式：父code_当前code)',
  `parent_id` bigint(20) NOT NULL COMMENT '父级部门id',
  `level` int(11) NOT NULL COMMENT '部门层级',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `delete_status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '删除状态（0：未删除 [默认] ；1：已删除）',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;


CREATE TABLE IF NOT EXISTS `rbac_user_department`   (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `department_id` bigint(20) NOT NULL COMMENT '部门id',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `rbac_department`(`id`, `name`, `code`, `path`, `parent_id`, `level`, `description`,`delete_status`, `created_by`, `created_time`, `updated_by`, `updated_time`, `version`) VALUES (1, '顶级部门', 'top', 'top', 0, 0, '默认', 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `rbac_user_department`(`id`, `user_id`, `department_id`, `created_by`, `created_time`, `updated_by`, `updated_time`, `version`) VALUES (1, 1, 1, NULL, NULL, NULL, NULL, 0);

 
