package priv.wangcheng.exception.holder.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ConcurrentReferenceHashMap;
import priv.wangcheng.exception.holder.ExceptionHandleStrategyHolder;
import priv.wangcheng.exception.strategy.ExceptionHandleStrategy;
import priv.wangcheng.exception.strategy.impl.DefaultExceptionHandleStrategy;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author  evan
 * @Date: 2018/9/26 21:12
 * @Description: 提供策略支持
 */
public class BaseSpringExceptionHandleStrategyHolder  implements ExceptionHandleStrategyHolder,ApplicationContextAware {

    private  final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 保存异常以及对应的策略信息
     */
    private Map<Class<? extends Throwable>,ExceptionHandleStrategy> strategyMap = new ConcurrentReferenceHashMap<>();
    /**
     * 默认的异常处理策略
     */
    private ExceptionHandleStrategy defaultExceptionHandleStrategy = new DefaultExceptionHandleStrategy();

    @Override
    public ExceptionHandleStrategy getStrategy(Throwable e) {
        ExceptionHandleStrategy strategy = strategyMap.get(e.getClass());
        return Objects.isNull(strategy) ? defaultExceptionHandleStrategy : strategy;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.info("开始加载异常处理策略");
        Map<String, ExceptionHandleStrategy> strategyBeans = applicationContext.getBeansOfType(ExceptionHandleStrategy.class);
        Assert.state(CollectionUtils.isEmpty(strategyMap),"没有加载到异常处理策略对象");
        loadThrowableAndStrategy(strategyBeans);
        logger.info("加载到异常处理策略:[{}]",printStrategyMap());
    }

    /**
     * 加载异常和策略
     * @param strategyBeans 异常策略类型
     */
    private void loadThrowableAndStrategy(Map<String, ExceptionHandleStrategy> strategyBeans){
        strategyBeans.forEach((strategyName,strategy)->{
            List<Class<? extends Throwable>> supports = strategy.supports();
            supports.forEach(t->strategyMap.put(t,strategy));
        });
    }
    /**
     * 打印策略map
     * @return
     */
    private String printStrategyMap(){
        StringBuffer stringBuffer = new StringBuffer();
        strategyMap.forEach((key,value)->
            stringBuffer.append("\n{")
                        .append(key.toString())
                        .append(":")
                        .append(value)
                        .append("}\n")
        );
        return stringBuffer.toString();
    }
}
