package priv.wangcheng.zeus.security.core.social;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author: wangcheng
 * @version $Id: SocialAuthenticationToken.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public class SocialAuthenticationToken extends AbstractAuthenticationToken {

    private Object principal;

    private String providerId;


    public SocialAuthenticationToken(String principal,String providerId){
        super(null);
        this.principal = principal;
        this.providerId = providerId;
    }

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public SocialAuthenticationToken(Collection<? extends GrantedAuthority> authorities,Object principal,String providerId) {
        super(authorities);
        this.principal = principal;
        this.providerId = providerId;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    public void setPrincipal(Object principal) {
        this.principal = principal;
    }
    @Override
    public Object getPrincipal() {
        return principal;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
