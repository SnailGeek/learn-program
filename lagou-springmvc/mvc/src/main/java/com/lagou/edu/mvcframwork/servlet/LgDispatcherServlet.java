package com.lagou.edu.mvcframwork.servlet;

import com.lagou.edu.mvcframwork.annotations.LgController;
import com.lagou.edu.mvcframwork.annotations.LgService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.*;

public class LgDispatcherServlet extends HttpServlet {
    private Properties properties = new Properties();

    /**
     * 扫描的全限定类名
     */
    private List<String> classNames = new ArrayList<>();

    private Map<String, Object> iocMap = new HashMap<>();

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
     * 基于classNames缓存的类的全限定类名，以及反射技术,完成对象创建和管理
     */
    private void doInstance() {
        if (classNames.size() == 0) {
            return;
        }
        try {
            for (String className : classNames) {
                final Class<?> aClass = Class.forName(className);
                if (aClass.isAnnotationPresent(LgController.class)) {
                    // controller的ID不做过多处理，取类名首字母小写
                    final String simpleName = aClass.getSimpleName();
                    final String lowerFirstSimpleName = lowerFirst(simpleName);
                    final Object o = aClass.newInstance();
                    iocMap.put(lowerFirstSimpleName, o);
                } else if (aClass.isAnnotationPresent(LgService.class)) {
                    final LgService annotation = aClass.getAnnotation(LgService.class);
                    String beanName = annotation.value();
                    if (!"".equals(beanName.trim())) {
                        iocMap.put(beanName, aClass.newInstance());
                    } else {
                        beanName = lowerFirst(aClass.getSimpleName());
                        iocMap.put(beanName, aClass.getNestHost());
                    }

                    final Class<?>[] interfaces = aClass.getInterfaces();
                    for (Class<?> anInterface : interfaces) {
                        iocMap.put(anInterface.getName(), aClass.newInstance());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String lowerFirst(String str) {
        final char[] chars = str.toCharArray();
        if ('A' <= chars[0] && chars[0] <= 'Z') {
            chars[0] += 32;
        }
        return String.valueOf(chars);
    }


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
