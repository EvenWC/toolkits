package priv.wangcheng.exception.strategy.impl;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.wangcheng.common.support.ResponseModel;
import priv.wangcheng.exception.BusinessException;
import priv.wangcheng.exception.strategy.AbstractExceptionHandleStrategy;

import java.util.List;

/**
 * @author : Administrator
 * @Date: 2018/8/30 21:52
 * @Description: 业务断言的处理策略
 */
public class BusinessExceptionHandleStrategy extends AbstractExceptionHandleStrategy {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object doHandle(Throwable e) {
        logger.warn(e.getMessage());
        return ResponseModel.ERROR(e.getMessage());
    }

    @Override
    public List<Class<? extends Throwable>> supports() {
        return Lists.newArrayList(BusinessException.class);
    }
}
