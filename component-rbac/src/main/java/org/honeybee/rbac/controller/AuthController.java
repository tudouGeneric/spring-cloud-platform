package org.honeybee.rbac.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.rbac.dto.RbacUserDTO;
import org.honeybee.rbac.pojo.JwtUser;
import org.honeybee.rbac.service.AuthService;
import org.honeybee.rbac.valid.group.RbacUserLoginValidGroup;
import org.honeybee.rbac.vo.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@Api(tags = "权限相关接口")
public class AuthController {

    @Value("${auth.jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/login")
    @ApiOperation(value = "用户登录", notes = "登录成功返回token")
    public ResponseMessage<UserToken> login(@RequestBody @Validated(RbacUserLoginValidGroup.class) RbacUserDTO userDTO) {
       UserToken response = authService.login(userDTO.getAccount(), userDTO.getPassword());
       return ResponseMessage.success("登录成功", response);
    }

    @GetMapping(value = "/logout")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "用户登出")
    public ResponseMessage logout(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        authService.logout(token);
        return ResponseMessage.success("登出成功");
    }

    @GetMapping(value = "/user")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "根据token获取用户信息")
    public ResponseMessage<JwtUser> getUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        JwtUser userDetail = authService.getUserByToken(token);
        return ResponseMessage.success("返回成功", userDetail);
    }

    /*@PostMapping(value = "/register")
    @ApiOperation(value = "用户注册")
    public ResponseMessage<UserVO> sign(@RequestBody @Validated(RbacUserCreateValidGroup.class) RbacUserDTO userDTO) {
        UserVO userVO = authService.register(userDTO);
        return ResponseMessage.success("注册成功", userVO);
    }*/

    @GetMapping(value = "/refresh")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "刷新token")
    public ResponseMessage<UserToken> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        UserToken response = authService.refresh(token);
        if(response == null) {
            return ResponseMessage.fail("刷新失败:token无效");
        }
        return ResponseMessage.success("刷新成功", response);
    }

}
