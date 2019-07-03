package priv.wangcheng.common.utils;

import org.slf4j.helpers.MessageFormatter;

/**
 * @author wangcheng
 * @version $Id: StringFormatter.java, v0.1 2019/5/27 23:04 wangcheng Exp $$
 */
public class StringFormatter {

    private StringFormatter() throws IllegalAccessException {
        throw new IllegalAccessException("工具类禁止实例化");
    }

    /**
     * 格式化一个带有占位符的字符串
     * @param message 原始字符串
     * @param args 占位符替换值
     * @return 格式化之后的结果
     */
    public static String format(String message,Object... args){
        return MessageFormatter.arrayFormat(message,args).getMessage();
    }
}
