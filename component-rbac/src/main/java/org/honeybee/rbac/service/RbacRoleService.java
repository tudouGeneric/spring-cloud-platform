package org.honeybee.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.honeybee.base.vo.ResultVO;
import org.honeybee.rbac.dto.RbacRoleDTO;
import org.honeybee.rbac.dto.RbacRoleSearchDTO;
import org.honeybee.rbac.entity.RbacPermission;
import org.honeybee.rbac.entity.RbacRole;

import java.util.List;

/**
 * 角色 Service接口
 */
public interface RbacRoleService extends IService<RbacRole> {

    /**
     * 根据角色id查询角色
     * @param roleId
     * @return
     */
    RbacRole findByRoleId(Long roleId);

    /**
     * 根据用户id查询角色
     * @param userId
     * @return
     */
    List<RbacRole> findByUserId(Long userId);

    RbacRole create(RbacRoleDTO rbacRoleDTO);

    /**
     * 删除角色
     * @param roleIds role_id集合
     * @return
     */
    ResultVO deleteRoles(List<Long> roleIds);

    /**
     * 根据角色id查询拥有的权限
     * @param roleId
     * @return
     */
    List<RbacPermission> listPermissionsByRoleId(Long roleId);

    /**
     * 给角色分配权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    ResultVO attachRolePermissions(Long roleId, List<Long> permissionIds);

    /**
     * 根据条件分页查询角色
     * @param roleSearchDTO
     * @return
     */
    IPage<RbacRole> find(RbacRoleSearchDTO roleSearchDTO);

}
