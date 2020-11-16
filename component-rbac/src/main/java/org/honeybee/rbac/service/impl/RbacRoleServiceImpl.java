package org.honeybee.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.honeybee.base.exception.BussinessException;
import org.honeybee.base.vo.ResultVO;
import org.honeybee.rbac.dto.RbacRoleDTO;
import org.honeybee.rbac.entity.RbacRole;
import org.honeybee.rbac.mapper.RbacRoleMapper;
import org.honeybee.rbac.mapper.RbacRolePermissionMapper;
import org.honeybee.rbac.mapper.RbacUserRoleMapper;
import org.honeybee.rbac.service.RbacRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class RbacRoleServiceImpl extends ServiceImpl<RbacRoleMapper, RbacRole> implements RbacRoleService {

    @Autowired
    private RbacRoleMapper rbacRoleMapper;

    @Autowired
    private RbacRolePermissionMapper rolePermissionMapper;

    @Autowired
    private RbacUserRoleMapper userRoleMapper;

    @Override
    public List<RbacRole> findByUserId(Long userId) {
        return rbacRoleMapper.findByUserId(userId);
    }

    @Override
    @Transactional
    public RbacRole create(RbacRoleDTO rbacRoleDTO) {
        //校验code不能重复
        rbacRoleDTO.setCode(rbacRoleDTO.getCode().toUpperCase());

        QueryWrapper codeQueryWrapper = new QueryWrapper<RbacRole>()
                .eq("code", rbacRoleDTO.getCode());
        List<RbacRole> codeRoleList = rbacRoleMapper.selectList(codeQueryWrapper);
        if(CollectionUtils.isNotEmpty(codeRoleList)) {
            throw new BussinessException("权限编码[" + rbacRoleDTO.getCode() + "]已存在");
        }

        RbacRole rbacRole = new RbacRole();
        BeanUtils.copyProperties(rbacRoleDTO, rbacRole, "id");
        //保存至数据库
        rbacRoleMapper.insert(rbacRole);
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
        rbacRoleMapper.deleteBatchIds(roleIds);

        return new ResultVO(true, "删除成功");
    }

}
