package org.honeybee.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.honeybee.rbac.entity.RbacUser;
import org.springframework.stereotype.Repository;

/**
 * 用户操作dao
 */
@Repository
public interface RbacUserMapper extends BaseMapper<RbacUser> {

    /**
     * 根据账号获取用户
     * @param account
     * @return
     */
    RbacUser getByAccount(@Param("account")String account);

}
