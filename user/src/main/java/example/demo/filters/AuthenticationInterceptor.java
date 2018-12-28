package example.demo.filters;

import example.demo.cache.LoginToken;
import example.demo.util.JwtUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Collections;

/**
 * jwt认证
 *
 * @author wsl
 * @date 2018/12/28
 */
public class AuthenticationInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 用户带过来的token 也可以放到cookie里
        String token = request.getParameter("token");
        /*if(Collections.){

        }*/
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 取得方法中是否含有注解 没有注解代表
        LoginToken loginToken = method.getAnnotation(LoginToken.class);
        if (loginToken == null) {
            return true;
        }
        JwtUtil.parseJWT(token);
        return true;

    }
}
