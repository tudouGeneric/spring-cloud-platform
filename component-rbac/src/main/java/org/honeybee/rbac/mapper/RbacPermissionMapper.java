package org.honeybee.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.honeybee.rbac.entity.RbacPermission;
import org.honeybee.rbac.enums.PermissionTypeEnum;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限相关dao
 */
@Repository
public interface RbacPermissionMapper extends BaseMapper<RbacPermission> {

    /**
     * 根据角色和权限类型查询权限
     * @param roleIds 角色id集合
     * @param type 权限类型
     * @return
     */
    List<RbacPermission> findByRoleIdsAndType(@Param("roleIds") List<Long> roleIds, @Param("type") int type);

    /**
     * 根据权限id查询自身和所有的子节点
     * @return
     */
    List<RbacPermission> listSelfAndChildNodesById(@Param("permissionId") Long permissionId);

    /**
     * 根据角色id查询所拥有的权限集合
     * @param roleId
     * @return
     */
    List<RbacPermission> findByRoleId(@Param("roleId") Long roleId);

}
