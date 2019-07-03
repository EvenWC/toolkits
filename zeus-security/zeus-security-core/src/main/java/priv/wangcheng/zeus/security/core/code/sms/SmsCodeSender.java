/**
 * 
 */
package priv.wangcheng.zeus.security.core.code.sms;
/**
 * @author wangcheng
 * @version $Id: SmsCodeSender.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public interface SmsCodeSender {

	/**
	 * 发送短息验证码
	 * @param mobile 手机号
	 * @param code    验证码
	 */
	void send(String mobile, String code);
}
