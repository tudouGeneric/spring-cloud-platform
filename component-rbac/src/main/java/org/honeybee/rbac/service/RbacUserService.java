package org.honeybee.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.honeybee.base.vo.ResultVO;
import org.honeybee.rbac.dto.AttachUserRoleDTO;
import org.honeybee.rbac.dto.RbacUserDTO;
import org.honeybee.rbac.dto.RbacUserSearchDTO;
import org.honeybee.rbac.entity.RbacUser;
import org.honeybee.rbac.pojo.JwtUser;
import org.honeybee.rbac.vo.UserVO;

import java.util.List;

/**
 * 用户 Service接口
 */
public interface RbacUserService extends IService<RbacUser> {

    /**
     * 根据账号获取用户
     * @param account
     * @return
     */
    UserVO getByAccount(String account);

    /**
     * 查询所有用户
     * @return
     */
    List<UserVO> findAll();

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
    int update(RbacUserDTO userDTO);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    int delete(Long userId);

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

}
