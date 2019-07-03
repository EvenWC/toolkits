package priv.wangcheng.zeus.security.browser;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import priv.wangcheng.common.support.ResponseModel;
import priv.wangcheng.zeus.security.core.properties.BrowserProperties;
import priv.wangcheng.zeus.security.core.properties.SecurityConstants;

/**
 * @author wangcheng
 * @version $Id: BrowserSecurityController.java, v0.1 2019/5/26 17:55 wangcheng Exp $$
 */
@RestController
public class BrowserSecurityEndPoint {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private RequestCache requestCache = new HttpSessionRequestCache();

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	private BrowserProperties browserProperties;


	public BrowserSecurityEndPoint(BrowserProperties browserProperties) {
		this.browserProperties = browserProperties;
	}

	/**
	 * 当需要身份认证时，跳转到这里
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public ResponseModel requireAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		SavedRequest savedRequest = requestCache.getRequest(request, response);

		if (savedRequest != null) {
			String targetUrl = savedRequest.getRedirectUrl();
			logger.info("引发跳转的请求是:" + targetUrl);
			if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
				redirectStrategy.sendRedirect(request, response,browserProperties.getLoginPage());
			}
		}
		return ResponseModel.ERROR("访问的服务需要身份认证，请引导用户到登录页");
	}
}
