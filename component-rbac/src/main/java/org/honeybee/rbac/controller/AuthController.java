package org.honeybee.rbac.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.base.vo.ResultVO;
import org.honeybee.rbac.dto.RbacUserDTO;
import org.honeybee.rbac.dto.UserUpdatePasswordDTO;
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
@Api(tags = "用户验证相关接口")
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

    @PostMapping(value = "/logout")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "用户登出")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage logout(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        if(StringUtils.isBlank(token)) {
            return ResponseMessage.fail("获取token失败");
        }
        authService.logout(token);
        return ResponseMessage.success("登出成功");
    }

    @GetMapping(value = "/user")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "根据token获取用户信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage<JwtUser> getUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        JwtUser userDetail = authService.getUserByToken(token);
        return ResponseMessage.success("返回成功", userDetail);
    }

    @PostMapping(value = "/refresh")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "刷新token")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage<UserToken> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        if(StringUtils.isBlank(token)) {
            return ResponseMessage.fail("获取token失败");
        }
        UserToken response = authService.refresh(token);
        if(response == null) {
            return ResponseMessage.fail("刷新失败:token无效");
        }
        return ResponseMessage.success("刷新成功", response);
    }

    @PostMapping(value = "/update/password")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "用户修改密码")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage updatePassword(HttpServletRequest request, @RequestBody @Validated UserUpdatePasswordDTO userUpdatePasswordDTO) {
        String token = request.getHeader(tokenHeader);
        if(StringUtils.isBlank(token)) {
            return ResponseMessage.fail("获取token失败");
        }
        JwtUser userDetail = authService.getUserByToken(token);
        if(userDetail == null) {
            return ResponseMessage.fail("解析token失败");
        }

        return ResultVO.getResponseMessage(authService.updatePassword(userUpdatePasswordDTO, userDetail));
    }

}
