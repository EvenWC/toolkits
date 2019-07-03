package priv.wangcheng.zeus.security.core.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author wangcheng
 * @version $Id: ValidateCodeRepository.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码
     */
    String saveValidateCode(ServletWebRequest request, ValidateCodeType validateCodeType, ValidateCode code);
    /**
     * 获取验证码
     */
    ValidateCode getValidateCode(ServletWebRequest request, ValidateCodeType validateCodeType);
    /**
     * 移除验证码
     */
    void removeValidateCode(ServletWebRequest request, ValidateCodeType validateCodeType);

}
