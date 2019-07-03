package priv.wangcheng.zeus.security.core.social;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import priv.wangcheng.zeus.security.core.properties.SecurityConstants;

/**
 *
 * @author: wangcheng
 * @version $Id: SocialAuthenticationProcessingFilter.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public class SocialAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private static final String SPRING_SECURITY_FORM_OPENID_KEY = "openId";

    private static final String SPRING_SECURITY_FORM_PROVIDER_ID_KEY = "providerId";

    private String openIdParameter = SPRING_SECURITY_FORM_OPENID_KEY;

    private String providerIdParameter = SPRING_SECURITY_FORM_PROVIDER_ID_KEY;

    private boolean postOnly = true;

    public SocialAuthenticationProcessingFilter() {
        super(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPENID);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String openId = obtainOpenId(request);
        String providerId = obtainProviderId(request);

        if (openId == null) {
            openId = "";
        }

        if (providerId == null) {
            providerId = "";
        }

        openId = openId.trim();

        SocialAuthenticationToken authRequest = new SocialAuthenticationToken(
                openId, providerId);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private String obtainProviderId(HttpServletRequest request) {
        return request.getParameter(providerIdParameter);
    }

    private String obtainOpenId(HttpServletRequest request) {
        return request.getParameter(openIdParameter);
    }
    protected void setDetails(HttpServletRequest request,
                              SocialAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
