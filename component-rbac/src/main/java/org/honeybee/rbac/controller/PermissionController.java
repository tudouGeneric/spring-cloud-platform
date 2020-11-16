package org.honeybee.rbac.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.base.constant.BaseConstant;
import org.honeybee.base.vo.ResultVO;
import org.honeybee.rbac.dto.RbacPermissionDTO;
import org.honeybee.rbac.service.RbacPermissionService;
import org.honeybee.rbac.valid.group.RbacPermissionCreateValidGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
@Api(tags = "权限相关接口")
public class PermissionController {

    @Autowired
    private RbacPermissionService permissionService;

    @PostMapping("/create")
    @PreAuthorize(value = BaseConstant.SUPER_ADMIN_ROLE_AUTHORITY + "hasAuthority('PERMISSION')")
    @ApiOperation(value = "创建权限")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage create(@RequestBody @Validated(RbacPermissionCreateValidGroup.class) RbacPermissionDTO rbacPermissionDTO) {
        return ResponseMessage.success("创建成功", permissionService.create(rbacPermissionDTO));
    }


    @DeleteMapping("/delete")
    @PreAuthorize(value = BaseConstant.SUPER_ADMIN_ROLE_AUTHORITY + "hasAuthority('PERMISSION')")
    @ApiOperation(value = "删除权限(包括权限的子节点)")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage delete(List<Long> permissionIds) {
        ResultVO result = permissionService.deletePermissions(permissionIds);
        if(result.getFlag()) {
            return ResponseMessage.success(result.getMessage());
        } else {
            return ResponseMessage.fail(result.getMessage());
        }
    }

    @GetMapping("/listSelfAndChildNodes")
    @PreAuthorize(value = BaseConstant.SUPER_ADMIN_ROLE_AUTHORITY + "hasAuthority('PERMISSION')")
    @ApiOperation(value = "根据id查询自身和所有子节点的树")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage listSelfAndChildNodes(@Param("id") Long id) {
        return ResponseMessage.success("查询成功", permissionService.listSelfAndChildNodesTreeById(id));
    }

}
