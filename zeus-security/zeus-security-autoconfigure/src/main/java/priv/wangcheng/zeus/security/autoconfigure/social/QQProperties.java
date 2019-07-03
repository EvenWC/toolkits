package priv.wangcheng.zeus.security.autoconfigure.social;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @author wangcheng
 * @version $Id: QQProperties.java, v0.1 2019/5/28 19:48 wangcheng Exp $$
 */
public class QQProperties extends SocialProperties {
	
	private String providerId = "qq";

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
}
