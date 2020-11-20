package org.honeybee.rbac.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.base.constant.BaseConstant;
import org.honeybee.base.vo.ResultVO;
import org.honeybee.rbac.dto.AttachUserRoleDTO;
import org.honeybee.rbac.dto.RbacUserDTO;
import org.honeybee.rbac.dto.RbacUserSearchDTO;
import org.honeybee.rbac.enums.UserEnableEnum;
import org.honeybee.rbac.service.RbacUserService;
import org.honeybee.rbac.valid.group.RbacUserCreateValidGroup;
import org.honeybee.rbac.valid.group.RbacUserUpdateValidGroup;
import org.honeybee.rbac.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private RbacUserService rbacUserService;

    @PostMapping("/page")
    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "根据条件分页查询用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage find(@RequestBody RbacUserSearchDTO rbacUserSearchDTO) {
        IPage<UserVO> pageResult = rbacUserService.find(rbacUserSearchDTO);
        return ResponseMessage.success(null, pageResult);
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = BaseConstant.SUPER_ADMIN_ROLE_AUTHORITY + "hasAuthority('USER')")
    @ApiOperation(value = "根据id查询用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage<UserVO> getDetail(@PathVariable Long id) {
       return ResponseMessage.success(null, rbacUserService.getById(id));
    }

    @PostMapping("/create")
    @PreAuthorize(value = BaseConstant.SUPER_ADMIN_ROLE_AUTHORITY + "hasAuthority('USER')")
    @ApiOperation(value = "创建用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage create(@RequestBody @Validated(RbacUserCreateValidGroup.class) RbacUserDTO userDTO) {
        return ResponseMessage.success("创建成功", rbacUserService.create(userDTO));
    }

    @PostMapping("/update")
    @PreAuthorize(value = BaseConstant.SUPER_ADMIN_ROLE_AUTHORITY + "hasAuthority('USER')")
    @ApiOperation(value = "更新用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage update(@RequestBody @Validated(RbacUserUpdateValidGroup.class) RbacUserDTO userDTO) {
        return ResultVO.getResponseMessage(rbacUserService.update(userDTO));
    }

    @PostMapping("/enable")
    @PreAuthorize(value = BaseConstant.SUPER_ADMIN_ROLE_AUTHORITY + "hasAuthority('USER')")
    @ApiOperation(value = "启用用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage enableUsers(@RequestBody List<Long> userIds) {
        return ResultVO.getResponseMessage(rbacUserService.updateUsersEnableStatus(userIds, UserEnableEnum.ENABLE));
    }

    @PostMapping("/disable")
    @PreAuthorize(value = BaseConstant.SUPER_ADMIN_ROLE_AUTHORITY + "hasAuthority('USER')")
    @ApiOperation(value = "禁用用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage disableUsers(@RequestBody List<Long> userIds) {
        return ResultVO.getResponseMessage(rbacUserService.updateUsersEnableStatus(userIds, UserEnableEnum.DISABLE));
    }

    @DeleteMapping("/delete")
    @PreAuthorize(value = BaseConstant.SUPER_ADMIN_ROLE_AUTHORITY + "hasAuthority('USER')")
    @ApiOperation(value = "删除用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage delete(@RequestBody List<Long> userIds) {
        return ResultVO.getResponseMessage(rbacUserService.delete(userIds));
    }

    @PostMapping("/attachUserRoles")
    @PreAuthorize(value = BaseConstant.SUPER_ADMIN_ROLE_AUTHORITY + "hasAuthority('USER')")
    @ApiOperation(value = "给用户分配角色")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage attachUserRoles(@RequestBody @Validated AttachUserRoleDTO attachUserRoleDTO) {
        ResultVO result = rbacUserService.attachUserRoles(attachUserRoleDTO);
        return ResultVO.getResponseMessage(result);
    }

    @PostMapping("/reset/password")
    @PreAuthorize(value = BaseConstant.SUPER_ADMIN_ROLE_AUTHORITY + "hasAuthority('USER')")
    @ApiOperation(value = "重置用户密码")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage resetPassword(@RequestBody List<Long> userIds) {
        return ResultVO.getResponseMessage(rbacUserService.resetPassword(userIds));
    }

}
