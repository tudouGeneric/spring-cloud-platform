package org.honeybee.cache.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis操作工具类
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> stringObjectRedisTemplate;

    private static RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void init() {
        redisTemplate = stringObjectRedisTemplate;
    }

    //操作STRING类型
    /**
     * 根据键获取值
     * @param key 键
     * @return 对象
     */
    public static Object get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 存储对象
     * @param key 键
     * @param value 对象
     */
    public static void set(final String key, final Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 存储对象并设置超时时间
     * @param key 键
     * @param value 对象
     * @param timeout 超时时间(单位:秒)
     */
    public static void setExpire(final String key, final Object value, final long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 判断keys中的任意一个键是否存在值
     * @param keys
     * @return Boolean
     */
    public static Boolean exists(final String... keys) {
        if(keys == null || keys.length == 0) {
            return Boolean.FALSE;
        }

        for(String key : keys) {
            if(redisTemplate.opsForValue().get(key) != null) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 设置有效时间
     * @param key 键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return boolean
     */
    public static boolean expire(final String key, final long timeout, final TimeUnit unit) {
        Boolean result = redisTemplate.expire(key, timeout, unit);
        return result != null && result;
    }

    /**
     * 设置有效时间
     * @param key 键
     * @param timeout 超时时间(单位:秒)
     * @return boolean
     */
    public static boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 删除
     * @param key 键
     * @return boolean
     */
    public static boolean delete(final String key) {
       Boolean result = redisTemplate.delete(key);
       return result != null && result;
    }

    /**
     * 批量删除
     * @param keys 键集合
     * @return 成功删除的个数
     */
    public static long delete(final Collection<String> keys) {
        Long result = redisTemplate.delete(keys);
        return result == null ? 0 : result;
    }

    //操作HASH类型
    /**
     * 获取Hash中的数据
     * @param key Redis键
     * @param hKey Hash中的键
     * @return Hash中的对象
     */
    public static Object hashGet(final String key, final String hKey) {
        return redisTemplate.opsForHash().get(key, hKey);
    }

    /**
     * 获取Hash中的多个数据
     * @param key Redis键
     * @param keys Hash中的键集合
     * @return Hash中的对象集合
     */
    public static List<Object> hashMultiGet(final String key, final Collection<Object> keys) {
        return redisTemplate.opsForHash().multiGet(key, keys);
    }

    /**
     * Hash中存入数据
     * @param key Redis键
     * @param hKey Hash键
     * @param value 值
     */
    public static void hashPut(final String key, final String hKey, final Object value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * Hash中存入多个数据
     * @param key Redis键
     * @param values Hash键值对
     */
    public static void hashPutAll(final String key, final Map<String, Object> values) {
        redisTemplate.opsForHash().putAll(key, values);
    }

    //操作SET类型
    /**
     * Set中存入数据
     * @param key Redis键
     * @param values 对象
     * @return 存入的个数
     */
    public static long setAdd(final String key, final Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        return count == null ? 0 : count;
    }

    /**
     * Set中删除数据
     * @param key Redis键
     * @param values 对象
     * @return 删除的个数
     */
    public static long setDelete(final String key, final Object... values) {
        Long count = redisTemplate.opsForSet().remove(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 根据键获取Set的集合
     * @param key Redis键
     * @return 集合对象
     */
    public static Set<Object> setGet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    //操作LIST类型
    /**
     * List中存入数据
     * @param key Redis键
     * @param value 对象
     * @return 存入的个数
     */
    public static long listPush(final String key, final Object value) {
        Long count = redisTemplate.opsForList().rightPush(key, value);
        return count == null ? 0 : count;
    }

    /**
     * List中存入多个数据
     * @param key Redis键
     * @param values 多个对象
     * @return 存入的个数
     */
    public static long listPushAll(final String key, final Collection<Object> values) {
        Long count = redisTemplate.opsForList().rightPushAll(key, values);
        return count == null ? 0 : count;
    }

    /**
     * List中获取start到end索引之间的元素
     * start=0，end=-1表示获取全部元素
     * @param key Redis键
     * @param start 开始索引
     * @param end 结束索引
     * @return List集合对象
     */
    public static List<Object> listGet(final String key, final int start, final int end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    //操作ZSET类型
    /**
     * ZSet中新增数据
     * @param key Redis键
     * @param value 值
     * @param score 分数
     */
    public static void zSetAdd(final String key, final Object value, final double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * ZSet中删除数据
     * @param key Redis键
     * @param values 对象
     * @return 删除的个数
     */
    public static long zSetDelete(final String key, Object... values) {
        Long count = redisTemplate.opsForZSet().remove(key, values);
        return count == null ? 0 : count;
    }

    /**
     * ZSet中获取start到end之间的元素
     * start=0，end=-1表示获取全部元素
     * @param key Redis键
     * @param start 开始索引
     * @param end 结束索引
     * @return Set集合对象
     */
    public static Set<Object> zSetGet(final String key, final int start , final int end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

}
