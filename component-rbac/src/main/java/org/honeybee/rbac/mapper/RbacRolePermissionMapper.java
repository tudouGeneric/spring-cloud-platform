package org.honeybee.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.honeybee.rbac.entity.RbacRolePermission;
import org.springframework.stereotype.Repository;

/**
 * 角色权限关联dao
 */
@Repository
public interface RbacRolePermissionMapper extends BaseMapper<RbacRolePermission> {

}
