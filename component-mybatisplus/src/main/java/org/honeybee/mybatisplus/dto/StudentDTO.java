package org.honeybee.mybatisplus.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;
import org.honeybee.mybatisplus.converter.StudentIdentityConverter;
import org.honeybee.mybatisplus.enums.StudentIdentityEnum;
import org.honeybee.mybatisplus.valid.group.StudentAddVaildGroup;
import org.honeybee.mybatisplus.valid.group.StudentUpdateValidGroup;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Student DTO
 */
@Data
public class StudentDTO {

    //id
    @NotNull(message = "id不能为空", groups = {StudentUpdateValidGroup.class})
    @ExcelIgnore
    private Long id;

    //姓名
    @NotBlank(message = "姓名不能为空", groups = {StudentAddVaildGroup.class, StudentUpdateValidGroup.class})
    @ExcelProperty(index = 0, value = "姓名")
    private String name;

    //年龄
    @Min(value = 0, message = "年龄不能小于等于0", groups = {StudentAddVaildGroup.class, StudentUpdateValidGroup.class})
    @Max(value = 255, message = "年龄不能大于等于255", groups = {StudentAddVaildGroup.class, StudentUpdateValidGroup.class})
    @ExcelProperty(index = 1, value = "年龄")
    private Integer age;

    //身份
    @NotNull(message = "身份不能为空", groups = {StudentAddVaildGroup.class, StudentUpdateValidGroup.class})
    @ExcelProperty(index = 2, converter = StudentIdentityConverter.class, value = "身份")
    private StudentIdentityEnum  identity;

    @ExcelProperty(index = 3, value = "日期")
    @DateTimeFormat("yyyy-MM-dd")
    private Date date;

}
