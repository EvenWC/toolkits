package priv.wangcheng.zeus.security.browser.configure;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.util.Assert;
import priv.wangcheng.zeus.security.core.HttpSecurityDetailConfiguration;
import priv.wangcheng.zeus.security.core.properties.BrowserProperties;

/**
 * @author wangcheng
 * @version $Id: SessionConfiguration.java, v0.1 2019/6/1 0:24 wangcheng Exp $$
 */
public class SessionConfiguration implements HttpSecurityDetailConfiguration, InitializingBean {

    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    private InvalidSessionStrategy invalidSessionStrategy;

    private BrowserProperties browserProperties;

    public SessionConfiguration(
           ObjectProvider<SessionInformationExpiredStrategy> sessionInformationExpiredStrategy,
            ObjectProvider<InvalidSessionStrategy> invalidSessionStrategy,
            BrowserProperties browserProperties) {
        this.sessionInformationExpiredStrategy = sessionInformationExpiredStrategy.getIfUnique();
        this.invalidSessionStrategy = invalidSessionStrategy.getIfUnique();
        this.browserProperties = browserProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(sessionInformationExpiredStrategy,"require a sessionInformationExpiredStrategy：" + SessionInformationExpiredStrategy.class.getName());
        Assert.notNull(invalidSessionStrategy,"require a invalidSessionStrategy：" + InvalidSessionStrategy.class.getName());

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy)
                .maximumSessions(browserProperties.getSession().getMaximumSessions())
                .maxSessionsPreventsLogin(browserProperties.getSession().isMaxSessionsPreventsLogin())
                .expiredSessionStrategy(sessionInformationExpiredStrategy);
    }
}
