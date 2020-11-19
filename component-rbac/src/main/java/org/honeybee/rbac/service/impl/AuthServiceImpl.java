package org.honeybee.rbac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.honeybee.base.constant.BaseConstant;
import org.honeybee.cache.util.RedisUtil;
import org.honeybee.rbac.dto.RbacUserDTO;
import org.honeybee.rbac.pojo.JwtUser;
import org.honeybee.rbac.service.AuthService;
import org.honeybee.rbac.service.RbacUserService;
import org.honeybee.rbac.util.JwtUtil;
import org.honeybee.rbac.vo.UserToken;
import org.honeybee.rbac.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final RbacUserService rbacUserService;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           @Qualifier("userDetailsService") UserDetailsService userDetailsService,
                           RbacUserService rbacUserService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.rbacUserService = rbacUserService;
    }

    @Override
    public UserVO register(RbacUserDTO registerDTO) {
        return rbacUserService.create(registerDTO);
    }

    @Override
    public UserToken login(String username, String password) {
        //用户验证
         final Authentication authentication = this.authenticate(username, password);
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        final JwtUser userDetail = (JwtUser) authentication.getPrincipal();
        final String token = JwtUtil.generateAccessToken(userDetail);

        return new UserToken(token, userDetail.getId(), userDetail.getName());
    }

    @Override
    public void logout(String token) {
        String userName = JwtUtil.getUsernameFromToken(token);
        //清除token
        if(RedisUtil.exists(BaseConstant.JWT_KEY_START + userName)) {
            RedisUtil.delete(BaseConstant.JWT_KEY_START + userName);
        }
    }

    @Override
    public UserToken refresh(String oldToken) {
        JwtUser jwtUser = JwtUtil.getUserFromToken(oldToken);
        String token = JwtUtil.refreshToken(oldToken);
        return new UserToken(token, jwtUser.getId(), jwtUser.getName());
    }

    @Override
    public JwtUser getUserByToken(String token) {
        return JwtUtil.getUserFromToken(token);
    }

    private Authentication authenticate(String username, String password) {
        //该方法会去调用userDetailsService.loadUserByUsername()去验证用户名和密码，如果正确，则存储该用户名密码到“security 的 context中”
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
