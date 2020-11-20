package org.honeybee.rbac.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Setter;
import org.honeybee.base.enums.ICustomEnum;

/**
 * 权限类型枚举值
 */
public enum PermissionTypeEnum implements ICustomEnum<Integer> {

    MENU(0, "菜单"),
    INTERFACE(1, "接口");

    PermissionTypeEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    //标记数据库存的值是value
    @EnumValue
    @Setter
    private final Integer value;

    @Setter
    private final String description;

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
