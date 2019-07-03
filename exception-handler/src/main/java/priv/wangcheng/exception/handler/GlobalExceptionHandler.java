package priv.wangcheng.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletRequestAttributes;
import priv.wangcheng.common.support.ResponseModel;
import priv.wangcheng.exception.holder.ExceptionHandleStrategyHolder;
import priv.wangcheng.exception.strategy.ExceptionHandleStrategy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangcheng
 */
@RestControllerAdvice
@RestController
public class GlobalExceptionHandler implements ErrorController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ExceptionHandleStrategyHolder strategyHolder;

    private final ErrorProperties errorProperties;

    private final ErrorAttributes errorAttributes;

    public GlobalExceptionHandler(ErrorAttributes errorAttributes,ErrorProperties errorProperties,ExceptionHandleStrategyHolder strategyHolder){
        this.errorProperties = errorProperties;
        this.errorAttributes = errorAttributes;
        this.strategyHolder = strategyHolder;
    }
    @ExceptionHandler(value = {Throwable.class})
    public Object handleException(HttpServletRequest request, HttpServletResponse response, Throwable e){
        logger.info("开始处理异常："+e.getClass().getName());
        ExceptionHandleStrategy exceptionHandler = strategyHolder.getStrategy(e);
        logger.info("获取异常处理策略："+exceptionHandler.toString());
        return exceptionHandler.handle(request,response,e);
    }
    @RequestMapping("${server.error.path:${error.path:/error}}")
    public Object error(HttpServletRequest request, HttpServletResponse response){
        Throwable error = errorAttributes.getError(new ServletRequestAttributes(request));
        return handleException(request,response,error);
    }
    @Override
    public String getErrorPath() {
        return errorProperties.getPath();
    }

    public ErrorProperties getErrorProperties() {
        return errorProperties;
    }
}
