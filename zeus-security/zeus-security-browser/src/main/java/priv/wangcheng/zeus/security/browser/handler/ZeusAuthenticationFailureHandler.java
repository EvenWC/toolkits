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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import priv.wangcheng.common.support.ResponseModel;
import priv.wangcheng.common.utils.HttpResponseWriter;
import priv.wangcheng.common.utils.JsonUtils;
import priv.wangcheng.zeus.security.core.properties.BrowserProperties;
import priv.wangcheng.zeus.security.core.properties.LoginResponseType;

/**
 * @author wangcheng
 * @version $Id: ZeusAuthenticationFailureHandler.java, v0.1 2019/5/26 17:55 wangcheng Exp $$
 */
public class ZeusAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());
	

	private BrowserProperties browserProperties;

	public ZeusAuthenticationFailureHandler(
			BrowserProperties browserProperties) {
		this.browserProperties = browserProperties;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		logger.info("登录失败");
		
		if (LoginResponseType.JSON.equals(browserProperties.getLoginType())) {
			HttpResponseWriter.write(response,JsonUtils.writeValueAsString(ResponseModel.ERROR(exception.getMessage())));
		}else{
			super.onAuthenticationFailure(request, response, exception);
		}
	}
}
