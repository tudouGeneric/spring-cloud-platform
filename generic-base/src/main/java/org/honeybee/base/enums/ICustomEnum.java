package org.honeybee.base.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * 通用字典枚举
 * @param <T>
 */
public interface ICustomEnum<T extends Serializable> extends IEnum<T> {

    /**
     * 数据库中存储的值
     * @return
     */
    @JsonValue
    @Override
    T getValue();

    /**
     * 字典值
     * @return
     */
    String getDescription();

}
