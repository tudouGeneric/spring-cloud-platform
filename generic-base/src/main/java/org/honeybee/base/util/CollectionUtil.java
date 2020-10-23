package org.honeybee.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 集合工具类
 */
public class CollectionUtil {

    /**
     * 集合类型转换
     * @param list
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List copyList(List<T> list, Class tClass) {
        if(CollectionUtils.isEmpty(list)) {
            return new ArrayList();
        }
        return JSON.parseArray(JSON.toJSONString(list), tClass);
    }

    /**
     * map类型对象转换为 JSONObject
     * @param map
     * @return
     */
    public static JSONObject constrantMapToObject(Map map) {
        return JSON.parseObject(JSON.toJSONString(map));
    }

}
