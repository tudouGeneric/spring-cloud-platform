package org.honeybee.rbac.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户是否启用枚举类
 */
public enum UserEnableEnum {

    ENABLE(1, "启用"),
    DISABLE(0, "禁用");

    UserEnableEnum(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    //标记数据库存的值是code
    @EnumValue
    @Getter
    @Setter
    private final int code;

    @Getter
    @Setter
    private final String descp;

}
