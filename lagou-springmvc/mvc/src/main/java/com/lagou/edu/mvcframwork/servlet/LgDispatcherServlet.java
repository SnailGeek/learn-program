package com.lagou.edu.mvcframwork.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LgDispatcherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 加载配置文件 springmvc.properties
        final String contextConfigLocation = config.getInitParameter("contextConfigLocation");
        doLoadConfig(contextConfigLocation);
        // 扫描相关的类，扫描注解
        doScan("");
        // bean的初始化（实现IOC容器，基于注解）
        doInstance();

        // 实现依赖注入
        doAutowried();

        // 构造一个HandlerMapping处理器映射器，将配置好的url和Handler建立映射关系
        initHandlerMapping();

        // 等待请求进入，处理请求

        System.out.println("lg mvc init completed........");

        super.init(config);
    }

    private void initHandlerMapping() {
    }

    private void doAutowried() {
    }

    /**
     * IOC容器
     */
    private void doInstance() {

    }

    /**
     * 扫描类
     */
    private void doScan(String scanPackage) {

    }

    /**
     * 加载配置文件
     *
     * @param contextConfigLocation
     */
    private void doLoadConfig(String contextConfigLocation) {

    }
}
