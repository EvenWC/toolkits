package priv.wangcheng.exception.strategy.impl;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.wangcheng.common.support.ResponseModel;
import priv.wangcheng.exception.strategy.AbstractExceptionHandleStrategy;

import java.util.List;

/**
 * @author Administrator
 * @Date: 2018/9/4 21:00
 * @Description:
 */
public class DefaultExceptionHandleStrategy extends AbstractExceptionHandleStrategy  {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object doHandle(Throwable e) {
        //记录日志
        StackTraceElement[] stackTrace = e.getStackTrace();
        String className = stackTrace[0].getClassName();
        int lineNumber = stackTrace[0].getLineNumber();
        String methodName = stackTrace[0].getMethodName();
        boolean nativeMethod = stackTrace[0].isNativeMethod();
        StringBuffer sb = new StringBuffer();
        sb.append("className:"+className +"\n");
        sb.append("lineNumber:" +lineNumber +"\n");
        sb.append("methodName:" +methodName +"\n");
        sb.append("isNativeMethod:" +nativeMethod +"\n");
        logger.error(sb.toString(),e);
        return ResponseModel.ERROR(e.getMessage());
    }
    @Override
    public List<Class<? extends Throwable>> supports() {
        return Lists.newArrayList(Throwable.class);
    }
}
