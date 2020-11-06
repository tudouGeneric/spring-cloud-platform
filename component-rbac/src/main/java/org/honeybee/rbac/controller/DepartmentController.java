package org.honeybee.rbac.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.rbac.service.RbacDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseMessage findAll(@Param(value = "departmentId") Long departmentId) {
        return ResponseMessage.success(null, departmentService.getDepartmentTree(departmentId));
    }

}
