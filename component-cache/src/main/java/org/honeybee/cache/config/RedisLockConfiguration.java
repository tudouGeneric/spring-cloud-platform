package org.honeybee.cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * redis分布式锁配置类
 */
@Configuration
public class RedisLockConfiguration {

    private static final String integrationGroupKey = "redis_integration";      //分布式锁的组名称

    private static final long expireAfter = 10L * 1000L;        //分布式锁持有时间(避免获取后业务异常,锁不释放)

    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockRegistry(redisConnectionFactory, integrationGroupKey, expireAfter);
    }

}
