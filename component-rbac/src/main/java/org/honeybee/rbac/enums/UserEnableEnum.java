package org.honeybee.rbac.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Setter;
import org.honeybee.base.enums.ICustomEnum;

/**
 * 用户是否启用枚举类
 */
public enum UserEnableEnum implements ICustomEnum<Integer> {

    ENABLE(1, "启用"),
    DISABLE(0, "禁用");

    UserEnableEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    //标记数据库存的值是code
    @EnumValue
    @Setter
    private final int value;

    @Setter
    private final String description;

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

}
