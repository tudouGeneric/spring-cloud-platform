package org.honeybee.cache.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //自定义设置序列化器
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //设置key采用string序列化
        template.setKeySerializer(stringRedisSerializer);
        //设置hash的key采用string序列化
        template.setHashKeySerializer(stringRedisSerializer);

        FastJsonRedisSerializer jsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        jsonRedisSerializer.getFastJsonConfig().setSerializerFeatures(SerializerFeature.WriteClassName);
        //添加autotype白名单并打开autotype功能
        ParserConfig.getGlobalInstance().addAccept("org.honeybee");
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        //设置value采用fastjson的序列化方式
        template.setValueSerializer(jsonRedisSerializer);
        //设置hash的value采用fastjson的序列化方式
        template.setHashValueSerializer(jsonRedisSerializer);
        //设置其他默认的序列化方式
        template.setDefaultSerializer(jsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

}
