package org.honeybee.rbac.service.impl;

import cn.hutool.db.sql.Direction;
import cn.hutool.db.sql.Order;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.honeybee.base.exception.BussinessException;
import org.honeybee.base.vo.ResultVO;
import org.honeybee.rbac.dto.RbacRoleDTO;
import org.honeybee.rbac.dto.RbacRoleSearchDTO;
import org.honeybee.rbac.entity.RbacPermission;
import org.honeybee.rbac.entity.RbacRole;
import org.honeybee.rbac.entity.RbacRolePermission;
import org.honeybee.rbac.mapper.RbacPermissionMapper;
import org.honeybee.rbac.mapper.RbacRoleMapper;
import org.honeybee.rbac.mapper.RbacRolePermissionMapper;
import org.honeybee.rbac.mapper.RbacUserRoleMapper;
import org.honeybee.rbac.service.RbacRoleService;
import org.honeybee.rbac.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class RbacRoleServiceImpl extends ServiceImpl<RbacRoleMapper, RbacRole> implements RbacRoleService {

    @Autowired
    private RbacRoleMapper roleMapper;

    @Autowired
    private RbacRolePermissionMapper rolePermissionMapper;

    @Autowired
    private RbacUserRoleMapper userRoleMapper;

    @Autowired
    private RbacPermissionMapper permissionMapper;

    @Override
    public RbacRole findByRoleId(Long roleId) {
        QueryWrapper queryWrapper = new QueryWrapper<RbacRole>()
                .eq("delete_status", 0)
                .eq("id", roleId);
        RbacRole role = roleMapper.selectOne(queryWrapper);
        return role;
    }

    @Override
    public List<RbacRole> findByUserId(Long userId) {
        return roleMapper.findByUserId(userId);
    }

    @Override
    @Transactional
    public RbacRole create(RbacRoleDTO rbacRoleDTO) {
        //校验code不能重复
        rbacRoleDTO.setCode(rbacRoleDTO.getCode().toUpperCase());

        QueryWrapper codeQueryWrapper = new QueryWrapper<RbacRole>()
                .eq("code", rbacRoleDTO.getCode());
        List<RbacRole> codeRoleList = roleMapper.selectList(codeQueryWrapper);
        if(CollectionUtils.isNotEmpty(codeRoleList)) {
            throw new BussinessException("权限编码[" + rbacRoleDTO.getCode() + "]已存在");
        }

        RbacRole rbacRole = new RbacRole();
        BeanUtils.copyProperties(rbacRoleDTO, rbacRole, "id");
        //保存至数据库
        roleMapper.insert(rbacRole);
        return rbacRole;
    }

    @Override
    @Transactional
    public ResultVO deleteRoles(List<Long> roleIds) {
        if(CollectionUtils.isEmpty(roleIds)) {
            return new ResultVO(false, "删除的角色为空");
        }

        //删除角色权限关联表
        rolePermissionMapper.deleteByRoleIds(roleIds);
        //删除角色用户关联表
        userRoleMapper.deleteByRoleIds(roleIds);
        //删除角色表
        roleMapper.deleteBatchIds(roleIds);

        return new ResultVO(true, "删除成功");
    }

    @Override
    public List<RbacPermission> listPermissionsByRoleId(Long roleId) {
        RbacRole role = this.findByRoleId(roleId);
        if(role == null) {
            throw new BussinessException("角色[" + roleId + "]不存在");
        }

        //查询角色所拥有的所有权限集合
        List<RbacPermission> list = permissionMapper.findByRoleId(roleId);
        return list;
    }

    @Override
    @Transactional
    public ResultVO attachRolePermissions(Long roleId, List<Long> permissionIds) {
        //判断角色是否存在
        RbacRole role = this.findByRoleId(roleId);
        if(role == null) {
            throw new BussinessException("角色[" + roleId + "]不存在");
        }

        //删除原来的权限角色关联
        rolePermissionMapper.deleteByRoleIds(Arrays.asList(roleId));
        //新增绑定的权限
        for(Long permissionId : permissionIds) {
            RbacRolePermission rolePermission = new RbacRolePermission(roleId, permissionId);
            rolePermissionMapper.insert(rolePermission);
        }
        return new ResultVO(true, "成功");
    }

    @Override
    public IPage<RbacRole> find(RbacRoleSearchDTO roleSearchDTO) {
        QueryWrapper<RbacRole> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(roleSearchDTO.getCode())) {
            wrapper.like("code", roleSearchDTO.getCode());
        }
        if(StringUtils.isNotBlank(roleSearchDTO.getName())) {
            wrapper.like("name", roleSearchDTO.getName());
        }
        if(roleSearchDTO.getOrders() != null) {
            for(Order order : roleSearchDTO.getOrders()) {
                boolean asc = true;
                if(order.getDirection() != null && order.getDirection().equals(Direction.DESC)) {
                    asc = false;
                }
                wrapper.orderBy(true, asc, order.getField());
            }
        }

        Page page = new Page(roleSearchDTO.getPageNumber(), roleSearchDTO.getPageSize());
        IPage<RbacRole> rbacUserPage = roleMapper.selectPage(page, wrapper);
        return rbacUserPage;
    }

}
