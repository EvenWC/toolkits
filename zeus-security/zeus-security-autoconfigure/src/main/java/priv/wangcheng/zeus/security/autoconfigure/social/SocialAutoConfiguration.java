/**
 * 
 */
package priv.wangcheng.zeus.security.autoconfigure.social;

import javax.sql.DataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.social.SocialWebAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.util.Assert;
import org.springframework.web.servlet.View;
import priv.wangcheng.zeus.security.browser.BrowserSocialEndpoint;
import priv.wangcheng.zeus.security.core.social.ZeusConnectionStatusView;
import priv.wangcheng.zeus.security.core.social.ZeusSpringSocialConfigurer;
import priv.wangcheng.zeus.security.core.social.jdbc.ZeusJdbcUsersConnectionRepository;
import priv.wangcheng.zeus.security.core.social.process.SocialAuthenticationFilterPostProcessor;

/**
 * @author: wangcheng
 * @version $Id: SocialAutoConfiguration.java, v0.1 2019/5/29 12:39 wangcheng Exp $$
 */
@Configuration
@ConditionalOnClass({ SocialConfigurerAdapter.class})
@AutoConfigureBefore(SocialWebAutoConfiguration.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class SocialAutoConfiguration {


	@Configuration
	@EnableSocial
	@EnableConfigurationProperties(SocialProperties.class)
	protected static class SocialAutoConfigurerAdapter extends SocialConfigurerAdapter{

		private ObjectProvider<DataSource> dataSource;

		private SocialProperties socialProperties;

		private ObjectProvider<ConnectionSignUp> connectionSignUpObjectProvider;

		private ObjectProvider<SocialAuthenticationFilterPostProcessor> socialAuthenticationFilterPostProcessorObjectProvider;

		public SocialAutoConfigurerAdapter(ObjectProvider<DataSource> dataSource,
				SocialProperties socialProperties, ObjectProvider<ConnectionSignUp> connectionSignUpObjectProvider,
				ObjectProvider<SocialAuthenticationFilterPostProcessor> socialAuthenticationFilterPostProcessorObjectProvider) {
			this.dataSource = dataSource;
			this.socialProperties = socialProperties;
			this.connectionSignUpObjectProvider = connectionSignUpObjectProvider;
			this.socialAuthenticationFilterPostProcessorObjectProvider = socialAuthenticationFilterPostProcessorObjectProvider;
		}

		@Override
		public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
            DataSource dataSource = this.dataSource.getIfUnique();
            Assert.notNull(dataSource,"缺少数据源配置");
            ZeusJdbcUsersConnectionRepository repository = new ZeusJdbcUsersConnectionRepository(dataSource,
					connectionFactoryLocator, Encryptors.noOpText());
			//repository.setTablePrefix("zeus_");
            ConnectionSignUp connectionSignUp = connectionSignUpObjectProvider.getIfUnique();
            if(connectionSignUp != null) {
				repository.setConnectionSignUp(connectionSignUp);
			}
			return repository;
		}

		@Bean
		public SpringSocialConfigurer zeusSocialSecurityConfigurer() {
			String filterProcessesUrl = socialProperties.getFilterProcessesUrl();
			ZeusSpringSocialConfigurer configurer = new ZeusSpringSocialConfigurer(filterProcessesUrl);
			configurer.signupUrl(socialProperties.getSignUpUrl());
            SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor = socialAuthenticationFilterPostProcessorObjectProvider
                    .getIfUnique();
            if(socialAuthenticationFilterPostProcessor != null){
				configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
			}
			return configurer;
		}

		@Bean
		public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
			return new ProviderSignInUtils(connectionFactoryLocator,
					getUsersConnectionRepository(connectionFactoryLocator)) {
			};
		}

		@Bean("connect/status")
        public View zeusConnectionStatusView(){
		    return new ZeusConnectionStatusView();
        }
	}
}
