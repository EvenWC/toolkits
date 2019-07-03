package priv.wangcheng.zeus.security.demo.security;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import priv.wangcheng.zeus.security.core.authorize.RbacSevice;

/**
 * @author: Administrator
 * @date: 2019/5/19 17:04
 * @description:
 */
@Component("rbacSevice")
public class DemoRbacSevice implements RbacSevice {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        //匿名用户直接拒绝
        if(authentication instanceof AnonymousAuthenticationToken){
            return false;
        }
        Object principal = authentication.getPrincipal();
        String username;
        if(principal instanceof String){
            username = (String) principal;
        }else{
            username = ((UserDetails) principal).getUsername();
        }
        List<String> resources = loadResourceByUserName(username);
        boolean hasPermission = false;
        for (String path : resources) {
            if(antPathMatcher.match(path,request.getRequestURI())){
                hasPermission = true;
                break;
            }
        }
        return hasPermission;
    }
    private List<String> loadResourceByUserName(String username){
        //模拟加载的资源,这里资源简单处理为url 也可以抽象出来。
        List<String> resources = new ArrayList<>();
        resources.add("/user/me");
        resources.add("/");
        return resources;
    }
}
