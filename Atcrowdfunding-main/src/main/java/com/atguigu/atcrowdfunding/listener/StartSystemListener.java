package com.atguigu.atcrowdfunding.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartSystemListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
         ServletContext application = sce.getServletContext();
         String APP_PATH = application.getContextPath();
         application.setAttribute("APP_PATH",APP_PATH);
        System.out.println(APP_PATH);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
