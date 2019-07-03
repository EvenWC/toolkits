package priv.wangcheng.zeus.security.core;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.Assert;
import priv.wangcheng.zeus.security.core.HttpSecurityDetailConfiguration;
import priv.wangcheng.zeus.security.core.properties.SecurityConstants;

/**
 * 用户名密码配置
 * @author wangcheng
 * @version $Id: PasswordAuthenticationConfiguration.java, v0.1 2019/6/1 0:02 wangcheng Exp $$
 */
public class PasswordAuthenticationConfiguration implements HttpSecurityDetailConfiguration, InitializingBean {

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    private AuthenticationFailureHandler authenticationFailureHandler;

    public PasswordAuthenticationConfiguration(
           ObjectProvider<AuthenticationSuccessHandler> authenticationSuccessHandler, ObjectProvider<AuthenticationFailureHandler> authenticationFailureHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler.getIfUnique();
        this.authenticationFailureHandler = authenticationFailureHandler.getIfUnique();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(authenticationSuccessHandler,"require a authentication success handler：" + AuthenticationFailureHandler.class.getName());
        Assert.notNull(authenticationFailureHandler,"require a authentication failure handler：" + AuthenticationSuccessHandler.class.getName());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);
    }

}
