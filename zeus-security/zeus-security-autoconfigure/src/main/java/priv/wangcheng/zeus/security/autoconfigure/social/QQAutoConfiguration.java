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
import priv.wangcheng.zeus.security.qq.connet.QQConnectionFactory;

/**
 * @author wangcheng
 * @version $Id: QQAutoConfiguration.java, v0.1 2019/5/28 19:48 wangcheng Exp $$
 */
@Configuration
@ConditionalOnClass({ SocialConfigurerAdapter.class, QQConnectionFactory.class })
@ConditionalOnProperty(prefix = "zeus.social.qq", name = "app-id")
@AutoConfigureBefore(SocialWebAutoConfiguration.class)
@AutoConfigureAfter(SocialAutoConfiguration.class)
public class QQAutoConfiguration {



    @Configuration
    @ConditionalOnWebApplication
    protected static class QQConfigurerAdapter extends SocialAutoConfigurerAdapter {

        private final QQProperties properties;

        protected QQConfigurerAdapter(SocialProperties properties) {
            this.properties = properties.getQq();
        }


        @Bean({"connect/qqConnect", "connect/qqConnected"})
        @ConditionalOnMissingBean(name = "qqConnectedView")
        public View qqConnectedView() {
            return new GenericConnectionStatusView(properties.getProviderId(),"腾讯QQ");
        }

        @Override
        protected ConnectionFactory<?> createConnectionFactory() {
            return new QQConnectionFactory(this.properties.getProviderId(),this.properties.getAppId(),
                    this.properties.getAppSecret());
        }
    }
}
