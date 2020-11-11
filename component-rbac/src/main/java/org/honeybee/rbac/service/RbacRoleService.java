package org.honeybee.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.honeybee.rbac.dto.RbacRoleDTO;
import org.honeybee.rbac.entity.RbacRole;

import java.util.List;

/**
 * 角色 Service接口
 */
public interface RbacRoleService extends IService<RbacRole> {

    /**
     * 根据用户id查询角色
     * @param userId
     * @return
     */
    List<RbacRole> findByUserId(Long userId);

    RbacRole create(RbacRoleDTO rbacRoleDTO);

}
