package org.honeybee.base.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.honeybee.base.handler.CustomEnumWebSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private CustomEnumWebSerializer customEnumWebSerializer;

    /**
     * 配置所有请求支持跨域访问，并且不限定域
     * 支持的方法
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    /**
     * 添加customEnumWebSerializer到ObjectMapper内
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.stream().filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
                .forEach(converter ->  {
                    MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
                    SimpleModule simpleModule = new SimpleModule();
                    simpleModule.addSerializer(customEnumWebSerializer);
                    jsonConverter.getObjectMapper().registerModule(simpleModule);
                });
    }

}
