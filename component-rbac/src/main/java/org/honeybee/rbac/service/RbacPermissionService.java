package org.honeybee.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.honeybee.base.vo.ResultVO;
import org.honeybee.rbac.dto.RbacPermissionDTO;
import org.honeybee.rbac.entity.RbacPermission;
import org.honeybee.rbac.enums.PermissionTypeEnum;

import java.util.List;

/**
 * 权限 Service接口
 */
public interface RbacPermissionService extends IService<RbacPermission> {

    /**
     * 根据角色和权限类型查询角色拥有的权限集合
     * @param roleIds
     * @param typeEnum
     * @return
     */
    List<RbacPermission> findByRoleIdsAndType(List<Long> roleIds, PermissionTypeEnum typeEnum);

    /**
     * 创建权限
     * @param rbacPermissionDTO
     * @return
     */
    RbacPermission create(RbacPermissionDTO rbacPermissionDTO);

    /**
     * 删除权限
     * @param permissionIds
     * @return
     */
    ResultVO deletePermissions(List<Long> permissionIds);

    /**
     * 根据权限id查询下级权限树
     * @param permissionId
     * @return
     */
    List<RbacPermission> listSelfAndChildNodesTreeById(Long permissionId);

}
