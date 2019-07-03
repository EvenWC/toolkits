package priv.wangcheng.zeus.security.autoconfigure.social;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.social.SocialWebAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.util.Assert;
import priv.wangcheng.zeus.security.core.social.SocialAuthenticationProcessingFilter;
import priv.wangcheng.zeus.security.core.social.SocialAuthenticationProvider;

/**
 * @author: wangcheng
 * @version $Id: OpenIdAuthenticationSecurityConfiguration.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
@Configuration
@ConditionalOnClass({SocialConfigurerAdapter.class})
@AutoConfigureBefore(SocialWebAutoConfiguration.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class OpenIdAutoConfiguration{


    @Configuration
    @ConditionalOnWebApplication
    public class OpenIdAuthenticationSecurityConfiguration extends
            SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> implements InitializingBean{

        private UsersConnectionRepository usersConnectionRepository;

        private SocialUserDetailsService socialUserDetailsService;

        private AuthenticationSuccessHandler authenticationSuccessHandler;

        private AuthenticationFailureHandler authenticationFailureHandler;

        public OpenIdAuthenticationSecurityConfiguration(
                ObjectProvider<UsersConnectionRepository> usersConnectionRepository,
                ObjectProvider<SocialUserDetailsService> socialUserDetailsService,
                ObjectProvider<AuthenticationSuccessHandler> authenticationSuccessHandler,
                ObjectProvider<AuthenticationFailureHandler> authenticationFailureHandler) {
            this.usersConnectionRepository = usersConnectionRepository.getIfUnique();
            this.socialUserDetailsService = socialUserDetailsService.getIfUnique();
            this.authenticationSuccessHandler = authenticationSuccessHandler.getIfUnique();
            this.authenticationFailureHandler = authenticationFailureHandler.getIfUnique();
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            Assert.notNull(authenticationSuccessHandler,"require a authentication success handler：" + AuthenticationFailureHandler.class.getName());
            Assert.notNull(authenticationFailureHandler,"require a authentication failure handler：" + AuthenticationSuccessHandler.class.getName());
            Assert.notNull(usersConnectionRepository,"require a users connection repository:"+ UsersConnectionRepository.class.getName());
            Assert.notNull(socialUserDetailsService,"require a users social user detail service:"+ SocialUserDetailsService.class.getName());
        }
        @Override
        public void configure(HttpSecurity http) throws Exception {
            SocialAuthenticationProcessingFilter processingFilter = new SocialAuthenticationProcessingFilter();
            processingFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
            processingFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
            processingFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
            SocialAuthenticationProvider provider = new SocialAuthenticationProvider(usersConnectionRepository,socialUserDetailsService);
            http.authenticationProvider(provider)
                    .addFilterBefore(processingFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }

}