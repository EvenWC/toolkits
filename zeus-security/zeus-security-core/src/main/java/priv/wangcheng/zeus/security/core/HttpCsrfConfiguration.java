package priv.wangcheng.zeus.security.core;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import priv.wangcheng.zeus.security.core.HttpSecurityDetailConfiguration;

/**
 * @author wangcheng
 * @version $Id: LogoutConfiguration.java, v0.1 2019/6/1 0:33 wangcheng Exp $$
 */
public class HttpCsrfConfiguration implements HttpSecurityDetailConfiguration {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }
}
