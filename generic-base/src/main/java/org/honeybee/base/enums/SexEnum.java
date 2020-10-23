package org.honeybee.base.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.Setter;

/**
 * 性别枚举类
 */
public enum SexEnum {

    M("M", "男"),
    F("F", "女"),
    X("X", "未知");

    SexEnum(String code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    @EnumValue
    @Getter
    @Setter
    private final String code;

    @Getter
    @Setter
    private final String descp;

}
