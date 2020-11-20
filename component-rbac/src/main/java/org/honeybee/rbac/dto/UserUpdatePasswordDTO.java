package org.honeybee.rbac.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 用户更新密码接口DTO类
 */
@Data
public class UserUpdatePasswordDTO {

    @NotBlank(message = "原密码[oldPassword]不能为空")
    private String oldPassword;

    @NotBlank(message = "新密码[newPassword]不能为空")
    @Length(min = 1, max = 20, message = "新密码长度不能超过20个字符")
    private String newPassword;

}
