package org.honeybee.mybatisplus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum StudentIdentityEnum {

    NORMAL("NORMAL", "正常"),
    TRANSIENT("TRANSIENT", "借读");


    StudentIdentityEnum(String code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    //标记数据库存的值是code
    @EnumValue
    private final String code;

    private final String descp;

}
