package example.demo.cache;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 自定义注解
 *
 * @author wsl
 * @date 2018/11/26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GetCache {

    // cache 指明的id
    String id() default "";

    // 失效时间
    long expiry() default 3600 * 12;

    /**
     * cache 失效的时间的单位，默认是秒。
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 在缓存失效时，是否以异步方式更新缓存，默认为 true，即异步更新。
     */
    boolean updateAsync() default false;
}
