package priv.wangcheng.exception;

import java.util.Objects;

/**
 * @author: evan
 * @Date: 2018/9/4 21:54
 * @Description: 业务断言时使用
 */
public class Assert {

    private Assert() throws IllegalAccessException {
        throw new IllegalAccessException("不能实例化");
    }
    public static void nonNull(Object o,String message){
        if(Objects.isNull(o)){
            throw new BusinessException(message);
        }
    }
    public static void isTrue(Boolean condition,String message){
        if(!condition){
            throw new BusinessException(message);
        }
    }
    public static void isFalse(Boolean condition,String message){
        if(condition){
            throw new BusinessException(message);
        }
    }
}
