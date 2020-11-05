package org.honeybee.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.honeybee.base.entity.BaseEntity;

/**
 * 用户部门关联表
 */
@Data
@TableName(value = "rbac_user_department")
public class RbacUserDepartment extends BaseEntity {

    //用户id
    private Long userId;

    //部门id
    private Long departmentId;

}
