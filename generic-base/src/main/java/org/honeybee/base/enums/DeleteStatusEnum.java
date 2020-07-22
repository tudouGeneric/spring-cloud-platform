package org.honeybee.base.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 删除状态通用枚举
 * 用于逻辑删除
 */
public enum DeleteStatusEnum {

    EXISTED(0, "存在"),
    DELETED(1, "已删除");

    DeleteStatusEnum(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    //标记数据库存的值是code
    @EnumValue
    private final int code;

    private final String descp;

}
