package priv.wangcheng.zeus.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author wangcheng
 * @version $Id: AuthorizeConfigProviderManager.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public interface AuthorizeConfigProviderManager {

    /**
     * 各个模块的权限管理
     * @param config
     */
    void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
