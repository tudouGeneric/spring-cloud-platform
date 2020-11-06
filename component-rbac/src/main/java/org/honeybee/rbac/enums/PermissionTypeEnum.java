package org.honeybee.rbac.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.Setter;

/**
 * 权限类型枚举值
 */
public enum PermissionTypeEnum {

    MENU(1, "菜单"),
    INTERFACE(2, "接口");


    PermissionTypeEnum(int code, String descp) {
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
