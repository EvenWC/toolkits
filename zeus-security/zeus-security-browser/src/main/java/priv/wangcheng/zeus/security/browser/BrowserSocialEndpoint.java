package priv.wangcheng.zeus.security.browser;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import priv.wangcheng.zeus.security.core.social.SocialUserInfo;

/**
 * @author wangcheng
 * @version $Id: BrowserSocialEndpoint.java, v0.1 2019/6/1 17:00 wangcheng Exp $$
 */
@RestController
public class BrowserSocialEndpoint implements InitializingBean {

    private ProviderSignInUtils providerSignInUtils;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(providerSignInUtils,"require a providerSignInUtils：" + ProviderSignInUtils.class.getName());
    }

    public BrowserSocialEndpoint(ProviderSignInUtils providerSignInUtils) {
        this.providerSignInUtils = providerSignInUtils;
    }

    @GetMapping("/social/user")
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        SocialUserInfo userInfo = new SocialUserInfo();
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadimg(connection.getImageUrl());
        return userInfo;
    }
}
