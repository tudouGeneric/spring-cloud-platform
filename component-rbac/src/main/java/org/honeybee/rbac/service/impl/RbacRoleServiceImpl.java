package org.honeybee.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.honeybee.rbac.entity.RbacRole;
import org.honeybee.rbac.mapper.RbacRoleMapper;
import org.honeybee.rbac.service.RbacRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RbacRoleServiceImpl extends ServiceImpl<RbacRoleMapper, RbacRole> implements RbacRoleService {

    @Autowired
    private RbacRoleMapper rbacRoleMapper;

    @Override
    public List<RbacRole> findByUserId(Long userId) {
        return rbacRoleMapper.findByUserId(userId);
    }

}
