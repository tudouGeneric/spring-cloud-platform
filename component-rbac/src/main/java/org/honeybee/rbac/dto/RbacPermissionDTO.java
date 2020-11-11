package org.honeybee.rbac.dto;

import lombok.Data;
import org.honeybee.rbac.enums.PermissionTypeEnum;
import org.honeybee.rbac.valid.group.RbacPermissionCreateValidGroup;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RbacPermissionDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 父id
     */
    @NotNull(message = "父id[parentId]不能为空", groups = {RbacPermissionCreateValidGroup.class})
    @Min(value = 0, message = "父id[parentId]不能小于0",groups = {RbacPermissionCreateValidGroup.class})
    private Long parentId;

    /**
     * 代码
     */
    @NotBlank(message = "编码[code]不能为空", groups = {RbacPermissionCreateValidGroup.class})
    private String code;

    /**
     * 名称
     */
    @NotBlank(message = "名称[name]不能为空", groups = {RbacPermissionCreateValidGroup.class})
    private String name;

    /**
     * 权限类型
     */
    @NotNull(message = "权限类型[type]不能为空", groups = {RbacPermissionCreateValidGroup.class})
    private PermissionTypeEnum type;

    /**
     * 描述
     */
    private String description;

    /**
     * 配置值
     */
    private String config;
}
