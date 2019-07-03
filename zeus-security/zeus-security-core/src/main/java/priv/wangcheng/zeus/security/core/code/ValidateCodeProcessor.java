/**
 * 
 */
package priv.wangcheng.zeus.security.core.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 *  验证码处理
 * @author wangcheng
 * @version $Id: ValidateCodeGenerator.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public interface ValidateCodeProcessor {

	/**
	 * 创建校验码
	 * 
	 * @param request
	 * @throws Exception
	 */
	void create(ServletWebRequest request) throws Exception;

	/**
	 * 校验验证码
	 * 
	 * @param servletWebRequest
	 * @throws Exception
	 */
	void validate(ServletWebRequest servletWebRequest);

}
