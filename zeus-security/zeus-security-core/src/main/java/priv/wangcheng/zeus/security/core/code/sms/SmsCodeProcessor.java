/**
 * 
 */
package priv.wangcheng.zeus.security.core.code.sms;

import java.util.Map;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import priv.wangcheng.zeus.security.core.code.ValidateCode;
import priv.wangcheng.zeus.security.core.code.ValidateCodeGenerator;
import priv.wangcheng.zeus.security.core.code.ValidateCodeRepository;
import priv.wangcheng.zeus.security.core.code.impl.AbstractValidateCodeProcessor;
import priv.wangcheng.zeus.security.core.properties.SecurityConstants;

/**
 * @author wangcheng
 * @version $Id: SmsCodeProcessor.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

	/**
	 * 短信验证码发送器
	 */
	private SmsCodeSender smsCodeSender;


	public SmsCodeProcessor(Map<String, ValidateCodeGenerator> validateCodeGenerators,
		 ValidateCodeRepository validateCodeRepository,SmsCodeSender smsCodeSender) {
		super(validateCodeGenerators,validateCodeRepository);
		this.smsCodeSender = smsCodeSender;
	}

	@Override
	protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
		String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
		String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
		smsCodeSender.send(mobile, validateCode.getCode());
	}

}
