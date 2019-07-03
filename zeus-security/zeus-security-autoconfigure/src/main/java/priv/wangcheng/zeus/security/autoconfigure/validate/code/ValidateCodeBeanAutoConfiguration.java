package priv.wangcheng.zeus.security.autoconfigure.validate.code;

import java.util.Map;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import priv.wangcheng.zeus.security.core.code.ValidateCodeEndpoint;
import priv.wangcheng.zeus.security.core.code.ValidateCodeFilter;
import priv.wangcheng.zeus.security.core.code.ValidateCodeProcessor;
import priv.wangcheng.zeus.security.core.code.ValidateCodeProcessorHolder;
import priv.wangcheng.zeus.security.core.properties.SecurityProperties;

/**
 * @author wangcheng
 * @version $Id: ValidateCodeBeanConfiguration.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
@Configuration
@AutoConfigureAfter(ValidateCodeProcessorAutoConfiguration.class)
public class ValidateCodeBeanAutoConfiguration {
	
	private SecurityProperties securityProperties;

	private Map<String, ValidateCodeProcessor> validateCodeProcessors;

	public ValidateCodeBeanAutoConfiguration(SecurityProperties securityProperties,
			Map<String, ValidateCodeProcessor> validateCodeProcessors) {
		this.securityProperties = securityProperties;
		this.validateCodeProcessors = validateCodeProcessors;
	}
	@Bean
	public ValidateCodeEndpoint validateCodeEndpoint(){
		return new ValidateCodeEndpoint(validateCodeProcessorHolder());
	}
	@Bean
	public ValidateCodeProcessorHolder validateCodeProcessorHolder(){
		return new ValidateCodeProcessorHolder(validateCodeProcessors);
	}

	@Bean
	public ValidateCodeFilter validateCodeFilter(ObjectProvider<AuthenticationFailureHandler> authenticationFailureHandler){
		return new ValidateCodeFilter(authenticationFailureHandler,securityProperties,validateCodeProcessorHolder());
	}
}
