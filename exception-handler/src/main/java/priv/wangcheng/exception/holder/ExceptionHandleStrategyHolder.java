package priv.wangcheng.exception.holder;

import priv.wangcheng.exception.strategy.ExceptionHandleStrategy;

/**
 * @author : Administrator
 * @Date: 2018/9/26 20:55
 * @Description: 用来
 */

public interface ExceptionHandleStrategyHolder {
    /***
     *   通过异常创建策略
     * @param e
     * @return
     */
    ExceptionHandleStrategy getStrategy(Throwable e);

}
