/**
 * 
 */
package priv.wangcheng.zeus.app.handle;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.util.Assert;
import priv.wangcheng.common.utils.HttpResponseWriter;
import priv.wangcheng.common.utils.JsonUtils;

/**
 *
 */
public class ZeusAppAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements
		InitializingBean {

	private Logger logger = LoggerFactory.getLogger(getClass());


	private ClientDetailsService clientDetailsService;

    private AuthorizationServerTokenServices authorizationServerTokenServices;

	public ZeusAppAuthenticationSuccessHandler(
			ObjectProvider<ClientDetailsService> clientDetailsService,
			ObjectProvider<AuthorizationServerTokenServices> authorizationServerTokenServices) {
		this.clientDetailsService = clientDetailsService.getIfUnique();
		this.authorizationServerTokenServices = authorizationServerTokenServices.getIfUnique();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(clientDetailsService,"require a ClientDetailsService:" + ClientDetailsService.class.getName());
		Assert.notNull(authorizationServerTokenServices,"require a AuthorizationServerTokenServices:" + AuthorizationServerTokenServices.class.getName());

	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.info("登录成功");
		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Basic ")) {
			throw new UnapprovedClientAuthenticationException("请求头缺少client信息");
		}
		String[] tokens = extractAndDecodeHeader(header);
		assert tokens.length == 2;
		String clientId = tokens[0];
		String clientSecret = tokens[1];
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if(clientDetails == null){
            throw new UnapprovedClientAuthenticationException("不存在相关的client信息，clientId:" + clientId);
        }else if(!org.apache.commons.lang.StringUtils.equals(clientSecret,clientDetails.getClientSecret())){
            throw new UnapprovedClientAuthenticationException("clientSecret 传入错误 ，clientId:" + clientId);
        }
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP,clientId,clientDetails.getScope(),"custom");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);
        OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
		HttpResponseWriter.write(response, JsonUtils.writeValueAsString(accessToken));
	}


	private String[] extractAndDecodeHeader(String header)
			throws IOException {

		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		}
		catch (IllegalArgumentException e) {
			throw new BadCredentialsException(
					"Failed to decode basic authentication token");
		}

		String token = new String(decoded, "UTF-8");

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}

}
