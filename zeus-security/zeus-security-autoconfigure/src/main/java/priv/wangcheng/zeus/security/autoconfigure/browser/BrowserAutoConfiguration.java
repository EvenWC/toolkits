package priv.wangcheng.zeus.security.autoconfigure.browser;

import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.social.SocialWebAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.util.Assert;
import priv.wangcheng.zeus.security.core.HttpSecurityDetailConfiguration;
import priv.wangcheng.zeus.security.core.HttpSecurityDetailConfigurationManager;
import priv.wangcheng.zeus.security.core.SecurityConfigurationAdapterManager;
import priv.wangcheng.zeus.security.core.authorize.AuthorizeConfigProviderManager;

/**
 * @author wangcheng
 * @version $Id: BrowserAutoConfiguration.java, v0.1 2019/5/31 23:47 wangcheng Exp $$
 */
@Configuration
@ConditionalOnClass({SocialConfigurerAdapter.class, HttpSecurityDetailConfiguration.class})
@AutoConfigureBefore(SocialWebAutoConfiguration.class)
@AutoConfigureAfter(BrowserConfigureBeanAutoConfiguration.class)
public class BrowserAutoConfiguration {


    @Configuration
    public class BrowserSecurityConfiguration extends WebSecurityConfigurerAdapter implements InitializingBean {

        private SecurityConfigurationAdapterManager adapterManager;

        private HttpSecurityDetailConfigurationManager httpSecurityDetailConfigurationManager;

        private AuthorizeConfigProviderManager authorizeConfigProviderManager;

        public BrowserSecurityConfiguration(
                ObjectProvider<SecurityConfigurationAdapterManager> adapterManager,
                ObjectProvider<HttpSecurityDetailConfigurationManager> httpSecurityDetailConfigurationManagerObjectProvider,
                ObjectProvider<AuthorizeConfigProviderManager> authorizeConfigProviderManager) {
            this.adapterManager = adapterManager.getIfUnique();
            this.httpSecurityDetailConfigurationManager = httpSecurityDetailConfigurationManagerObjectProvider.getIfUnique();
            this.authorizeConfigProviderManager = authorizeConfigProviderManager.getIfUnique();
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            Assert.notNull(adapterManager,"adapterManager is not null");
            Assert.notNull(httpSecurityDetailConfigurationManager,"authorizeConfigProviderManager is not null");
            Assert.notNull(authorizeConfigProviderManager,"authorizeConfigProviderManager is not null");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //应用注入的各个模块
            adapterManager.apply(http);
            //配置注入的功能点
            httpSecurityDetailConfigurationManager.configure(http);
            //授权管理
            authorizeConfigProviderManager.configure(http.authorizeRequests());
        }
    }
}
