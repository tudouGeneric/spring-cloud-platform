package org.honeybee.rbac.service;

import org.honeybee.rbac.dto.RbacUserDTO;
import org.honeybee.rbac.pojo.JwtUser;
import org.honeybee.rbac.vo.UserToken;
import org.honeybee.rbac.vo.UserVO;

public interface AuthService {

    /**
     * 注册用户
     * @return
     */
    UserVO register(RbacUserDTO registerDTO);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    UserToken login(String username, String password);

    /**
     * 登出
     * @param token
     */
    void logout(String token);

    /**
     * 刷新Token
     * @param oldToken
     * @return
     */
    UserToken refresh(String oldToken);

    /**
     * 根据Token获取用户信息
     * @param token
     * @return
     */
    JwtUser getUserByToken(String token);

}
