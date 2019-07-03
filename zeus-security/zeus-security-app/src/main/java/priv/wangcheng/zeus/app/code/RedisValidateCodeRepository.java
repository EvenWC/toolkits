package priv.wangcheng.zeus.app.code;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.ServletWebRequest;
import priv.wangcheng.zeus.security.core.code.ValidateCode;
import priv.wangcheng.zeus.security.core.code.ValidateCodeRepository;
import priv.wangcheng.zeus.security.core.code.ValidateCodeType;

/**
 * @author: wangcheng
 * @date: 2019/5/12 15:50
 * @description:
 */
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    private RedisTemplate<Object,Object> redisTemplate;

    private final static String VALIDATE_CODE_TOKEN_PARAM_NAME = "deviceId";

    private String validateCodeTokenParamName = VALIDATE_CODE_TOKEN_PARAM_NAME;

    public RedisValidateCodeRepository(
            RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String saveValidateCode(ServletWebRequest request, ValidateCodeType validateCodeType, ValidateCode code) {
        String key = buildValidateCodeKey( request,validateCodeType);
        redisTemplate.opsForValue().set(buildValidateCodeKey( request,validateCodeType),code,30,TimeUnit.MINUTES);
        return key;
    }

    @Override
    public ValidateCode getValidateCode(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return (ValidateCode)redisTemplate.opsForValue().get(buildValidateCodeKey(request,validateCodeType));
    }

    @Override
    public void removeValidateCode(ServletWebRequest request, ValidateCodeType validateCodeType) {
        redisTemplate.delete(buildValidateCodeKey(request,validateCodeType));
    }

    private String buildValidateCodeKey(ServletWebRequest request, ValidateCodeType validateCodeType){
        String token = request.getHeader(validateCodeTokenParamName);
        Assert.hasText(token,"请求头缺少" + validateCodeTokenParamName +"参数");
        return  validateCodeType.getParamNameOnValidate() + "_" + token;
    }

}
