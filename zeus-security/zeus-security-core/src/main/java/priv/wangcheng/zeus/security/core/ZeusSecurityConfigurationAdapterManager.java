package priv.wangcheng.zeus.security.core;

import java.util.List;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

/**
 * @author wangcheng
 * @version $Id: ZeusSecurityConfigurationAdapterManager.java, v0.1 2019/6/1 21:14 wangcheng Exp $$
 */
public class ZeusSecurityConfigurationAdapterManager implements SecurityConfigurationAdapterManager {

    private List<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>> adapters;

    public ZeusSecurityConfigurationAdapterManager(
            List<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>> adapters) {
        this.adapters = adapters;
    }

    @Override
    public void apply(HttpSecurity http) throws Exception {
        for (int i = 0; i < adapters.size(); i++) {
            SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> config = adapters.get(i);
            http.apply(config);
        }
    }
}
