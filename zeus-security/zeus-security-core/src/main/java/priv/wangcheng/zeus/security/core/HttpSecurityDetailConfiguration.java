package priv.wangcheng.zeus.security.core;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @author wangcheng
 * @version $Id: xx.java, v0.1 2019/5/31 23:59 wangcheng Exp $$
 */
public interface HttpSecurityDetailConfiguration {

    /**
     * 配置安全模块明细项
     * @param http
     */
    void configure(HttpSecurity http) throws Exception;

}
