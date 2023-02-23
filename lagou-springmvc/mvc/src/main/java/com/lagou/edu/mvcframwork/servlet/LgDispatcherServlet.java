package com.lagou.edu.mvcframwork.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LgDispatcherServlet extends HttpServlet {
    private Properties properties = new Properties();

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
        doScan(properties.getProperty("scanPackage"));
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
     * 扫描的全限定类名
     */
    private List<String> classNames = new ArrayList<>();

    /**
     * 扫描类
     */
    private void doScan(String scanPackage) {
        final String scanPackagePath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + scanPackage.replaceAll("\\.", "/");
        File pack = new File(scanPackagePath);
        final File[] files = pack.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                doScan(scanPackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                final String className = scanPackage + "." + file.getName().replaceAll(".class", "");
                classNames.add(className);
            }
        }
    }

    /**
     * 加载配置文件
     *
     * @param contextConfigLocation
     */
    private void doLoadConfig(String contextConfigLocation) {
        final InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
