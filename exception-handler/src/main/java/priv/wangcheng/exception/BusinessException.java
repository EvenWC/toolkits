package priv.wangcheng.exception;

/**
 * @Auther: Administrator
 * @Date: 2018/8/30 21:55
 * @Description:
 */
public class BusinessException extends RuntimeException {

    public BusinessException(){}

    public BusinessException(String message){
        super(message);
    }

    /**
     * 业务异常不需要打印堆栈信息，复写fillInStackTrace方法，提升性能
     * @return
     */
    @Override
    public synchronized Throwable fillInStackTrace(){return this;}

}
