package priv.wangcheng.zeus.security.core;

import java.util.List;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @author wangcheng
 * @version $Id: ZeusHttpSecurityDetailConfigurationManager.java, v0.1 2019/6/1 21:47 wangcheng Exp $$
 */
public class ZeusHttpSecurityDetailConfigurationManager implements HttpSecurityDetailConfigurationManager {

    private List<HttpSecurityDetailConfiguration> configurations;

    public ZeusHttpSecurityDetailConfigurationManager(
            List<HttpSecurityDetailConfiguration> configurations) {
        this.configurations = configurations;
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        for (int i = 0; i < configurations.size(); i++) {
            configurations.get(i).configure(http);
        }
    }
}
