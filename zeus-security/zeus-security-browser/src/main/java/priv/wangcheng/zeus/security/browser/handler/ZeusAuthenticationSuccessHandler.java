/**
 * 
 */
package priv.wangcheng.zeus.security.browser.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import priv.wangcheng.common.utils.HttpResponseWriter;
import priv.wangcheng.common.utils.JsonUtils;
import priv.wangcheng.zeus.security.core.properties.BrowserProperties;
import priv.wangcheng.zeus.security.core.properties.LoginResponseType;

/**
 * @author wangcheng
 * @version $Id: ZeusAuthenticationSuccessHandler.java, v0.1 2019/5/26 17:55 wangcheng Exp $$
 */
public class ZeusAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private BrowserProperties browserProperties;

	public ZeusAuthenticationSuccessHandler(
			BrowserProperties browserProperties) {
		this.browserProperties = browserProperties;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		logger.info("登录成功");

		if (LoginResponseType.JSON.equals(browserProperties.getLoginType())) {
			HttpResponseWriter.write(response,JsonUtils.writeValueAsString(authentication));
		} else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}
}
