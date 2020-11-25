package com.atguigu.atcrowdfunding.listener;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class StartSystemListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext application = sce.getServletContext();
        String APP_PATH = application.getContextPath();
        application.setAttribute("APP_PATH", APP_PATH);
        System.out.println(APP_PATH);

        //查找所有许可，放到set集合中
        ApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(application);
        PermissionService permissionService = ioc.getBean(PermissionService.class);
        List<Permission> permissionList = permissionService.queryAllPermission();
        Set<String> url = new HashSet<>();
        for (Permission p : permissionList) {
            if (p.getUrl() != null) {
                url.add("/" + p.getUrl());
            }
        }
        application.setAttribute("url", url);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
