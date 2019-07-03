package priv.wangcheng.exception.configure;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import priv.wangcheng.exception.handler.GlobalExceptionHandler;
import priv.wangcheng.exception.holder.ExceptionHandleStrategyHolder;
import priv.wangcheng.exception.holder.impl.BaseSpringExceptionHandleStrategyHolder;
import priv.wangcheng.exception.properties.ExceptionHandlerProperties;

/**
 * @author: Administrator
 * @date: 2019/6/7 16:00
 * @description:
 */
@Configuration
@EnableConfigurationProperties(ExceptionHandlerProperties.class)
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
@Import(ExceptionHandleStrategyAutoConfiguration.class)
public class GlobalExceptionHandlerAutoConfiguration {

    @Bean
    public ExceptionHandleStrategyHolder exceptionHandleStrategyHolder(){
        return new BaseSpringExceptionHandleStrategyHolder();
    }
    @Bean
    public GlobalExceptionHandler globalExceptionHandler(ErrorAttributes errorAttributes, ServerProperties serverProperties, ExceptionHandleStrategyHolder strategyHolder){
        return new GlobalExceptionHandler(errorAttributes,serverProperties.getError(),strategyHolder);
    }

}
