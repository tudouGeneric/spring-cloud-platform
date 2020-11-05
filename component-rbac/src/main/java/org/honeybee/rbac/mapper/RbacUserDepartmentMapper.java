package org.honeybee.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.honeybee.rbac.entity.RbacUserDepartment;
import org.springframework.stereotype.Repository;

/**
 * 用户权限关联表dao
 */
@Repository
public interface RbacUserDepartmentMapper extends BaseMapper<RbacUserDepartment> {
}
