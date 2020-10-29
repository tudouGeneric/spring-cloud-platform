package org.honeybee.rbac.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.base.constant.BaseConstant;
import org.honeybee.rbac.dto.RbacUserDTO;
import org.honeybee.rbac.dto.RbacUserSearchDTO;
import org.honeybee.rbac.service.RbacUserService;
import org.honeybee.rbac.valid.group.RbacUserCreateValidGroup;
import org.honeybee.rbac.valid.group.RbacUserUpdateValidGroup;
import org.honeybee.rbac.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private RbacUserService rbacUserService;

    @GetMapping("/all")
    @PreAuthorize(value = BaseConstant.SUPER_ADMIN_ROLE_AUTHORITY + "hasAuthority('USER_LIST_ALL')")
    @ApiOperation(value = "查询所有用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage findAll() {
        return ResponseMessage.success(null, rbacUserService.findAll());
    }

    @GetMapping("/page")
    @PreAuthorize(value = "hasAuthority('USER_LIST_ALL')")
    @ApiOperation(value = "根据条件分页查询用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage find(RbacUserSearchDTO rbacUserSearchDTO) {
        IPage<UserVO> pageResult = rbacUserService.find(rbacUserSearchDTO);
        return ResponseMessage.success(null, pageResult);
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('USER_GET_DETAIL')")
    @ApiOperation(value = "根据id查询用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage<UserVO> getDetail(@PathVariable Long id) {
       return ResponseMessage.success(null, rbacUserService.getById(id));
    }

    @GetMapping("/account")
    @PreAuthorize(value = "hasAuthority('USER_GET_DETAIL')")
    @ApiOperation(value = "根据账号查询用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage<UserVO> getByAccount(@NotBlank(message = "账号[account不能为空]") String account) {
        return ResponseMessage.success(null, rbacUserService.getByAccount(account));
    }

    @PostMapping("/create")
    @PreAuthorize(value = "hasAuthority('USER_CREATE')")
    @ApiOperation(value = "创建用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage create(@RequestBody @Validated(RbacUserCreateValidGroup.class) RbacUserDTO userDTO) {
        return ResponseMessage.success("创建成功", rbacUserService.create(userDTO));
    }

    @PostMapping("/update")
    @PreAuthorize(value = "hasAuthority('USER_UPDATE')")
    @ApiOperation(value = "更新用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage update(@RequestBody @Validated(RbacUserUpdateValidGroup.class) RbacUserDTO userDTO) {
        return ResponseMessage.success("更新成功", rbacUserService.update(userDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('USER_DELETE')")
    @ApiOperation(value = "删除用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public ResponseMessage delete(@PathVariable Long id) {
        return ResponseMessage.success("删除成功", rbacUserService.delete(id));
    }

}
