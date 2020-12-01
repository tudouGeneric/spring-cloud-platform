package org.honeybee.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.honeybee.base.entity.BaseEntity;
import org.honeybee.base.enums.DeleteStatusEnum;
import org.honeybee.mybatisplus.enums.StudentIdentityEnum;

/**
 * 学生表（测试表）
 */
@Data
@TableName(value = "t_test_student")
public class Student extends BaseEntity {

    /**
     * uuid
     */
    private String uuid;

    /**
     * 姓名
     */

    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 身份(正常生/借读生)
     */
    private StudentIdentityEnum identity;

    /**
     * 删除状态（0：未删除 [默认] ；1：已删除）
     */
    private DeleteStatusEnum deleteStatus = DeleteStatusEnum.EXISTED;

}
