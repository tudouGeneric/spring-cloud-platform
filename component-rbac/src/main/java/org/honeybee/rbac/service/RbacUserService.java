package org.honeybee.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.honeybee.base.vo.ResultVO;
import org.honeybee.rbac.dto.AttachUserRoleDTO;
import org.honeybee.rbac.dto.RbacUserDTO;
import org.honeybee.rbac.dto.RbacUserSearchDTO;
import org.honeybee.rbac.entity.RbacUser;
import org.honeybee.rbac.enums.UserEnableEnum;
import org.honeybee.rbac.pojo.JwtUser;
import org.honeybee.rbac.vo.UserVO;

import java.util.List;

/**
 * 用户 Service接口
 */
public interface RbacUserService extends IService<RbacUser> {

    /**
     * 根据条件查询用户
     * @param rbacUserSearchDTO
     * @return
     */
    IPage<UserVO> find(RbacUserSearchDTO rbacUserSearchDTO);

    /**
     * 注册用户
     * @param userDTO
     * @return
     */
    UserVO register(RbacUserDTO userDTO);

    /**
     * 创建用户
     * @param userDTO
     * @return
     */
    UserVO create(RbacUserDTO userDTO);

    /**
     * 更新用户
     * @param userDTO
     * @return
     */
    ResultVO update(RbacUserDTO userDTO);

    /**
     * 批量删除用户
     * @param userIds
     * @return
     */
    ResultVO delete(List<Long> userIds);

    /**
     * 根据id获取用户信息
     * @param userId
     * @return
     */
    UserVO getById(Long userId);

    /**
     * 获取当前用户信息
     * @return
     */
    JwtUser getCurrent();

    ResultVO attachUserRoles(AttachUserRoleDTO attachUserRoleDTO);

    /**
     * 更新用户启用/禁用状态
     * @param userIds
     * @param enableEnum
     * @return
     */
    ResultVO updateUsersEnableStatus(List<Long> userIds, UserEnableEnum enableEnum);

    /**
     * 重置用户密码为初始密码
     * @param userIds
     * @return
     */
    ResultVO resetPassword(List<Long> userIds);

}
