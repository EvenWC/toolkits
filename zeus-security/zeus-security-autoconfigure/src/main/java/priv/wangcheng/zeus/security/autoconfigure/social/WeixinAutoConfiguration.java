package priv.wangcheng.zeus.security.autoconfigure.social;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.boot.autoconfigure.social.SocialWebAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.GenericConnectionStatusView;
import org.springframework.web.servlet.View;
import priv.wangcheng.zeus.security.weixin.connect.WeixinConnectionFactory;

/**
 * @author wangcheng
 * @version $Id: WeixinAutoConfiguration.java, v0.1 2019/5/28 22:24 wangcheng Exp $$
 */
@Configuration
@ConditionalOnClass({SocialConfigurerAdapter.class,WeixinConnectionFactory.class})
@ConditionalOnProperty(prefix = "zeus.social.weixin", name = "app-id")
@AutoConfigureBefore(SocialWebAutoConfiguration.class)
@AutoConfigureAfter(SocialAutoConfiguration.class)
public class WeixinAutoConfiguration {


    @Configuration
    @ConditionalOnWebApplication
    protected static class WeixinConfigurerAdapter extends SocialAutoConfigurerAdapter {

        private final WeixinProperties properties;

        protected WeixinConfigurerAdapter(SocialProperties socialProperties) {
            this.properties = socialProperties.getWeixin();
        }

        @Bean({"connect/weixinConnect", "connect/weixinConnected"})
        @ConditionalOnMissingBean(name = "weixinConnectedView")
        public View weixinConnectedView() {
            return new GenericConnectionStatusView(properties.getProviderId(),"腾讯QQ");
        }
        @Override
        protected ConnectionFactory<?> createConnectionFactory() {
            return new WeixinConnectionFactory(this.properties.getProviderId(),this.properties.getAppId(),
                    this.properties.getAppSecret());
        }
    }


}
