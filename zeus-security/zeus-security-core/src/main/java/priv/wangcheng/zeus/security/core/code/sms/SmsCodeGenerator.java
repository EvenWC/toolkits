/**
 * 
 */
package priv.wangcheng.zeus.security.core.code.sms;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.context.request.ServletWebRequest;
import priv.wangcheng.zeus.security.core.code.ValidateCode;
import priv.wangcheng.zeus.security.core.code.ValidateCodeGenerator;
import priv.wangcheng.zeus.security.core.properties.SecurityProperties;

/**
 * @author wangcheng
 * @version $Id: SmsCodeGenerator.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public class SmsCodeGenerator implements ValidateCodeGenerator {

	private SecurityProperties securityProperties;

	public SmsCodeGenerator(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

	@Override
	public ValidateCode generate(ServletWebRequest request) {
		String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
		return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}
}
