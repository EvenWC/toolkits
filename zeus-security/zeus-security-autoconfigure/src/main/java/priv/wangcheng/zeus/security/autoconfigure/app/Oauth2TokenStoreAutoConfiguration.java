package priv.wangcheng.zeus.security.autoconfigure.app;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import priv.wangcheng.zeus.app.control.AppSecurityEndpoint;
import priv.wangcheng.zeus.app.jwt.JwtTokenEnhancer;
import priv.wangcheng.zeus.security.core.properties.SecurityProperties;

/**
 * @author: Administrator
 * @date: 2019/5/18 21:35
 * @description:
 */
@Configuration
@ConditionalOnBean(RedisConnectionFactory.class)
@ConditionalOnClass(AppSecurityEndpoint.class)
@AutoConfigureAfter(Oauth2BeanAutoConfiguration.class)
public class Oauth2TokenStoreAutoConfiguration {

    private RedisConnectionFactory redisConnectionFactory;

    public Oauth2TokenStoreAutoConfiguration(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Bean
    @ConditionalOnProperty(prefix = "imooc.security.oauth2Propertites",name="tokenType",havingValue = "redis")
    public TokenStore redisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Configuration
    @ConditionalOnProperty(prefix = "imooc.security.oauth2Propertites",name="tokenType",havingValue = "jwt",matchIfMissing = true)
    public static class JwtTokenConfiguration {

        private SecurityProperties securityProperties;

        public JwtTokenConfiguration(SecurityProperties securityProperties) {
            this.securityProperties = securityProperties;
        }

        @Bean
        public TokenStore jwtTokenStore(){
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setSigningKey(securityProperties.getOauth2Propertites().getJwtSigningKey());
            return converter;
        }
        @Bean
        public TokenEnhancer jwtTokenEnhancer(){
            return new JwtTokenEnhancer();
        }
    }
}
