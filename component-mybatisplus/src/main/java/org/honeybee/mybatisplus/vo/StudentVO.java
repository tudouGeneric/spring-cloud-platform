package org.honeybee.mybatisplus.vo;

import lombok.Data;
import org.honeybee.mybatisplus.enums.StudentIdentityEnum;

import java.util.Date;

/**
 * student输出类
 */
@Data
public class StudentVO {

    //id
    private Long id;

    //姓名
    private String name;

    //年龄
    private Integer age;

    //身份
    private StudentIdentityEnum identity;

    //更新人
    private String updatedBy;

    //更新时间
    private Date updatedTime;

}
