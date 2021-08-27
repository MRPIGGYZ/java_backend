package com.example.backend.JwtUtils;

import com.example.backend.User.MainController;
import com.example.backend.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

public class JwtAuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    MainController accountService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        else {
            System.out.println("需要验证用户token");
            if (token == null) {
                return false;
            }
            String userId = JwtUtils.getAudience(token);
            List<User> users = accountService.getUserList().getUserById(Integer.parseInt(userId));
            if (users.isEmpty()) return false;
            if(!JwtUtils.verifyToken(token, users.get(0).getPassword())){
                return false;
            }
            String userName = JwtUtils.getClaimByName(token, "userName").asString();
            httpServletRequest.setAttribute("id", userId);
            httpServletRequest.setAttribute("userName", userName);

            return true;

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
