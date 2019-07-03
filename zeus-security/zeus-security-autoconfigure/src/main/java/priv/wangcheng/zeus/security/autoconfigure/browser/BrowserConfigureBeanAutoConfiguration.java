package priv.wangcheng.zeus.security.autoconfigure.browser;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import priv.wangcheng.zeus.security.autoconfigure.authorize.AuthorizeAutoConfiguration;
import priv.wangcheng.zeus.security.browser.BrowserSocialEndpoint;
import priv.wangcheng.zeus.security.core.HttpCsrfConfiguration;
import priv.wangcheng.zeus.security.core.HttpSecurityDetailConfiguration;
import priv.wangcheng.zeus.security.browser.configure.LogoutConfiguration;
import priv.wangcheng.zeus.security.core.PasswordAuthenticationConfiguration;
import priv.wangcheng.zeus.security.browser.configure.RememberMeConfiguration;
import priv.wangcheng.zeus.security.browser.configure.SessionConfiguration;
import priv.wangcheng.zeus.security.core.properties.BrowserProperties;


/**
 * @author wangcheng
 * @version $Id: BrowserConfigureBeanAutoConfiguration.java, v0.1 2019/6/1 12:03 wangcheng Exp $$
 */
@Configuration
@ConditionalOnClass({BrowserSocialEndpoint.class})
@AutoConfigureAfter({BrowserBeanAutoConfiguration.class, AuthorizeAutoConfiguration.class})
public class BrowserConfigureBeanAutoConfiguration {


    @Bean
    public LogoutConfiguration logoutConfiguration(ObjectProvider<LogoutSuccessHandler> logoutSuccessHandler){
        return new LogoutConfiguration(logoutSuccessHandler);
    }

    @Bean
    public RememberMeConfiguration rememberMeConfiguration(ObjectProvider<PersistentTokenRepository> persistentTokenRepository,
            BrowserProperties browserProperties,
            ObjectProvider<UserDetailsService> userDetailsService){
        return new RememberMeConfiguration(persistentTokenRepository,browserProperties,userDetailsService);
    }
    @Bean
    public SessionConfiguration sessionConfiguration(ObjectProvider<SessionInformationExpiredStrategy> sessionInformationExpiredStrategy,
            ObjectProvider<InvalidSessionStrategy> invalidSessionStrategy,
            BrowserProperties browserProperties){
        return new SessionConfiguration(sessionInformationExpiredStrategy,invalidSessionStrategy,browserProperties);
    }

}
