package org.honeybee.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.honeybee.base.exception.BussinessException;
import org.honeybee.base.vo.ResultVO;
import org.honeybee.rbac.dto.RbacPermissionDTO;
import org.honeybee.rbac.entity.RbacPermission;
import org.honeybee.rbac.enums.PermissionTypeEnum;
import org.honeybee.rbac.mapper.RbacPermissionMapper;
import org.honeybee.rbac.service.RbacPermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RbacPermissionServiceImpl extends ServiceImpl<RbacPermissionMapper, RbacPermission> implements RbacPermissionService {

    @Autowired
    private RbacPermissionMapper rbacPermissionMapper;

    @Override
    public List<RbacPermission> findByRoleIdsAndType(List<Long> roleIds, PermissionTypeEnum typeEnum) {
        return rbacPermissionMapper.findByRoleIdsAndType(roleIds, typeEnum.getCode());
    }

    @Override
    public RbacPermission create(RbacPermissionDTO rbacPermissionDTO) {
        //校验逻辑
        Long parentId = rbacPermissionDTO.getParentId();
        if(parentId == 0 && rbacPermissionDTO.getType().equals(PermissionTypeEnum.INTERFACE)) {     //如果是最上级的权限,必须是菜单
            throw new BussinessException("最上级的权限类型必须是菜单");
        }

        //所有权限的上级必须是菜单
        RbacPermission parentPermission = rbacPermissionMapper.selectById(parentId);
        if(!parentPermission.getType().equals(PermissionTypeEnum.MENU)) {
            throw new BussinessException("权限的父级必须是菜单类型");
        }

        //将code统一转为大写, code编码必须唯一
        rbacPermissionDTO.setCode(rbacPermissionDTO.getCode().toUpperCase());
        QueryWrapper codeQueryWrapper = new QueryWrapper<RbacPermission>()
                .eq("code", rbacPermissionDTO.getCode());
        List<RbacPermission> codeSameList = rbacPermissionMapper.selectList(codeQueryWrapper);
        if(CollectionUtils.isNotEmpty(codeSameList)) {
            throw new BussinessException("权限编码[" + rbacPermissionDTO.getCode() + "]已存在");
        }

        //保存至数据库
        RbacPermission rbacPermission = new RbacPermission();
        BeanUtils.copyProperties(rbacPermissionDTO, rbacPermission, "id");
        rbacPermissionMapper.insert(rbacPermission);
        return rbacPermission;
    }

    @Override
    public ResultVO deletePermissions(List<Long> permissionIds) {
        for(Long permissionId : permissionIds) {
             rbacPermissionMapper.listSelfAndChildNodesById(permissionId);
        }



        return null;
    }

    @Override
    public List<RbacPermission> listSelfAndChildNodesTreeById(Long permissionId) {
        if(permissionId == null) {  //如果id是空的,默认为顶级权限
            QueryWrapper queryWrapper = new QueryWrapper<RbacPermission>()
                    .eq("parent_id", 0);
            RbacPermission topPermission = rbacPermissionMapper.selectOne(queryWrapper);
            if(topPermission == null) {
                throw new BussinessException("查询失败:没有顶级权限");
            } else {
                permissionId = topPermission.getId();
            }
        }

        //根据id查询本节点和所有子节点的权限集合
        List<RbacPermission> allList = rbacPermissionMapper.listSelfAndChildNodesById(permissionId);
        //最终的树形权限集合
        List<RbacPermission> list = new ArrayList<>();
        //子权限集合
        List<RbacPermission> childList = new ArrayList<>();

        //筛选出根节点和其他子节点
        for (RbacPermission rbacPermission : allList) {
            if (rbacPermission.getId().equals(permissionId)) {
                list.add(rbacPermission);
            } else {
                childList.add(rbacPermission);
            }
        }

        //循环子部门放入父级部门中
        for (RbacPermission childPermission : childList) {
            for (RbacPermission permission : allList) {
                if (childPermission.getParentId().equals(permission.getId())) {
                    if (permission.getChildList() == null) {
                        permission.setChildList(new ArrayList<RbacPermission>());
                    }
                    permission.getChildList().add(childPermission);
                    break;
                }
            }
        }

        return list;
    }

}
