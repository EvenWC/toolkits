package priv.wangcheng.exception.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import priv.wangcheng.exception.strategy.impl.BusinessExceptionHandleStrategy;
import priv.wangcheng.exception.strategy.impl.DefaultExceptionHandleStrategy;

/**
 * @author: Administrator
 * @date: 2019/6/7 16:05
 * @description:
 */
@Configuration
public class ExceptionHandleStrategyAutoConfiguration {


    @Bean
    public BusinessExceptionHandleStrategy businessExceptionHandleStrategy(){
        return new BusinessExceptionHandleStrategy();
    }

    @Bean
    public DefaultExceptionHandleStrategy defaultExceptionHandleStrategy(){
        return new DefaultExceptionHandleStrategy();
    }

}
