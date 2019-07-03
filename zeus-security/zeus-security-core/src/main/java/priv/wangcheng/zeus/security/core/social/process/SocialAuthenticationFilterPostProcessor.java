package priv.wangcheng.zeus.security.core.social.process;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 *  社交登陆后置处理器
 * @author wangcheng
 * @version $Id: SocialAuthenticationFilterPostProcessor.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public interface SocialAuthenticationFilterPostProcessor {

    /**
     *
     * @param filter
     */
    void process(SocialAuthenticationFilter filter);
}
