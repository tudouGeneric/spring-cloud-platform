package org.honeybee.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.honeybee.rbac.entity.RbacUserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色关联 dao
 */
@Repository
public interface RbacUserRoleMapper extends BaseMapper<RbacUserRole> {

    /**
     * 根据角色id集合删除
     * @param roleIds
     */
    void deleteByRoleIds(@Param("roleIds") List<Long> roleIds);

    /**
     * 根据用户id集合删除
     * @param userIds
     */
    void deleteByUserIds(@Param("userIds") List<Long> userIds);

}
