package priv.wangcheng.zeus.app.social.process;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import priv.wangcheng.zeus.security.core.social.process.SocialAuthenticationFilterPostProcessor;

/**
 * @author: Administrator
 * @date: 2019/5/17 23:42
 * @description:
 */
public class AppSocialAuthenticationFilterPostProcessor implements SocialAuthenticationFilterPostProcessor {

    private AuthenticationSuccessHandler zeusAppAuthenticationSuccessHandler;

    public AppSocialAuthenticationFilterPostProcessor(
            AuthenticationSuccessHandler zeusAppAuthenticationSuccessHandler) {
        this.zeusAppAuthenticationSuccessHandler = zeusAppAuthenticationSuccessHandler;
    }

    @Override
    public void process(SocialAuthenticationFilter filter) {
        filter.setAuthenticationSuccessHandler(zeusAppAuthenticationSuccessHandler);
    }
}
