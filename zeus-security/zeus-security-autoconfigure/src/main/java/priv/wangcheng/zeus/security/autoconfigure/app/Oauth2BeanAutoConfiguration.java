package priv.wangcheng.zeus.security.autoconfigure.app;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.util.Assert;
import priv.wangcheng.zeus.app.code.RedisValidateCodeRepository;
import priv.wangcheng.zeus.app.control.AppSecurityEndpoint;
import priv.wangcheng.zeus.app.handle.ZeusAppAuthenticationFailureHandler;
import priv.wangcheng.zeus.app.handle.ZeusAppAuthenticationSuccessHandler;
import priv.wangcheng.zeus.app.social.process.AppSocialAuthenticationFilterPostProcessor;
import priv.wangcheng.zeus.app.social.process.SpringSocialConfigurerPostProcessor;
import priv.wangcheng.zeus.app.social.utils.AppSignUpUtils;
import priv.wangcheng.zeus.security.autoconfigure.core.SecurityCoreAutoConfiguration;
import priv.wangcheng.zeus.security.core.properties.SecurityProperties;
import priv.wangcheng.zeus.security.core.social.process.SocialAuthenticationFilterPostProcessor;

/**
 * @author wangcheng
 * @version $Id: Oauth2BeanAutoConfiguration.java, v0.1 2019/6/2 0:20 wangcheng Exp $$
 */
@Configuration
@ConditionalOnClass(AppSecurityEndpoint.class)
@AutoConfigureAfter(SecurityCoreAutoConfiguration.class)
public class Oauth2BeanAutoConfiguration implements InitializingBean {


    private RedisTemplate<Object,Object> redisTemplate;


    public Oauth2BeanAutoConfiguration(ObjectProvider<RedisTemplate<Object,Object>> redisTemplateObjectProvider) {
        this.redisTemplate = redisTemplateObjectProvider.getIfUnique();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(redisTemplate,"require a :" + RedisTemplate.class.getName());
    }

    @Bean
    public AuthenticationSuccessHandler appAuthenticationSuccessHandler(ObjectProvider<ClientDetailsService> clientDetailsService,
            ObjectProvider<AuthorizationServerTokenServices> authorizationServerTokenServices){
        return new ZeusAppAuthenticationSuccessHandler(clientDetailsService,authorizationServerTokenServices);
    }
    @Bean
    public AuthenticationFailureHandler appAuthenticationFailureHandler(){
        return new ZeusAppAuthenticationFailureHandler();
    }
    @Bean
    public AppSecurityEndpoint appSecurityEndpoint(ObjectProvider<ProviderSignInUtils> providerSignInUtils,AppSignUpUtils appSignUpUtils){
        return new AppSecurityEndpoint(providerSignInUtils,appSignUpUtils);
    }

    @Bean
    public AppSignUpUtils appSignUpUtils(ObjectProvider<UsersConnectionRepository> usersConnectionRepository,
            ObjectProvider<ConnectionFactoryLocator> connectionFactoryLocator){
        return new AppSignUpUtils(redisTemplate,usersConnectionRepository,connectionFactoryLocator);
    }

    @Bean
    public SocialAuthenticationFilterPostProcessor appSocialAuthenticationFilterPostProcessor(AuthenticationSuccessHandler authenticationSuccessHandler){
        return new AppSocialAuthenticationFilterPostProcessor(authenticationSuccessHandler);
    }
    @Bean
    public SpringSocialConfigurerPostProcessor springSocialConfigurerPostProcessor(){
        return new SpringSocialConfigurerPostProcessor();
    }
    @Bean
    public RedisValidateCodeRepository redisValidateCodeRepository(){
        return new RedisValidateCodeRepository(redisTemplate);
    }
}
