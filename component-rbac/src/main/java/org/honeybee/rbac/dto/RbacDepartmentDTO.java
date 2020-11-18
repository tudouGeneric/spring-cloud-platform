package org.honeybee.rbac.dto;

import lombok.Data;
import org.honeybee.rbac.valid.group.RbacDepartmentCreateValidGroup;
import org.honeybee.rbac.valid.group.RbacDepartmentUpdateValidGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RbacDepartmentDTO {

    @NotNull(message = "id不能为空", groups = {RbacDepartmentUpdateValidGroup.class})
    private Long id;

    /**
     * 编号
     */
    @NotBlank(message = "编码[code]不能为空", groups = {RbacDepartmentCreateValidGroup.class, RbacDepartmentUpdateValidGroup.class})
    private String code;

    /**
     * 名称
     */
    @NotBlank(message = "名称[name]不能为空", groups = {RbacDepartmentCreateValidGroup.class, RbacDepartmentUpdateValidGroup.class})
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 父部门id
     */
    @NotNull(message = "父部门id[parentId]不能为空", groups = {RbacDepartmentCreateValidGroup.class, RbacDepartmentUpdateValidGroup.class})
    private Long parentId;

}
