package com.atguigu.atcrowdfunding.interceptor;
import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //每次拦截请求都查询数据库表的所有许可，效率低
        //查找所有许可，放到set集合中
/*        List<Permission> permissionList =  permissionService.queryAllPermission();
        Set<String> url = new HashSet<>();
        for(Permission p:permissionList){
            if(p.getUrl()!=null){
                url.add("/"+p.getUrl());
            }
        }*/
        //获取url集合
        Set<String> url = (Set<String>) request.getSession().getServletContext().getAttribute("url");
        //判断该访问是否在set集合中
        String servletPath = request.getServletPath();
        if(url.contains(servletPath)){
            //判断该用户是否有该访问的权限
            //获取该用户的访问限权集合，判断servletPath是否在里面
            Set<String> roleUrl = (Set<String>) request.getSession().getAttribute("url");
            if(roleUrl.contains(servletPath)){
                return true;
            }else{
                response.sendRedirect(request.getContextPath()+"/login.htm");
                return false;
            }
        }else{
            //如果不在，则放行
            return true;
        }
    }
}
