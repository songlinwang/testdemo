package example.demo.cache;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;
import example.demo.util.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author wsl
 * @date 2018/11/26
 */
@Component // 交给spring加入到IOC容器
@Aspect // 这是一个切面
public class AopCacheAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopCacheAspect.class);

    @Resource
    private RedisUtil redisUtil;

    @Around("@annotation(example.demo.cache.GetCache)")
    public Object dealWithCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object cacheObject;
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object[] params = proceedingJoinPoint.getArgs(); // 获得入参列表
        // 获取目标对象
        Object target = proceedingJoinPoint.getTarget();
        String className = target.getClass().getName();
        String key = genKey(method, className,params);
        if (redisUtil.get(key) == null) { //  不含有该缓存
            cacheObject = proceedingJoinPoint.proceed();
            redisUtil.set(key,JSONObject.toJSON(cacheObject));
        } else {
            Class<?> returnType = ((MethodSignature) proceedingJoinPoint.getSignature()).getReturnType();
            cacheObject = JSONObject.parseObject(String.valueOf(redisUtil.get(key)), returnType);
        }
        return cacheObject;
    }

    /**
     * key的生成方式需要改
     *
     * @param method
     * @param className
     * @return
     */
    public String genKey(Method method, String className,Object[] params) {
        StringBuilder sb = new StringBuilder();
        GetCache getCache = method.getAnnotation(GetCache.class);
        String cacheId = StringUtils.isNullOrEmpty(getCache.id()) ? "EMPTY_ID" : getCache.id();
        for(Object o : params){
            sb.append(cacheId).append(o.toString()).append("_");
        }
        sb.append(className);
        return sb.toString();
    }
}
