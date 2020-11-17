package org.honeybee.rbac.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 给用户分配角色的DTO
 */
@Data
public class AttachUserRoleDTO {

    @NotNull(message = "用户id[userId]不能为空")
    private Long userId;

    @NotNull(message = "角色id集合[roleIds]不能为空")
    private List<Long> roleIds;

}
