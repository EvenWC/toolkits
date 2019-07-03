package priv.wangcheng.zeus.security.autoconfigure.validate.code;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import priv.wangcheng.zeus.security.autoconfigure.core.SecurityCoreAutoConfiguration;
import priv.wangcheng.zeus.security.core.code.ValidateCodeGenerator;
import priv.wangcheng.zeus.security.core.code.ValidateCodeRepository;
import priv.wangcheng.zeus.security.core.code.image.ImageCodeGenerator;
import priv.wangcheng.zeus.security.core.code.image.ImageCodeProcessor;
import priv.wangcheng.zeus.security.core.code.sms.DefaultSmsCodeSender;
import priv.wangcheng.zeus.security.core.code.sms.SmsCodeGenerator;
import priv.wangcheng.zeus.security.core.code.sms.SmsCodeProcessor;
import priv.wangcheng.zeus.security.core.code.sms.SmsCodeSender;
import priv.wangcheng.zeus.security.core.properties.SecurityProperties;

/**
 * @author wangcheng
 * @version $Id: ValidateCodeProcessorAutoConfiguration.java, v0.1 2019/6/1 18:28 wangcheng Exp $$
 */
@Configuration
@AutoConfigureAfter(SecurityCoreAutoConfiguration.class)
public class ValidateCodeProcessorAutoConfiguration {

    private SecurityProperties securityProperties;

    public ValidateCodeProcessorAutoConfiguration(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }


    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        return new ImageCodeGenerator(securityProperties);
    }

    @Bean
    @ConditionalOnMissingBean(name = "smsValidateCodeGenerator")
    public ValidateCodeGenerator smsValidateCodeGenerator() {
        return new SmsCodeGenerator(securityProperties);
    }

    @Bean
    public ImageCodeProcessor imageValidateCodeProcessor(Map<String, ValidateCodeGenerator> validateCodeGenerators,
            ValidateCodeRepository validateCodeRepository) {
        return new ImageCodeProcessor(validateCodeGenerators,validateCodeRepository);
    }

    @Bean
    public SmsCodeProcessor smsValidateCodeProcessor(SmsCodeSender smsCodeSender,Map<String, ValidateCodeGenerator> validateCodeGenerators,
            ValidateCodeRepository validateCodeRepository) {
        return new SmsCodeProcessor(validateCodeGenerators,validateCodeRepository,smsCodeSender);
    }

}
