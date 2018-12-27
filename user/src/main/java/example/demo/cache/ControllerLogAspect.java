package example.demo.cache;

import com.alibaba.fastjson.JSONObject;
import example.vo.JsonResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 如何采用post方式的application/json请求 无法获得具体的参数
 * <p>
 * Author wsl
 * Date 2018/12/16
 */
@Component
@Aspect
public class ControllerLogAspect {

    @Around("@annotation(controllerLog)")
    public void getControllerLog(ProceedingJoinPoint joinPoint, Log controllerLog) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.
                getRequestAttributes()).getRequest();
        //System.out.println("获取reque的请求参数--" + JSONObject.toJSON(request.getParameterMap()));
        // 如果从paramterMap中获取 可能存在 只获取到了 pagesize 以及 pageindex
        MethodSignature joinPointObject = (MethodSignature) joinPoint.getSignature();
        Method method = joinPointObject.getMethod();
        //Log controllerLog = method.getAnnotation(Log.class);
        String description = method.getAnnotation(Log.class).moduleName() +
                method.getAnnotation(Log.class).moduleType() + request.getParameterMap();
        final String arg = "";
        Object[] args = joinPoint.getArgs();
        System.out.println("args类型" + JSONObject.toJSON(args));
        Arrays.asList(args).forEach(i -> {
            if (i.getClass().getTypeName().contains("Request") ||
                    i.getClass().getTypeName().contains("MultipartFile")) {
                System.out.println("文件类型");
            } else {
                //description += i;
            }
        });
       /* String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        Log controllerLog = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    controllerLog = method.getAnnotation(Log.class);
                    description = controllerLog.moduleName() + controllerLog.moduleType();
                    break;
                }
            }
        }*/
        //System.out.println("获取注解信息--" + description);

        //System.out.println("获取方法的参数" + JSONObject.toJSONString(joinPoint.getArgs()));
        System.out.println("获取文件类型的:" + MultipartFile.class.getTypeName());
        //System.out.println("request的文件类型:"+);'
//        Object[] args = joinPoint.getArgs();
//        Arrays.asList(args).forEach(i -> System.out.println(i.getClass().getTypeName()));
//        Arrays.asList(args).forEach(i -> System.out.println(i.getClass().getName()));
//        Arrays.asList(args).forEach(i -> {
//            if (i.getClass().getTypeName().equals(StandardMultipartHttpServletRequest.class.getTypeName()) ||
//                    i.getClass().getTypeName().contains("MultipartFile")) {
//                System.out.println("文件类型");
//            }
//        });

        JsonResult result = (JsonResult) joinPoint.proceed();

        if (result.getCode() == 200) { // 请求成功了 则记录日志
            description = controllerLog.moduleName() + controllerLog.moduleType();
            //description += JSONObject.toJSONString(joinPoint.getArgs());
            System.out.println("请求成功了:" + description);
        }

    }
}
