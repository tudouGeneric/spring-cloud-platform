package org.honeybee.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.honeybee.base.entity.BaseEntity;
import org.honeybee.base.enums.DeleteStatusEnum;
import org.honeybee.rbac.enums.PermissionTypeEnum;

/**
 * 权限表
 */
@Data
@TableName(value = "rbac_permission")
public class RbacPermission extends BaseEntity {

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 代码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 权限类型
     */
    private PermissionTypeEnum type;

    /**
     * 描述
     */
    private String description;

    /**
     * 配置值
     */
    private String config;

    /**
     * 删除状态（0：未删除 [默认] ；1：已删除）
     */
    private DeleteStatusEnum deleteStatus = DeleteStatusEnum.EXISTED;

}
