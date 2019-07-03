package priv.wangcheng.zeus.security.autoconfigure.browser;

import javax.sql.DataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.social.SocialWebAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.util.Assert;
import priv.wangcheng.zeus.security.browser.BrowserSecurityEndPoint;
import priv.wangcheng.zeus.security.browser.code.SessionValidateCodeRepository;
import priv.wangcheng.zeus.security.browser.handler.ZeusAuthenticationFailureHandler;
import priv.wangcheng.zeus.security.browser.handler.ZeusAuthenticationSuccessHandler;
import priv.wangcheng.zeus.security.browser.handler.ZeusLogoutSuccessHandler;
import priv.wangcheng.zeus.security.browser.session.ZeusExpiredSessionStrategy;
import priv.wangcheng.zeus.security.browser.session.ZeusInvalidSessionStrategy;
import priv.wangcheng.zeus.security.core.code.ValidateCodeRepository;
import priv.wangcheng.zeus.security.core.properties.BrowserProperties;

/**
 * @author wangcheng
 * @version $Id: BrowserDetailBeanAutoConfiguration.java, v0.1 2019/6/1 0:37 wangcheng Exp $$
 */
@Configuration
@ConditionalOnClass({SocialConfigurerAdapter.class, BrowserSecurityEndPoint.class})
@AutoConfigureBefore(SocialWebAutoConfiguration.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties(BrowserProperties.class)
public class BrowserBeanAutoConfiguration {

    private BrowserProperties browserProperties;

    public BrowserBeanAutoConfiguration(BrowserProperties browserProperties) {
        this.browserProperties = browserProperties;
    }

    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new ZeusInvalidSessionStrategy(browserProperties.getSession().getSessionInvalidUrl());
    }

    @Bean
    public BrowserSecurityEndPoint browserSecurityEndPoint(){
        return new BrowserSecurityEndPoint(browserProperties);
    }

    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return new ZeusExpiredSessionStrategy(browserProperties.getSession().getSessionInvalidUrl());
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationSuccessHandler.class)
    public AuthenticationSuccessHandler authenticationSuccessHandler(BrowserProperties browserProperties){
        return new ZeusAuthenticationSuccessHandler(browserProperties);
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationFailureHandler.class)
    public AuthenticationFailureHandler authenticationFailureHandler(BrowserProperties browserProperties){
        return new ZeusAuthenticationFailureHandler(browserProperties);
    }

    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new ZeusLogoutSuccessHandler(browserProperties);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(ObjectProvider<DataSource> dataSoure) {
        DataSource dataSource = dataSoure.getIfUnique();
        Assert.notNull(dataSource,"require a dataSource");
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
    @Bean
    public ValidateCodeRepository sessionValidateCodeRepository(){
        return new SessionValidateCodeRepository();
    }
}
