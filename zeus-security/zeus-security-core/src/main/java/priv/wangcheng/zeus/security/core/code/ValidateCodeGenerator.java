/**
 * 
 */
package priv.wangcheng.zeus.security.core.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author wangcheng
 * @version $Id: ValidateCodeGenerator.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public interface ValidateCodeGenerator {

	/**
	 * 生成验证码
	 * @param request
	 * @return
	 */
	ValidateCode generate(ServletWebRequest request);
	
}
