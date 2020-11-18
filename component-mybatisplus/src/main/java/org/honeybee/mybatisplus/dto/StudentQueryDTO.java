package org.honeybee.mybatisplus.dto;

import cn.hutool.db.Page;
import lombok.Data;
import org.honeybee.mybatisplus.enums.StudentIdentityEnum;

@Data
public class StudentQueryDTO extends Page {

    //姓名
    private String name;

    //最小年龄 >= minAge
    private Integer minAge;

    //最大年龄 <= maxAge
    private Integer maxAge;

    //身份
    private StudentIdentityEnum identity;

}
