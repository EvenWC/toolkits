package priv.wangcheng.zeus.security.core;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @author wangcheng
 * @version $Id: HttpSecurityDetailConfigurationManager.java, v0.1 2019/6/1 21:46 wangcheng Exp $$
 */
public interface HttpSecurityDetailConfigurationManager {

    void configure(HttpSecurity http) throws Exception;

}
