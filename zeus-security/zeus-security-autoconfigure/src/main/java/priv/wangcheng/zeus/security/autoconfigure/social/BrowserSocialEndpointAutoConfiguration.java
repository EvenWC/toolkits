package priv.wangcheng.zeus.security.autoconfigure.social;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.web.ProviderSignInUtils;
import priv.wangcheng.zeus.security.browser.BrowserSocialEndpoint;

/**
 * @author wangcheng
 * @version $Id: BrowserSocialEndPointAutoConfiguration.java, v0.1 2019/6/1 17:40 wangcheng Exp $$
 */
@Configuration
@ConditionalOnClass({SocialConfigurerAdapter.class, BrowserSocialEndpoint.class})
@AutoConfigureAfter(SocialAutoConfiguration.class)
public class BrowserSocialEndpointAutoConfiguration {

    @Bean
    public BrowserSocialEndpoint browserSocialEndpoint(ProviderSignInUtils providerSignInUtils){
        return new BrowserSocialEndpoint(providerSignInUtils);
    }

}
