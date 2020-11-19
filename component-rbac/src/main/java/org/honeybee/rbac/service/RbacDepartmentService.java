package org.honeybee.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.honeybee.base.vo.ResultVO;
import org.honeybee.rbac.dto.RbacDepartmentDTO;
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

    /**
     * 创建部门
     * @param departmentDTO
     * @return
     */
    ResultVO create(RbacDepartmentDTO departmentDTO);

    /**
     * 根据id删除部门
     * @param departmentId
     * @return
     */
    ResultVO deleteById(Long departmentId);

}
