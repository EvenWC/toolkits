package priv.wangcheng.zeus.security.demo.security;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;
import priv.wangcheng.zeus.security.core.authorize.AuthorizeConfigProvider;
import priv.wangcheng.zeus.security.core.properties.SecurityProperties;

/**
 * @author: Administrator
 * @date: 2019/5/19 16:16
 * @description:
 */
@Component
@Order
public class DemoAuthorizeProvider implements AuthorizeConfigProvider {

    @Autowired
    private SecurityProperties securityProperties;
    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        String signOutUrl = securityProperties.getBrowser().getSignOutUrl();
        if(StringUtils.isNotEmpty(signOutUrl)){
            config.antMatchers(signOutUrl).permitAll();
        }
        config.anyRequest()
                .access("@rbacSevice.hasPermission(request,authentication)");
    }
}
