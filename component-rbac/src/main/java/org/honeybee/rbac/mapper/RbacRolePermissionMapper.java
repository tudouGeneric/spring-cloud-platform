package org.honeybee.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.honeybee.rbac.entity.RbacRolePermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色权限关联dao
 */
@Repository
public interface RbacRolePermissionMapper extends BaseMapper<RbacRolePermission> {

    /**
     * 根据权限id集合删除
     * @param permissionIds
     */
    void deleteByPermissionIds(@Param("permissionIds") List<Long> permissionIds);

    /**
     * 根据角色id集合删除
     * @param roleIds
     */
    void deleteByRoleIds(@Param("roleIds") List<Long> roleIds);

}
