package priv.wangcheng.zeus.security.autoconfigure.app;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.Assert;
import priv.wangcheng.zeus.app.control.AppSecurityEndpoint;
import priv.wangcheng.zeus.security.core.HttpSecurityDetailConfigurationManager;
import priv.wangcheng.zeus.security.core.SecurityConfigurationAdapterManager;
import priv.wangcheng.zeus.security.core.authorize.AuthorizeConfigProviderManager;

/**
 * @author: Administrator
 * @date: 2019/5/7 22:53
 * @description:
 */
@Configuration
@ConditionalOnClass(AppSecurityEndpoint.class)
@AutoConfigureAfter(Oauth2TokenStoreAutoConfiguration.class)
public class Oauth2ResourceServerAutoConfiguration{


    @Configuration
    @EnableResourceServer
    protected static class Oauth2ResourceServerConfig extends ResourceServerConfigurerAdapter implements InitializingBean {

        private SecurityConfigurationAdapterManager adapterManager;

        private HttpSecurityDetailConfigurationManager httpSecurityDetailConfigurationManager;

        private AuthorizeConfigProviderManager authorizeConfigProviderManager;

        private SecurityExpressionHandler<FilterInvocation> oAuth2WebSecurityExpressionHandler;

        public Oauth2ResourceServerConfig(
                ObjectProvider<SecurityConfigurationAdapterManager> adapterManager,
                ObjectProvider<HttpSecurityDetailConfigurationManager> httpSecurityDetailConfigurationManagerObjectProvider,
                ObjectProvider<AuthorizeConfigProviderManager> authorizeConfigProviderManager,
                ObjectProvider<SecurityExpressionHandler<FilterInvocation>> oAuth2WebSecurityExpressionHandler) {
            this.adapterManager = adapterManager.getIfUnique();
            this.httpSecurityDetailConfigurationManager = httpSecurityDetailConfigurationManagerObjectProvider.getIfUnique();
            this.authorizeConfigProviderManager = authorizeConfigProviderManager.getIfUnique();
            this.oAuth2WebSecurityExpressionHandler = oAuth2WebSecurityExpressionHandler.getIfUnique();
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            Assert.notNull(adapterManager,"require a SecurityConfigurationAdapterManager:" + SecurityConfigurationAdapterManager.class.getName());
            Assert.notNull(httpSecurityDetailConfigurationManager,"require a HttpSecurityDetailConfigurationManager:" + HttpSecurityDetailConfigurationManager.class.getName());
            Assert.notNull(authorizeConfigProviderManager,"require a AuthorizeConfigProviderManager:" + AuthorizeConfigProviderManager.class.getName());
            Assert.notNull(oAuth2WebSecurityExpressionHandler,"require a OAuth2WebSecurityExpressionHandler:" + OAuth2WebSecurityExpressionHandler.class.getName());
        }
        @Override
        public void configure(HttpSecurity http) throws Exception {
            //应用注入的各个模块
            adapterManager.apply(http);
            //配置注入的功能点
            httpSecurityDetailConfigurationManager.configure(http);
            //授权管理
            authorizeConfigProviderManager.configure(http.authorizeRequests());
        }

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.expressionHandler(oAuth2WebSecurityExpressionHandler);
        }
    }
}

