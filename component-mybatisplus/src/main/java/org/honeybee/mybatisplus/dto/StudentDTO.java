package org.honeybee.mybatisplus.dto;

import lombok.Data;
import org.honeybee.base.enums.DeleteStatusEnum;
import org.honeybee.mybatisplus.enums.StudentIdentityEnum;
import org.honeybee.mybatisplus.valid.group.StudentAddVaildGroup;
import org.honeybee.mybatisplus.valid.group.StudentUpdateValidGroup;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Student DTO
 */
@Data
public class StudentDTO {

    //id
    @NotNull(message = "id不能为空", groups = {StudentUpdateValidGroup.class})
    private Long id;

    //姓名
    @NotBlank(message = "姓名不能为空", groups = {StudentAddVaildGroup.class, StudentUpdateValidGroup.class})
    private String name;

    //年龄
    @Min(value = 0, message = "年龄不能小于等于0", groups = {StudentAddVaildGroup.class, StudentUpdateValidGroup.class})
    @Max(value = 255, message = "年龄不能大于等于255", groups = {StudentAddVaildGroup.class, StudentUpdateValidGroup.class})
    private Integer age;

    //身份
    @NotNull(message = "身份不能为空", groups = {StudentAddVaildGroup.class, StudentUpdateValidGroup.class})
    private StudentIdentityEnum  identity;

}
