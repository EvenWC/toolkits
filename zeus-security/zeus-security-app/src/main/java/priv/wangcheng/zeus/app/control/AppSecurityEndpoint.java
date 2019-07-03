package priv.wangcheng.zeus.app.control;


import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import priv.wangcheng.zeus.app.social.utils.AppSignUpUtils;
import priv.wangcheng.zeus.security.core.social.SocialUserInfo;

/**
 * @author: wangcheng
 * @date: 2019/5/18 10:10
 * @description:
 */
@RestController
public class AppSecurityEndpoint {

    private ProviderSignInUtils providerSignInUtils;

    private AppSignUpUtils appSignUpUtils;


    public AppSecurityEndpoint(ObjectProvider<ProviderSignInUtils> providerSignInUtils,
            AppSignUpUtils appSignUpUtils) {
        this.providerSignInUtils = providerSignInUtils.getIfUnique();
        this.appSignUpUtils = appSignUpUtils;
    }

    @RequestMapping("/zeus/signup")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        SocialUserInfo userInfo = new SocialUserInfo();
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadimg(connection.getImageUrl());
        //保存进redis
        appSignUpUtils.saveConnectionData(new ServletWebRequest(request), connection.createData());
        return userInfo;
    }
}
