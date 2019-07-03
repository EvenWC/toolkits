/**
 * 
 */
package priv.wangcheng.zeus.security.core.code.sms;

/**
 * @author wangcheng
 * @version $Id: DefaultSmsCodeSender.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

	@Override
	public void send(String mobile, String code) {
		System.out.println("向手机"+mobile+"发送短信验证码"+code);
	}

}
