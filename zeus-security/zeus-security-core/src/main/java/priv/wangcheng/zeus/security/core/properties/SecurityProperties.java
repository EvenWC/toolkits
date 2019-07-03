package priv.wangcheng.zeus.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wangcheng
 * @version $Id: SecurityProperties.java, v0.1 2019/5/26 12:13 wangcheng Exp $$
 */
@ConfigurationProperties(prefix = "zeus.security")
public class SecurityProperties {


    private BrowserProperties browser = new BrowserProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();

    private Oauth2Propertites oauth2Propertites = new Oauth2Propertites();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    public Oauth2Propertites getOauth2Propertites() {
        return oauth2Propertites;
    }

    public void setOauth2Propertites(Oauth2Propertites oauth2Propertites) {
        this.oauth2Propertites = oauth2Propertites;
    }
}
