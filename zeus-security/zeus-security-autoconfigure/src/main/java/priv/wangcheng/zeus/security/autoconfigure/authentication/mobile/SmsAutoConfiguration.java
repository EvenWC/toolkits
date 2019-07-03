/**
 * 
 */
package priv.wangcheng.zeus.security.autoconfigure.authentication.mobile;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.social.SocialWebAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.util.Assert;
import priv.wangcheng.zeus.security.core.authentication.mobile.SmsCodeAuthenticationFilter;
import priv.wangcheng.zeus.security.core.authentication.mobile.SmsCodeAuthenticationProvider;


/**
 * @author wangcheng
 * @version $Id: SmsCodeAuthenticationSecurityConfiguration.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
@Configuration
@ConditionalOnClass({SocialConfigurerAdapter.class})
@AutoConfigureBefore(SocialWebAutoConfiguration.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class SmsAutoConfiguration{

    @Configuration
    public class SmsCodeAuthenticationSecurityConfiguration extends
            SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> implements InitializingBean {

        private AuthenticationSuccessHandler authenticationSuccessHandler;

        private AuthenticationFailureHandler authenticationFailureHandler;

        private UserDetailsService userDetailsService;

        public SmsCodeAuthenticationSecurityConfiguration(
                ObjectProvider<AuthenticationSuccessHandler> authenticationSuccessHandler,
                ObjectProvider<AuthenticationFailureHandler> authenticationFailureHandler,
                ObjectProvider<UserDetailsService> userDetailsService) {
            this.authenticationSuccessHandler = authenticationSuccessHandler.getIfUnique();
            this.authenticationFailureHandler = authenticationFailureHandler.getIfUnique();
            this.userDetailsService = userDetailsService.getIfUnique();
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            Assert.notNull(authenticationSuccessHandler,"require a authentication success handler：" + AuthenticationFailureHandler.class.getName());
            Assert.notNull(authenticationFailureHandler,"require a authentication failure handler：" + AuthenticationSuccessHandler.class.getName());
            Assert.notNull(userDetailsService,"require a user detail service:"+ UserDetailsService.class.getName());
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {

            SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
            smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
            smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
            smsCodeAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

            SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider(userDetailsService);

            http.authenticationProvider(smsCodeAuthenticationProvider)
                    .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        }


    }
}