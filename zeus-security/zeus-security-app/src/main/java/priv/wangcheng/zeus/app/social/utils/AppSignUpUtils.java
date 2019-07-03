package priv.wangcheng.zeus.app.social.utils;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;
import priv.wangcheng.zeus.app.exception.AppSecurityException;

/**
 * @author: Administrator
 * @date: 2019/5/18 09:11
 * @description:
 */
public class AppSignUpUtils implements InitializingBean {

    private RedisTemplate<Object,Object> redisTemplate;

    private UsersConnectionRepository usersConnectionRepository;

    private ConnectionFactoryLocator connectionFactoryLocator;

    public AppSignUpUtils(RedisTemplate<Object, Object> redisTemplate,
            ObjectProvider<UsersConnectionRepository> usersConnectionRepository,
            ObjectProvider<ConnectionFactoryLocator> connectionFactoryLocator) {
        this.redisTemplate = redisTemplate;
        this.usersConnectionRepository = usersConnectionRepository.getIfUnique();
        this.connectionFactoryLocator = connectionFactoryLocator.getIfUnique();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(redisTemplate,"require a RedisTemplate:" + RedisTemplate.class.getName());
        Assert.notNull(usersConnectionRepository,"require a UsersConnectionRepository:" + UsersConnectionRepository.class.getName());
        Assert.notNull(connectionFactoryLocator,"require a ConnectionFactoryLocator:" + ConnectionFactoryLocator.class.getName());
    }

    /**
     * 保存第三方用户信息到redis
     * @param request
     * @param connectionData
     */
    public void saveConnectionData(WebRequest request, ConnectionData connectionData){
        redisTemplate.opsForValue().set(getKey(request),connectionData,10,TimeUnit.MINUTES);
    }

    /**
     * 注册用户信息
     * @param request
     */
    public void doPostSignUp(String userId,WebRequest request){
        String key = getKey(request);
        if(!redisTemplate.hasKey(key)){
            throw new AppSecurityException("缓存中不存在第三方用户信息");
        }
        ConnectionData connectionData = (ConnectionData)redisTemplate.opsForValue().get(key);
        Connection<?> connection = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId()).createConnection(connectionData);
        usersConnectionRepository.createConnectionRepository(userId).addConnection(connection);
        redisTemplate.delete(key);
    }

    /**
     * 获取保存进缓存中的key
     * @param request
     * @return
     */
    private String getKey(WebRequest request) {
        String deviseId = request.getHeader("deviseId");
        if(StringUtils.isEmpty(deviseId)){
            throw new AppSecurityException("请求中缺少deviseId");
        }
        return "imooc:security:deviseId."+deviseId;
    }


}
