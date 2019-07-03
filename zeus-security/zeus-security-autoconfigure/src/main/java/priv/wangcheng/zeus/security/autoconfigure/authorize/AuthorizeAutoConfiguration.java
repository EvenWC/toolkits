package priv.wangcheng.zeus.security.autoconfigure.authorize;

import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import priv.wangcheng.zeus.security.autoconfigure.core.SecurityCoreAutoConfiguration;
import priv.wangcheng.zeus.security.core.authorize.AuthorizeConfigProvider;
import priv.wangcheng.zeus.security.core.authorize.AuthorizeConfigProviderManager;
import priv.wangcheng.zeus.security.core.authorize.ZeusAuthorizeConfigProvider;
import priv.wangcheng.zeus.security.core.authorize.ZeusAuthorizeConfigProviderManager;
import priv.wangcheng.zeus.security.core.properties.SecurityProperties;

/**
 * @author wangcheng
 * @version $Id: AuthorizeAutoConfiguration.java, v0.1 2019/6/1 19:57 wangcheng Exp $$
 */
@Configuration
@ConditionalOnClass(OAuth2WebSecurityExpressionHandler.class)
@AutoConfigureAfter(SecurityCoreAutoConfiguration.class)
public class AuthorizeAutoConfiguration implements ApplicationContextAware {


    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SecurityExpressionHandler<FilterInvocation> oAuth2WebSecurityExpressionHandler(){
        OAuth2WebSecurityExpressionHandler handler = new OAuth2WebSecurityExpressionHandler();
        handler.setApplicationContext(applicationContext);
        return handler;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public AuthorizeConfigProvider zeusAuthorizeConfigProvider(SecurityProperties securityProperties){
        return new ZeusAuthorizeConfigProvider(securityProperties);
    }

    @Bean
    public AuthorizeConfigProviderManager zeusAuthorizeConfigProviderManager(List<AuthorizeConfigProvider> providers){
        return new ZeusAuthorizeConfigProviderManager(providers);
    }


}
