package org.honeybee.mybatisplus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

public enum StudentIdentityEnum {

    NORMAL("NORMAL", "正常"),
    TRANSIENT("TRANSIENT", "借读");


    StudentIdentityEnum(String code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    public static StudentIdentityEnum getEnumByDescp(String descp) {
        if(StringUtils.isBlank(descp)) {
            return null;
        }
        for(StudentIdentityEnum identityEnum : StudentIdentityEnum.values()) {
            if(identityEnum.getDescp().equals(descp)) {
                return identityEnum;
            }
        }
        return null;
    }

    public static StudentIdentityEnum getEnumByCode(String code) {
        if(StringUtils.isBlank(code)) {
            return null;
        }
        for(StudentIdentityEnum identityEnum : StudentIdentityEnum.values()) {
            if(identityEnum.getCode().equals(code)) {
                return identityEnum;
            }
        }
        return null;
    }

    //标记数据库存的值是code
    @EnumValue
    @Getter
    @Setter
    private final String code;

    @Getter
    @Setter
    private final String descp;

}
