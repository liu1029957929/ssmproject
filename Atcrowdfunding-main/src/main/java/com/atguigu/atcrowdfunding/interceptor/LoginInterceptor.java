package com.atguigu.atcrowdfunding.interceptor;

import com.atguigu.atcrowdfunding.bean.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //定义哪些路径是可以放行的
        List<String> uri = new ArrayList<>();
        uri.add("/user/reg.do");
        uri.add("/user/reg.htm");
        uri.add("/login.htm");
        uri.add("/dologin.do");
        uri.add("/logout.htm");
        uri.add("/index.htm");

        String servletPath = request.getServletPath();
        if(uri.contains(servletPath)){
            return true;
        }else{
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute("user");

            if(user!=null){
                return true;
            }else{
                response.sendRedirect(request.getContextPath()+"/login.htm");
                return false;
            }
        }
    }
}
