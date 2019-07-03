package priv.wangcheng.zeus.app.jwt;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * @author: Administrator
 * @date: 2019/5/18 22:23
 * @description:
 */
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String,Object> data = new HashMap<>();
        data.put("company","525qz");
        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(data);
        return accessToken;
    }
}
