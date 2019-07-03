/**
 * 
 */
package priv.wangcheng.zeus.security.core.code;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author wangcheng
 * @version $Id: ValidateCodeProcessorHolder.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public class ValidateCodeProcessorHolder {

	private Map<String, ValidateCodeProcessor> validateCodeProcessors;


	public ValidateCodeProcessorHolder(
			Map<String, ValidateCodeProcessor> validateCodeProcessors) {
		this.validateCodeProcessors = validateCodeProcessors;
	}

	public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
		return findValidateCodeProcessor(type.toString().toLowerCase());
	}

	public ValidateCodeProcessor findValidateCodeProcessor(String type) {
		String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
		ValidateCodeProcessor processor = validateCodeProcessors.get(name);
		if (processor == null) {
			throw new ValidateCodeException("验证码处理器" + name + "不存在");
		}
		return processor;
	}

}
