package priv.wangcheng.exception.strategy;

import priv.wangcheng.common.support.ResponseModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author : Administrator
 * @Date: 2018/8/30 22:19
 * @Description: 抽象出来处理异常的策略接口
 */
public interface ExceptionHandleStrategy {
    /**
     * 处理异常的接口
     */
    Object handle(HttpServletRequest request, HttpServletResponse response, Throwable e);

    /**
     *  支持处理的异常类型
     */
    List<Class<? extends Throwable>> supports();
}
