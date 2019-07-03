package priv.wangcheng.zeus.app.social.process;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import priv.wangcheng.zeus.security.core.social.ZeusSpringSocialConfigurer;

/**
 * @author: Administrator
 * @date: 2019/5/18 10:03
 * @description:
 */
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(StringUtils.equals(beanName,"zeusSocialSecurityConfigurer")){
            ZeusSpringSocialConfigurer configurer = (ZeusSpringSocialConfigurer)bean;
            configurer.signupUrl("/zeus/signup");
        }
        return bean;
    }
}
