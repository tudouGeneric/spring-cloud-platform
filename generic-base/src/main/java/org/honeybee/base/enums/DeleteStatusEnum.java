package org.honeybee.base.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.Setter;

/**
 * 删除状态通用枚举
 * 用于逻辑删除
 */
public enum DeleteStatusEnum {

    EXISTED(0, "未删除"),
    DELETED(1, "已删除");

    DeleteStatusEnum(int code, String descp) {
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
