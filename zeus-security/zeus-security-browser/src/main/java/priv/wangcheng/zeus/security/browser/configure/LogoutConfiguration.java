package priv.wangcheng.zeus.security.browser.configure;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.Assert;
import priv.wangcheng.zeus.security.core.HttpSecurityDetailConfiguration;
import priv.wangcheng.zeus.security.core.properties.SecurityConstants;

/**
 * @author wangcheng
 * @version $Id: LogoutConfiguration.java, v0.1 2019/6/1 0:33 wangcheng Exp $$
 */
public class LogoutConfiguration implements HttpSecurityDetailConfiguration, InitializingBean {

    private LogoutSuccessHandler logoutSuccessHandler;

    public LogoutConfiguration(
           ObjectProvider<LogoutSuccessHandler> logoutSuccessHandler) {
        this.logoutSuccessHandler = logoutSuccessHandler.getIfUnique();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(logoutSuccessHandler,"require a logoutSuccessHandler：" + LogoutSuccessHandler.class.getName());

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.logout()
                .logoutUrl(SecurityConstants.DEFAULT_LOGOUT_URL)
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies(SecurityConstants.LOGOUT_DELETE_COOKIE);

    }
}
