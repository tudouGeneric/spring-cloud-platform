package org.honeybee.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.honeybee.base.entity.BaseEntity;
import org.honeybee.base.enums.DeleteStatusEnum;

import java.beans.Transient;
import java.util.List;

/**
 * 部门表
 */
@Data
@TableName(value = "rbac_department")
public class RbacDepartment extends BaseEntity {

    //编号
    private String code;

    //名称
    private String name;

    //描述
    private String description;

    //部门级联路径(格式：父code_当前code)
    private String path;

    //父级部门id
    private Long parentId;

    //部门等级
    private Integer level;

    /**
     * 删除状态（0：未删除 [默认] ；1：已删除）
     */
    private DeleteStatusEnum deleteStatus = DeleteStatusEnum.EXISTED;

    @TableField(exist = false)
    private List<RbacDepartment> childList;

}
