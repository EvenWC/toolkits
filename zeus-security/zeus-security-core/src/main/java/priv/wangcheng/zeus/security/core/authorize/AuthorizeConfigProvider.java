package priv.wangcheng.zeus.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author wangcheng
 * @version $Id: AuthorizeConfigProviderManager.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public interface AuthorizeConfigProvider {

    /**
     *  抽象权限配置，各个模块需要配置的权限实现改接口
     * @param config
     */
    void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
