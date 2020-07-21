package org.honeybee.interceptor;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 元对象处理器
 * https://blog.csdn.net/qq_26030541/article/details/103118701
 */
@Slf4j
@Component
public class BaseMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //获取需要被填充的字段
        Object createdBy = getFieldValByName("createdBy", metaObject);
        Object createdTime = getFieldValByName("createdTime", metaObject);
        Object updatedBy = getFieldValByName("updatedBy", metaObject);
        Object updatedTime = getFieldValByName("updatedTime", metaObject);
        Object version = getFieldValByName("version", metaObject);
        if(createdBy == null) {
            this.setInsertFieldValByName("createdBy", "admin", metaObject);
        }
        if(createdTime == null) {
            this.setInsertFieldValByName("createdTime", new Date(), metaObject);
        }
        if(updatedBy == null) {
            this.setInsertFieldValByName("updatedBy", "admin", metaObject);
        }
        if(updatedTime == null) {
            this.setInsertFieldValByName("updatedTime", new Date(), metaObject);
        }
        if(version == null) {
            this.setInsertFieldValByName("version", 0L, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long version = (Long) getFieldValByName("version", metaObject);

        this.setUpdateFieldValByName("updatedBy", "admin", metaObject);
        this.setUpdateFieldValByName("updatedTime", new Date(), metaObject);
        this.setUpdateFieldValByName("version", version+1, metaObject);
    }

}
