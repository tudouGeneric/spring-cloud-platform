package org.honeybee.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.honeybee.base.constant.BaseConstant;
import org.honeybee.base.exception.BussinessException;
import org.honeybee.base.vo.ResultVO;
import org.honeybee.rbac.dto.RbacDepartmentDTO;
import org.honeybee.rbac.entity.RbacDepartment;
import org.honeybee.rbac.entity.RbacUser;
import org.honeybee.rbac.mapper.RbacDepartmentMapper;
import org.honeybee.rbac.service.RbacDepartmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RbacDepartmentServiceImpl extends ServiceImpl<RbacDepartmentMapper, RbacDepartment> implements RbacDepartmentService {

    @Autowired
    private RbacDepartmentMapper departmentMapper;

    /**
     * 部门树
     * @param departmentId
     * @return
     */
    @Override
    public List<RbacDepartment> getDepartmentTree(Long departmentId) {

        RbacDepartment dept = null;
        if (departmentId == null) {
            LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<RbacDepartment>()
                    .eq(RbacDepartment::getLevel, 0);
            dept = departmentMapper.selectOne(queryWrapper);
        } else {
            dept = departmentMapper.selectById(departmentId);
            if (dept == null) {    //查询不到部门
                throw new BussinessException("部门不存在, id={}" + departmentId);
            }
        }

        //所有的部门集合
        List<RbacDepartment> allList = departmentMapper.findAllLowerLevelByDepartment(dept);

        //最终的树形部门集合
        List<RbacDepartment> list = new ArrayList<>();

        //子部门集合
        List<RbacDepartment> childList = new ArrayList<>();

        //筛选出顶级部门和其他的子部门
        for (RbacDepartment rbacDepartment : allList) {
            if (rbacDepartment.getId().equals(dept.getId())) {
                list.add(rbacDepartment);
            } else {
                childList.add(rbacDepartment);
            }
        }

        //循环子部门放入父级部门中
        for (RbacDepartment childDepartment : childList) {
            for (RbacDepartment department : allList) {
                if (childDepartment.getParentId().equals(department.getId())) {
                    if (department.getChildList() == null) {
                        department.setChildList(new ArrayList<RbacDepartment>());
                    }
                    department.getChildList().add(childDepartment);
                    break;
                }
            }
        }

        return list;
    }

    @Override
    public ResultVO create(RbacDepartmentDTO departmentDTO) {
        //判断编号code是否重复
        QueryWrapper queryWrapper = new QueryWrapper<RbacDepartment>()
                .eq("code", departmentDTO.getCode());
        List<RbacDepartment> codeDepartmentList = departmentMapper.selectList(queryWrapper);
        if(CollectionUtils.isNotEmpty(codeDepartmentList)) {
            return new ResultVO(false, "部门编码[" + departmentDTO.getCode() + "]已存在");
        }
        
        RbacDepartment parentDepartment = null;
        if(departmentDTO.getParentId() == null) {
            departmentDTO.setParentId(0L);
        } else {
            if(!departmentDTO.getParentId().equals(0L)) {
                //校验父级部门是否存在
                parentDepartment = departmentMapper.selectById(departmentDTO.getParentId());
                if(parentDepartment == null) {
                    return new ResultVO(false, "父级部门不存在");
                }
            }
        }

        RbacDepartment rbacDepartment = new RbacDepartment();
        BeanUtils.copyProperties(departmentDTO, rbacDepartment, "id");
        if(parentDepartment == null && rbacDepartment.getParentId().equals(0L)) {    //如果是顶级部门
            rbacDepartment.setLevel(0);
            rbacDepartment.setPath(rbacDepartment.getCode());
        } else {    //根据父级部门设置level和path
            rbacDepartment.setLevel(parentDepartment.getLevel() + 1);
            rbacDepartment.setPath(parentDepartment.getPath() + BaseConstant.RBAC_DEPAERMENT_PATH_BREAK + rbacDepartment.getCode());
        }

        //保存至数据库
        departmentMapper.insert(rbacDepartment);
        return new ResultVO(true, "创建成功");
    }

}