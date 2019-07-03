package priv.wangcheng.zeus.security.core.code;

import priv.wangcheng.zeus.security.core.properties.SecurityConstants;

/**
 * @author wangcheng
 * @version $Id: ValidateCodeSecurityConfig.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public enum ValidateCodeType {
	/**
	 * 短信验证码
	 */
	SMS {
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
		}
	},
	/**
	 * 图片验证码
	 */
	IMAGE {
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
		}
	};

	/**
	 * 校验时从请求中获取的参数的名字
	 * @return
	 */
	public abstract String getParamNameOnValidate();
}
