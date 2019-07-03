package priv.wangcheng.zeus.security.core.authorize;

import java.util.List;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author wangcheng
 * @version $Id: ZeusAuthorizeConfigProviderManager.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public class ZeusAuthorizeConfigProviderManager implements AuthorizeConfigProviderManager{

    private List<AuthorizeConfigProvider> providers;

    public ZeusAuthorizeConfigProviderManager(
            List<AuthorizeConfigProvider> providers) {
        this.providers = providers;
    }

    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        providers.forEach(provider-> provider.configure(config));
    }
}
