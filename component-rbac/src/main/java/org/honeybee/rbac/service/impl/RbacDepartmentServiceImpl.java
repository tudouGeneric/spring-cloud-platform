package org.honeybee.rbac.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.honeybee.rbac.entity.RbacDepartment;
import org.honeybee.rbac.mapper.RbacDepartmentMapper;
import org.honeybee.rbac.service.RbacDepartmentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RbacDepartmentServiceImpl extends ServiceImpl<RbacDepartmentMapper, RbacDepartment> implements RbacDepartmentService {

    public static void main(String[] args) {
        //所有的部门集合
        List<RbacDepartment> allList = new ArrayList<>();

        //最终的树形部门集合
        List<RbacDepartment> list = new ArrayList<>();

        //子部门集合
        List<RbacDepartment> childList = new ArrayList<>();

        //筛选出一级部门和其他的子部门
        for(RbacDepartment rbacDepartment : allList) {
            if(rbacDepartment.getParentId() == null || rbacDepartment.getParentId().equals(0L)) {
                list.add(rbacDepartment);
            } else {
                childList.add(rbacDepartment);
            }
        }

        //循环子部门
        for(RbacDepartment childDepartment : childList) {
            for(RbacDepartment department : allList) {
                if(childDepartment.getParentId().equals(department.getId())) {
                    if(department.getChildList() == null) {
                        department.setChildList(new ArrayList<RbacDepartment>());
                    }
                }
                department.getChildList().add(childDepartment);
                break;
            }

        }


    }

}
