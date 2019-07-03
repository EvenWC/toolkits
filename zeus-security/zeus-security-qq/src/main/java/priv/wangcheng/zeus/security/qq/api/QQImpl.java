/**
 * 
 */
package priv.wangcheng.zeus.security.qq.api;

import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import priv.wangcheng.common.utils.JsonUtils;
import priv.wangcheng.common.utils.StringFormatter;

/**
 * @author wangcheng
 * @version $Id: QQImpl.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token={}";
	
	private static final String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key={}&openid={}";
	
	private String appId;
	
	private String openId;

	public QQImpl(String accessToken, String appId) {
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		
		this.appId = appId;
		
		String url = StringFormatter.format(URL_GET_OPENID, accessToken);
		String result = getRestTemplate().getForObject(url, String.class);
		logger.info("获取到的结果信息：{}",result);
		String jsonResult = result.substring(result.indexOf("(") + 1, result.indexOf(")"));
		Map resultData = JsonUtils.readValue(jsonResult, Map.class);
        Assert.state(Objects.nonNull(resultData),"序列化结果信息失败");
        this.openId = resultData.get("openid").toString();
	}
	
	@Override
	public QQUserInfo getUserInfo() {
		String url = StringFormatter.format(URL_GET_USER_INFO, appId, openId);
		String result = getRestTemplate().getForObject(url, String.class);
		QQUserInfo userInfo = JsonUtils.readValue(result,QQUserInfo.class);
		userInfo.setOpenId(openId);
		Assert.state(Objects.nonNull(userInfo),"获取用户信息失败");
		return userInfo;
	}
}
