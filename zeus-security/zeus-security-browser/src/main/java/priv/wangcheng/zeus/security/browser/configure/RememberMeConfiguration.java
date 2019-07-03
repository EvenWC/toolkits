package priv.wangcheng.zeus.security.browser.configure;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.util.Assert;
import priv.wangcheng.zeus.security.core.HttpSecurityDetailConfiguration;
import priv.wangcheng.zeus.security.core.properties.BrowserProperties;

/**
 * @author wangcheng
 * @version $Id: RememberMeConfiguration.java, v0.1 2019/6/1 0:17 wangcheng Exp $$
 */
public class RememberMeConfiguration implements HttpSecurityDetailConfiguration, InitializingBean {

    private PersistentTokenRepository persistentTokenRepository;

    private BrowserProperties browserProperties;

    private  UserDetailsService userDetailsService;

    public RememberMeConfiguration(
            ObjectProvider<PersistentTokenRepository> persistentTokenRepository,
            BrowserProperties browserProperties,
            ObjectProvider<UserDetailsService> userDetailsService) {
        this.persistentTokenRepository = persistentTokenRepository.getIfUnique();
        this.browserProperties = browserProperties;
        this.userDetailsService = userDetailsService.getIfUnique();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(persistentTokenRepository,"require a persistentTokenRepository：" + PersistentTokenRepository.class.getName());
        Assert.notNull(userDetailsService,"require a userDetailsService：" + UserDetailsService.class.getName());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.rememberMe()
                .tokenRepository(persistentTokenRepository)
                .tokenValiditySeconds(browserProperties.getRememberMeSeconds())
                .userDetailsService(userDetailsService);
    }


}
