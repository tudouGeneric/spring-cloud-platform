package org.honeybee.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.honeybee.rbac.entity.RbacPermission;
import org.honeybee.rbac.enums.PermissionTypeEnum;

import java.util.List;

/**
 * 权限 Service接口
 */
public interface RbacPermissionService extends IService<RbacPermission> {

    List<RbacPermission> findByRoleIdsAndType(List<Long> roleIds, PermissionTypeEnum typeEnum);

}
