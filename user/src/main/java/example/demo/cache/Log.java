package example.demo.cache;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author wsl
 * Date 2018/12/16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {

    String moduleName() default "nicai" ; // 日志名称

    int moduleType() default 0; //日志类型

}
