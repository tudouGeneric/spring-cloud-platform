package org.honeybee.rbac.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.honeybee.base.enums.SexEnum;
import org.honeybee.rbac.valid.group.RbacUserCreateValidGroup;
import org.honeybee.rbac.valid.group.RbacUserLoginValidGroup;
import org.honeybee.rbac.valid.group.RbacUserRegisterValidGroup;
import org.honeybee.rbac.valid.group.RbacUserUpdateValidGroup;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class RbacUserDTO {

    /**
     * 用户id
     */
    @NotNull(message = "用户ID不能为空", groups = {RbacUserUpdateValidGroup.class})
    private Long id;

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空", groups = {RbacUserRegisterValidGroup.class, RbacUserCreateValidGroup.class, RbacUserLoginValidGroup.class})
    @Length(min = 1, max = 20, message = "账号长度不能超过20个字符", groups = {RbacUserRegisterValidGroup.class, RbacUserCreateValidGroup.class})
    @Pattern(regexp = "[A-Za-z0-9]+", message = "账号格式非法,只能含有字母和数字", groups = {RbacUserCreateValidGroup.class})
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = {RbacUserRegisterValidGroup.class, RbacUserLoginValidGroup.class})
    @Length(min = 1, max = 20, message = "密码长度不能超过20个字符", groups = {RbacUserRegisterValidGroup.class})
    private String password;

    /**
     * 部门id
     */
    @NotNull(message = "部门ID不能为空", groups = {RbacUserRegisterValidGroup.class, RbacUserCreateValidGroup.class})
    private Long departmentId;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private SexEnum sex;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空", groups = {RbacUserRegisterValidGroup.class, RbacUserCreateValidGroup.class})
    private String name;

    /**
     *生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date birth;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobilePhone;

    /**
     * 头像地址
     */
    private String photoUrl;

}
