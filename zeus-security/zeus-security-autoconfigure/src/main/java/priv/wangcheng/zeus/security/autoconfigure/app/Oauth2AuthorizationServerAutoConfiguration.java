package priv.wangcheng.zeus.security.autoconfigure.app;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.Assert;
import priv.wangcheng.zeus.app.control.AppSecurityEndpoint;
import priv.wangcheng.zeus.security.core.properties.Oauth2ClientProperties;
import priv.wangcheng.zeus.security.core.properties.SecurityProperties;

/**
 * @author wangcheng
 * @version $Id: AppSecurityAutoConfiguration.java, v0.1 2019/6/1 23:30 wangcheng Exp $$
 */
@Configuration
@ConditionalOnClass(AppSecurityEndpoint.class)
@AutoConfigureAfter(Oauth2TokenStoreAutoConfiguration.class)
public class Oauth2AuthorizationServerAutoConfiguration {

    @Configuration
    @EnableAuthorizationServer
    protected static class Oauth2AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter implements
            InitializingBean {

        private AuthenticationManager authenticationManager;

        private UserDetailsService userDetailsService;

        private SecurityProperties securityProperties;

        private TokenStore redisTokenStore;

        private JwtAccessTokenConverter jwtAccessTokenConverter;
        private TokenEnhancer jwtTokenEnhancer;

        public Oauth2AuthorizationServerConfiguration(
                ObjectProvider<AuthenticationManager> authenticationManager,
                ObjectProvider<UserDetailsService> userDetailsService,
                SecurityProperties securityProperties,
                ObjectProvider<TokenStore> redisTokenStore,
                ObjectProvider<JwtAccessTokenConverter> jwtAccessTokenConverter,
                ObjectProvider<TokenEnhancer> jwtTokenEnhancer) {
            this.authenticationManager = authenticationManager.getIfUnique();
            this.userDetailsService = userDetailsService.getIfUnique();
            this.securityProperties = securityProperties;
            this.redisTokenStore = redisTokenStore.getIfUnique();
            this.jwtAccessTokenConverter = jwtAccessTokenConverter.getIfUnique();
            this.jwtTokenEnhancer = jwtTokenEnhancer.getIfUnique();
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            Assert.notNull(authenticationManager,"require a authenticationManager:"+AuthenticationManager.class.getName());
            Assert.notNull(userDetailsService,"require a userDetailsService:"+UserDetailsService.class.getName());
            Assert.notNull(redisTokenStore,"require a redisTokenStore:"+TokenStore.class.getName());
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authenticationManager(authenticationManager)
                    .userDetailsService(userDetailsService)
                    .tokenStore(redisTokenStore);
            if(jwtAccessTokenConverter != null && jwtTokenEnhancer != null){
                TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
                List<TokenEnhancer> enhancers = new ArrayList();
                enhancers.add(jwtAccessTokenConverter);
                enhancers.add(jwtTokenEnhancer);
                enhancerChain.setTokenEnhancers(enhancers);
                endpoints.tokenEnhancer(enhancerChain)
                        .accessTokenConverter(jwtAccessTokenConverter);
            }
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            Oauth2ClientProperties[] properties = securityProperties.getOauth2Propertites().getOauth2ClientProperties();
            InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
            for (int i = 0; i < properties.length; i++) {
                Oauth2ClientProperties property = properties[i];
                builder.withClient(property.getClientId())
                        .secret(property.getClentSecurity())
                        .accessTokenValiditySeconds(property.getAccessTokenValiditySeconds())
                        .scopes("all")
                        .authorizedGrantTypes("password","refresh_token");
            }
        }
    }
}
