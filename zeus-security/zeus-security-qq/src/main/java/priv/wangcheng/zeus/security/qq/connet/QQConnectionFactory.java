/**
 * 
 */
package priv.wangcheng.zeus.security.qq.connet;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import priv.wangcheng.zeus.security.qq.api.QQ;

/**
 * @author wangcheng
 * @version $Id: QQConnectionFactory.java, v0.1 2019/5/27 22:20 wangcheng Exp $$
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

	public QQConnectionFactory(String providerId, String appId, String appSecret) {
		super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
	}
}
