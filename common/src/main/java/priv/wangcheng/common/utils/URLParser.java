package priv.wangcheng.common.utils;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangcheng
 * @version $Id: URLParser.java, v0.1 2019/5/27 23:27 wangcheng Exp $$
 */
public class URLParser {

    private static final Logger logger = LoggerFactory.getLogger(URLParser.class);

    /**
     * 解析出url请求的路径(如果有参数，那么取“？”前面那部分)
     * @param strURL url地址
     * @return url路径
     */
    public static String subHostUrl(String strURL){
        if(StringUtils.isEmpty(strURL)){
            throw new IllegalArgumentException("url 不能为空");
        }
        logger.info("开始解析url页面，url:{}",strURL);
        strURL = strURL.trim().toLowerCase();
        return strURL.split("[?]")[0];
    }
    /**
     * 截取url中的参数部分的url路径
     * @param strURL url地址
     * @return url请求参数部分
     */
    public static String subParamUrl(String strURL){
        String urlPage = subHostUrl(strURL);
        if(urlPage.length() == strURL.length() || urlPage.length() == strURL.length() - 1){
            return "";
        }
        return strURL.substring(urlPage.length() + 1);
    }
    /**
     * 解析出url参数中的键值对
     * @param strUrlParam  url地址
     * @return  参数的key value
     */
    public static Map<String, String> urlParams(String strUrlParam){
        Map<String, String> params = Maps.newHashMap();
        if(StringUtils.isEmpty(strUrlParam)){
            return params;
        }
        String[] arrSplit = strUrlParam.split("[&]");
        for(String strSplit : arrSplit){
            String[] kvArr= strSplit.split("[=]");
            //解析出键值
            String key = kvArr[0].trim();
            if(StringUtils.isNotEmpty(key)){
                String value = kvArr.length == 2 ? kvArr[1].trim() : "";
                params.put(key,value);
            }
        }
        return params;
    }
}
