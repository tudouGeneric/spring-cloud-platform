package org.honeybee.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.honeybee.rbac.entity.RbacUserRole;
import org.springframework.stereotype.Repository;

/**
 * 用户角色关联 dao
 */
@Repository
public interface RbacUserRoleMapper extends BaseMapper<RbacUserRole> {
}
