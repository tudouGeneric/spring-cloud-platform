package org.honeybee.rbac.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 给角色分配权限的DTO
 */
@Data
public class AttachRolePermissionDTO {

    @NotNull(message = "角色id[roleId]不能为空")
    private Long roleId;

    @NotNull(message = "权限id集合[permissionIds]不能为空")
    private List<Long> permissionIds;

}
