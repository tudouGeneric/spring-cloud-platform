package org.honeybee.rbac.dto;

import lombok.Data;
import org.honeybee.rbac.valid.group.RbacRoleCreateValidGroup;

import javax.validation.constraints.NotBlank;

@Data
public class RbacRoleDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 编码
     */
    @NotBlank(message = "编码[code]不能为空", groups = {RbacRoleCreateValidGroup.class})
    private String code;

    /**
     * 名称
     */
    @NotBlank(message = "名称[name]不能为空", groups = {RbacRoleCreateValidGroup.class})
    private String name;

    /**
     * 描述
     */
    private String description;

}
