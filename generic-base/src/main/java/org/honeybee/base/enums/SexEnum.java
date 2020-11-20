package org.honeybee.base.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Setter;

/**
 * 性别枚举类
 */
public enum SexEnum implements ICustomEnum<String> {

    M("M", "男"),
    F("F", "女"),
    X("X", "未知");

    SexEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @EnumValue
    @Setter
    private final String value;

    @Setter
    private final String description;

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
