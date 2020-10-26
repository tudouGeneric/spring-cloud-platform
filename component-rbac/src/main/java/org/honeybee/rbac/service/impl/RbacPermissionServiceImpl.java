package org.honeybee.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.honeybee.rbac.entity.RbacPermission;
import org.honeybee.rbac.mapper.RbacPermissionMapper;
import org.honeybee.rbac.service.RbacPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RbacPermissionServiceImpl extends ServiceImpl<RbacPermissionMapper, RbacPermission> implements RbacPermissionService {

    @Autowired
    private RbacPermissionMapper rbacPermissionMapper;

    @Override
    public List<RbacPermission> findByRoleIds(List<Long> roleIds) {
        return rbacPermissionMapper.findByRoleIds(roleIds);
    }

}
