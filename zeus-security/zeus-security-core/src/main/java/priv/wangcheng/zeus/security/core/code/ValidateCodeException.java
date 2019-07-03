/**
 * 
 */
package priv.wangcheng.zeus.security.core.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @author wangcheng
 * @version $Id: ValidateCodeController.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public class ValidateCodeException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7285211528095468156L;

	public ValidateCodeException(String msg) {
		super(msg);
	}

}
