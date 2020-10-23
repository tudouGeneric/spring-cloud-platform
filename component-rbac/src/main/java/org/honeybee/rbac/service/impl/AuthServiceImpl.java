package org.honeybee.rbac.service.impl;

import org.honeybee.rbac.dto.RbacUserDTO;
import org.honeybee.rbac.pojo.JwtUser;
import org.honeybee.rbac.service.AuthService;
import org.honeybee.rbac.vo.UserToken;
import org.honeybee.rbac.vo.UserVO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;

    @Override
    public UserVO register(RbacUserDTO registerDTO) {

        return null;
    }

    @Override
    public UserToken login(String username, String password) {
        return null;
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public UserToken refresh(String oldToken) {
        return null;
    }

    @Override
    public JwtUser getUserByToken(String token) {
        return null;
    }

}
