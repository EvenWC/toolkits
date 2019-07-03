/**
 * 
 */
package priv.wangcheng.zeus.security.browser.session;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.session.InvalidSessionStrategy;

/**
 * @author wangcheng
 * @version $Id: ZeusInvalidSessionStrategy.java, v0.1 2019/5/26 17:55 wangcheng Exp $$
 */
public class ZeusInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

	public ZeusInvalidSessionStrategy(String invalidSessionUrl) {
		super(invalidSessionUrl);
	}

	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		onSessionInvalid(request, response);
	}

}
