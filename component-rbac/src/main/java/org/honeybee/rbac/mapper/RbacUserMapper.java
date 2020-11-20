package org.honeybee.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.honeybee.rbac.dto.RbacUserSearchDTO;
import org.honeybee.rbac.entity.RbacUser;
import org.honeybee.rbac.vo.UserVO;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

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

    /**
     * 分页查询用户
     * @param page
     * @param userSearchDTO
     * @return
     */
    IPage<UserVO> listUserByPage(Page page, @Param("userSearch") RbacUserSearchDTO userSearchDTO);

    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    UserVO findById(@Param("userId") Long userId);

    /**
     * 批量删除用户(逻辑删除)
     * @param userIds
     */
    void deleteByUserIds(@Param("userIds") Collection<Long> userIds);

    /**
     * 批量更新用户启用/禁用状态
     * @param userIds
     * @param enable
     */
    void updateEnableByUserIds(@Param("userIds") Collection<Long> userIds, @Param("enable") int enable);

    /**
     * 批量更新用户密码
     * @param password
     * @return
     */
    int updatePassword(@Param("password") String password, Collection<Long> userIds);

}
