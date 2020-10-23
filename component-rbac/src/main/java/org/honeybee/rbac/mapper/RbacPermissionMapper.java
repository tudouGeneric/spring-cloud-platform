package org.honeybee.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.honeybee.rbac.entity.RbacPermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限相关dao
 */
@Repository
public interface RbacPermissionMapper extends BaseMapper<RbacPermission> {

    /**
     * 根据角色查询权限
     * @param roleIds
     * @return
     */
    List<RbacPermission> findByRoleIds(@Param("roleIds") List<Long> roleIds);

}
