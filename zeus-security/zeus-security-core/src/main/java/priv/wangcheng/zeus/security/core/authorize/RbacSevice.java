package priv.wangcheng.zeus.security.core.authorize;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * @author wangcheng
 * @version $Id: RbacSevice.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public interface RbacSevice {

    boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
