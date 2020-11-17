package org.honeybee.rbac.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.base.constant.BaseConstant;
import org.honeybee.base.vo.ResultVO;
import org.honeybee.rbac.dto.AttachRolePermissionDTO;
import org.honeybee.rbac.dto.RbacRoleDTO;
import org.honeybee.rbac.dto.RbacRoleSearchDTO;
import org.honeybee.rbac.service.RbacRoleService;
import org.honeybee.rbac.valid.group.RbacRoleCreateValidGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@Api(tags = "角色相关接口")
public class RoleController {

    @Autowired
    private RbacRoleService rbacRoleService;

    @PostMapping("/create")
    @PreAuthorize(value = BaseConstant.SUPER_ADMIN_ROLE_AUTHORITY + "hasAuthority('ROLE')")
    @ApiOperation(value = "创建角色")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage create(@RequestBody @Validated(RbacRoleCreateValidGroup.class) RbacRoleDTO rbacRoleDTO) {
        return ResponseMessage.success("创建成功", rbacRoleService.create(rbacRoleDTO));
    }

    @DeleteMapping("/delete")
    @PreAuthorize(value = BaseConstant.SUPER_ADMIN_ROLE_AUTHORITY + "hasAuthority('ROLE')")
    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage delete(@RequestBody List<Long> roleIds) {
        ResultVO result = rbacRoleService.deleteRoles(roleIds);
        return ResultVO.getResponseMessage(result);
    }

    @GetMapping("/listPermissionsByRoleId")
    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "根据角色id查询所拥有的权限")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage listPermissionsByRoleId(@Param("id") Long id) {
        return ResponseMessage.success("查询成功", rbacRoleService.listPermissionsByRoleId(id));
    }

    @PostMapping("/attachRolePermissions")
    @PreAuthorize(value = BaseConstant.SUPER_ADMIN_ROLE_AUTHORITY + "hasAuthority('ROLE')")
    @ApiOperation(value = "给角色分配权限")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage attachRolePermissions(@RequestBody @Validated AttachRolePermissionDTO attachRolePermissionDTO) {
        ResultVO result = rbacRoleService.attachRolePermissions(attachRolePermissionDTO.getRoleId(), attachRolePermissionDTO.getPermissionIds());
        return ResultVO.getResponseMessage(result);
    }


    @PostMapping("/page")
    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "根据条件分页查询角色")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage find(RbacRoleSearchDTO roleSearchDTO) {
        return ResponseMessage.success(null, rbacRoleService.find(roleSearchDTO));
    }

}
