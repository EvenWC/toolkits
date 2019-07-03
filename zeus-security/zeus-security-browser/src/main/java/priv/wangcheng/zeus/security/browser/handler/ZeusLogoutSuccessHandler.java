package priv.wangcheng.zeus.security.browser.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import priv.wangcheng.common.support.ResponseModel;
import priv.wangcheng.common.utils.HttpResponseWriter;
import priv.wangcheng.common.utils.JsonUtils;
import priv.wangcheng.zeus.security.core.properties.BrowserProperties;

/**
 * @author wangcheng
 * @version $Id: ZeusLogoutSuccessHandler.java, v0.1 2019/5/26 17:55 wangcheng Exp $$
 */
public class ZeusLogoutSuccessHandler implements LogoutSuccessHandler {

    private BrowserProperties browserProperties;

    public ZeusLogoutSuccessHandler(BrowserProperties browserProperties) {
        this.browserProperties = browserProperties;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String signOutUrl = browserProperties.getSignOutUrl();
        if(StringUtils.isEmpty(signOutUrl)){
            HttpResponseWriter.write(response,JsonUtils.writeValueAsString(ResponseModel.SUCCESS("退出登录成功")));
        }else{
            response.sendRedirect(signOutUrl);
        }
    }
}
