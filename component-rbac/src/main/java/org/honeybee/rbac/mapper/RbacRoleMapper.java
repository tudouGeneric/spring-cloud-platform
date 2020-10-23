package org.honeybee.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.honeybee.rbac.entity.RbacRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色相关dao
 */
@Repository
public interface RbacRoleMapper extends BaseMapper<RbacRole> {

    /**
     * 根据code查询角色
     * @param codes
     * @return
     */
    List<RbacRole> findByCodeIn(@Param("codes") List<String> codes);

    /**
     * 根据用户查询其拥有的角色
     * @param userId
     * @return
     */
    List<RbacRole> findByUserId(@Param("userId") Long userId);

}
