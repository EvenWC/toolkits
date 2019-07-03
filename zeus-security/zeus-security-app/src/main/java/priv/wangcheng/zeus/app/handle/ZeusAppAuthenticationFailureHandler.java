/**
 * 
 */
package priv.wangcheng.zeus.app.handle;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import priv.wangcheng.common.support.ResponseModel;
import priv.wangcheng.common.utils.HttpResponseWriter;
import priv.wangcheng.common.utils.JsonUtils;

/**
 *
 */
public class ZeusAppAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());
	

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		logger.info("登录失败");
		HttpResponseWriter.write(response, JsonUtils.writeValueAsString(ResponseModel.ERROR(exception.getMessage())));
	}

}
