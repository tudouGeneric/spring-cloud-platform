package org.honeybee.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.honeybee.rbac.entity.RbacDepartment;

import java.util.List;

/**
 * 部门 service接口
 */
public interface RbacDepartmentService extends IService<RbacDepartment> {

    /**
     * 根据部门id获取这个部门的部门树,如果id为空,则获取所有的部门树
     * @param departmentId
     */
    List<RbacDepartment> getDepartmentTree(Long departmentId);

}
