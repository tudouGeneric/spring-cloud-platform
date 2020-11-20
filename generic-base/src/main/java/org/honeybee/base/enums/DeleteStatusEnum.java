package org.honeybee.base.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Setter;

/**
 * 删除状态通用枚举
 * 用于逻辑删除
 */
public enum DeleteStatusEnum implements ICustomEnum<Integer> {

    EXISTED(0, "未删除"),
    DELETED(1, "已删除");

    DeleteStatusEnum(int value, String description) {
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
