package priv.wangcheng.common.utils;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import priv.wangcheng.common.support.ResponseModel;

/**
 * @author wangcheng
 * @version $Id: ResponseWriter.java, v0.1 2019/6/1 17:10 wangcheng Exp $$
 */
public class HttpResponseWriter {

    private HttpResponseWriter() throws IllegalAccessException {
        throw new IllegalAccessException("工具类禁止实例化");
    }

    public static void write(HttpServletResponse response,String jsonStr) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jsonStr);
    }


}
