package org.honeybee.rbac.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.honeybee.rbac.util.JwtUtil;
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

        String account = JwtUtil.getCurrentUserInfo() == null ? "Anonymous" : JwtUtil.getCurrentUserInfo().getAccount();

        if(createdBy == null) {
            this.setFieldValByName("createdBy", account, metaObject);
        }
        if(createdTime == null) {
            this.setFieldValByName("createdTime", new Date(), metaObject);
        }
        if(updatedBy == null) {
            this.setFieldValByName("updatedBy", account, metaObject);
        }
        if(updatedTime == null) {
            this.setFieldValByName("updatedTime", new Date(), metaObject);
        }
        if(version == null) {
            this.setFieldValByName("version", 0L, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long version = (Long) getFieldValByName("version", metaObject);

        String account = JwtUtil.getCurrentUserInfo() == null ? "Anonymous" : JwtUtil.getCurrentUserInfo().getAccount();

        this.setFieldValByName("updatedBy", account, metaObject);
        this.setFieldValByName("updatedTime", new Date(), metaObject);
        this.setFieldValByName("version", version == null ? 0L : version+1, metaObject);
    }

}
