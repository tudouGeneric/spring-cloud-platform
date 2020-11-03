package org.honeybee.rbac.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "auth")
@Data
public class AuthProperties {

    /**
     * 忽略校验
     */
    private String[] ignores;

    private JwtConfig jwt;

    @Data
    public static class JwtConfig {
        //token请求头名
        private String header;

        //密钥
        private String secret;

        //token有效期
        private String expiration;
    }


}
