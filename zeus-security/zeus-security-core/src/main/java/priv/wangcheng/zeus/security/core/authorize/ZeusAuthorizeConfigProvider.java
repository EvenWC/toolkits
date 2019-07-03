package priv.wangcheng.zeus.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import priv.wangcheng.zeus.security.core.properties.SecurityConstants;
import priv.wangcheng.zeus.security.core.properties.SecurityProperties;

/**
 * @author wangcheng
 * @version $Id: ZeusAuthorizeConfigProvider.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public class ZeusAuthorizeConfigProvider implements AuthorizeConfigProvider {

    private SecurityProperties securityProperties;

    public ZeusAuthorizeConfigProvider(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(
                SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                SecurityConstants.DEFAULT_LOGOUT_URL,
                securityProperties.getBrowser().getLoginPage(),
                securityProperties.getBrowser().getSignUpUrl(),
                securityProperties.getBrowser().getSession().getSessionInvalidUrl()+".json",
                securityProperties.getBrowser().getSession().getSessionInvalidUrl()+".html"
        ).permitAll();
    }
}
