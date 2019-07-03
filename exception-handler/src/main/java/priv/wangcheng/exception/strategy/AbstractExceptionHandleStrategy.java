package priv.wangcheng.exception.strategy;

import org.springframework.http.HttpStatus;
import priv.wangcheng.common.support.ResponseModel;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : Administrator
 * @Date: 2018/9/27 20:10
 * @Description:
 */
public abstract class AbstractExceptionHandleStrategy implements ExceptionHandleStrategy {

    /**
     * 处理异常的接口
     */
    @Override
    public final Object handle(HttpServletRequest request, HttpServletResponse response, Throwable e){
        preHandle( request,  response,  e);
        return doHandle(e);
    }

    /***
     * 预处理
     */
    private void preHandle(HttpServletRequest request, HttpServletResponse response, Throwable e){
        //设置状态为200
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
    }

    /**
     * 执行处理逻辑
     * @param e
     * @return
     */
    public abstract Object doHandle(Throwable e);

    @Override
    public String toString() {
        return getClass().getName();
    }
}
