package org.honeybee.rbac.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.honeybee.rbac.entity.RbacPermission;
import org.honeybee.rbac.entity.RbacRole;
import org.honeybee.rbac.entity.RbacUser;
import org.honeybee.rbac.mapper.RbacPermissionMapper;
import org.honeybee.rbac.mapper.RbacRoleMapper;
import org.honeybee.rbac.mapper.RbacUserMapper;
import org.honeybee.rbac.pojo.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义实现UserDetailService
 */
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private RbacUserMapper rbacUserMapper;

    @Autowired
    private RbacRoleMapper rbacRoleMapper;

    @Autowired
    private RbacPermissionMapper rbacPermissionMapper;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        //获取用户信息
        RbacUser rbacUser = rbacUserMapper.getByAccount(account);
        if(rbacUser == null) {
            throw new UsernameNotFoundException(account + ":该账号用户不存在");
        }
        //角色code和权限code添加到authorities中
        Set<String> authorities = new HashSet<>();
        //获取所有权限添加进去
        List<RbacRole> rbacRoles = rbacRoleMapper.findByUserId(rbacUser.getId());
        // hasRole()源码里面是通过拼接上前缀 ROLE_进行匹配角色的
        Set<String> roleCodes = rbacRoles.stream().map(e -> "ROLE_" + e.getCode()).collect(Collectors.toSet());
        if(CollectionUtils.isNotEmpty(roleCodes)) {
            authorities.addAll(roleCodes);
            List<Long> roleIds = rbacRoles.stream().map(e -> e.getId()).collect(Collectors.toList());
            //得到用户角色的所有权限
            List<RbacPermission> permissions = rbacPermissionMapper.findByRoleIds(roleIds);
            Set<String> permissionCodes = permissions.stream().map(e -> e.getCode()).collect(Collectors.toSet());
            if(CollectionUtils.isNotEmpty(permissionCodes)) {
                authorities.addAll(permissionCodes);
            }
        }

        rbacUser.setAuthorities(authorities);
        JwtUser jwtUser = new JwtUser(rbacUser);
        return jwtUser;
    }

}
