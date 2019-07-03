/**
 * 
 */
package priv.wangcheng.zeus.security.weixin.api;

/**
 *
 * @author wangcheng
 * @version $Id: Weixin.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public interface Weixin {

	/**
	 * 获取用户信息
	 * @param openId
	 * @return
	 */
	WeixinUserInfo getUserInfo(String openId);
}
