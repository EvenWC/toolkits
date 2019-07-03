/**
 * 
 */
package priv.wangcheng.zeus.security.weixin.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import priv.wangcheng.zeus.security.weixin.api.Weixin;
import priv.wangcheng.zeus.security.weixin.api.WeixinImpl;

/**
 * 
 * 微信的OAuth2流程处理器的提供器，供spring social的connect体系调用
 * @author wangcheng
 * @version $Id: WeixinServiceProvider.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public class WeixinServiceProvider extends AbstractOAuth2ServiceProvider<Weixin> {
	
	/**
	 * 微信获取授权码的url
	 */
	private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";
	/**
	 * 微信获取accessToken的url
	 */
	private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

	/**
	 * @param appId
	 * @param appSecret
	 */
	public WeixinServiceProvider(String appId, String appSecret) {
		super(new WeixinOAuth2Template(appId, appSecret,URL_AUTHORIZE,URL_ACCESS_TOKEN));
	}

	@Override
	public Weixin getApi(String accessToken) {
		return new WeixinImpl(accessToken);
	}

}
