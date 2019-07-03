package priv.wangcheng.zeus.security.autoconfigure.core;

import java.util.List;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpointHandlerMapping;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import priv.wangcheng.zeus.security.core.HttpCsrfConfiguration;
import priv.wangcheng.zeus.security.core.HttpSecurityDetailConfiguration;
import priv.wangcheng.zeus.security.core.HttpSecurityDetailConfigurationManager;
import priv.wangcheng.zeus.security.core.PasswordAuthenticationConfiguration;
import priv.wangcheng.zeus.security.core.SecurityConfigurationAdapterManager;
import priv.wangcheng.zeus.security.core.ZeusHttpSecurityDetailConfigurationManager;
import priv.wangcheng.zeus.security.core.ZeusSecurityConfigurationAdapterManager;
import priv.wangcheng.zeus.security.core.properties.SecurityProperties;

/**
 * @author wangcheng
 * @version $Id: SecurityCodeAutoConfiguration.java, v0.1 2019/6/1 18:48 wangcheng Exp $$
 */
@Configuration
@EnableConfigurationProperties({SecurityProperties.class})
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class SecurityCoreAutoConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RequestMappingHandlerMapping frameworkEndpointHandlerMapping(){
        return new FrameworkEndpointHandlerMapping();
    }

    @Bean
    public SecurityConfigurationAdapterManager zeusSecurityConfigurationAdapterManager(
            List<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>> adapters){
        return new ZeusSecurityConfigurationAdapterManager(adapters);
    }

    @Bean
    public HttpSecurityDetailConfiguration httpCsrfConfiguration(){
        return new HttpCsrfConfiguration();
    }
    @Bean
    public PasswordAuthenticationConfiguration passwordAuthenticationConfiguration(
            ObjectProvider<AuthenticationSuccessHandler> successHandlerObjectProvider,
            ObjectProvider<AuthenticationFailureHandler> failureHandlerObjectProvider){
        return new PasswordAuthenticationConfiguration(successHandlerObjectProvider,failureHandlerObjectProvider);
    }

    @Bean
    public HttpSecurityDetailConfigurationManager zeusHttpSecurityDetailConfigurationManager(List<HttpSecurityDetailConfiguration> configurations){
        return new ZeusHttpSecurityDetailConfigurationManager(configurations);
    }

}
