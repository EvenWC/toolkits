package priv.wangcheng.zeus.security.autoconfigure.validate.code;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import priv.wangcheng.zeus.security.core.code.ValidateCodeFilter;

/**
 * @author wangcheng
 * @version $Id: ValidateCodeAutoConfiguration.java, v0.1 2019/6/1 17:53 wangcheng Exp $$
 */
@Configuration
@ConditionalOnClass(ValidateCodeFilter.class)
@AutoConfigureAfter(ValidateCodeBeanAutoConfiguration.class)
public class ValidateCodeAutoConfiguration {

    @Configuration
    public class ValidateCodeSecurityConfiguration extends
            SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

        private ValidateCodeFilter validateCodeFilter;

        public ValidateCodeSecurityConfiguration(ValidateCodeFilter validateCodeFilter) {
            this.validateCodeFilter = validateCodeFilter;
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
        }
    }
}
