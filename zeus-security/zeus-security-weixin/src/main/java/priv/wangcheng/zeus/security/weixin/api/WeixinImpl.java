/**
 * 
 */
package priv.wangcheng.zeus.security.weixin.api;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.util.Assert;
import priv.wangcheng.common.utils.JsonUtils;

/**
 * Weixin API调用模板， scope为Request的Spring bean, 根据当前用户的accessToken创建。
 * 
 * @author wangcheng
 * @version $Id: WeixinImpl.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public class WeixinImpl extends AbstractOAuth2ApiBinding implements Weixin {
	/**
	 * 获取用户信息的url
	 */
	private static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=";
	
	/**
	 * @param accessToken
	 */
	public WeixinImpl(String accessToken) {
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
	}
	
	/**
	 * 默认注册的StringHttpMessageConverter字符集为ISO-8859-1，而微信返回的是UTF-8的，所以覆盖了原来的方法。
	 */
	@Override
	protected List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
		messageConverters.remove(0);
		messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return messageConverters;
	}

	/**
	 * 获取微信用户信息。
	 */
	@Override
	public WeixinUserInfo getUserInfo(String openId) {
		String url = URL_GET_USER_INFO + openId;
		String response = getRestTemplate().getForObject(url, String.class);
        if(response.contains("errcode")) {
			return null;
		}
		WeixinUserInfo profile = JsonUtils.readValue(response,WeixinUserInfo.class);
        Assert.state(Objects.nonNull(profile),"获取用户信息失败");
		return profile;
	}

}
