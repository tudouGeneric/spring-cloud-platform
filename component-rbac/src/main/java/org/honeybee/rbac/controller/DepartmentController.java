package org.honeybee.rbac.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.base.constant.BaseConstant;
import org.honeybee.base.vo.ResultVO;
import org.honeybee.rbac.dto.RbacDepartmentDTO;
import org.honeybee.rbac.service.RbacDepartmentService;
import org.honeybee.rbac.valid.group.RbacDepartmentCreateValidGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
@Api(tags = "部门相关接口")
public class DepartmentController {

    @Autowired
    private RbacDepartmentService departmentService;

    @GetMapping("/tree")
    @ApiOperation(value = "查询部门树")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage findAll(@Param(value = "departmentId") Long departmentId) {
        return ResponseMessage.success(null, departmentService.getDepartmentTree(departmentId));
    }

    @GetMapping("/create")
    @PreAuthorize(value = BaseConstant.SUPER_ADMIN_ROLE_AUTHORITY + "hasAuthority('DEPARTMENT')")
    @ApiOperation(value = "新增部门")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage create(@RequestBody @Validated(RbacDepartmentCreateValidGroup.class) RbacDepartmentDTO departmentDTO) {
        return ResultVO.getResponseMessage(departmentService.create(departmentDTO));
    }

    

}
