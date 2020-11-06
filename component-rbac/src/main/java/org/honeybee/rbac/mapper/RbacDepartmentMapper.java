package org.honeybee.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.honeybee.rbac.entity.RbacDepartment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门表相关dao
 */
@Repository
public interface RbacDepartmentMapper extends BaseMapper<RbacDepartment> {

    /**
     * 获取部门和所有子部门集合
     * @return
     */
    List<RbacDepartment> findAllLowerLevelByDepartment(@Param("department") RbacDepartment department);

}
