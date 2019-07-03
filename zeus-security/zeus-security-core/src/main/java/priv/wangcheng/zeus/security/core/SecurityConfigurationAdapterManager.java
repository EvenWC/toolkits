package priv.wangcheng.zeus.security.core;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @author wangcheng
 * @version $Id: SecurityConfigurationAdapterManager.java, v0.1 2019/6/1 21:09 wangcheng Exp $$
 */
public interface SecurityConfigurationAdapterManager {

    /**
     * 配置安全模块
     * @param http
     */
    void apply(HttpSecurity http) throws Exception;

}
