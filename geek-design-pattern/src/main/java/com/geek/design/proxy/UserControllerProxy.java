package com.geek.design.proxy;

import design.metrics.original.MetricsCollector;
import design.metrics.original.RequestInfo;

/**
 * @program: UserProxyController
 * @description: 共同实现接口的方式，实现代理模式
 * @author: wangf-q
 * @date: 2023-01-09 14:13
 **/
public class UserControllerProxy implements IUserController {

    private UserController userController;

    private MetricsCollector metricsCollector;

    public UserControllerProxy(UserController userController, MetricsCollector metricsCollector) {
        this.userController = userController;
        this.metricsCollector = metricsCollector;
    }

    @Override
    public UserVo login(String telephone, String password) {
        long startTimeStamp = System.currentTimeMillis();

        final UserVo userVo = userController.login(telephone, password);

        final long endTimeStamp = System.currentTimeMillis();
        long responseTime = endTimeStamp - startTimeStamp;

        final RequestInfo requestInfo = new RequestInfo("login", responseTime, startTimeStamp);
        metricsCollector.recordRequest(requestInfo);

        // 返回UserVo数据
        return userVo;
    }

    @Override
    public UserVo register(String telephone, String password) {
        long startTimeStamp = System.currentTimeMillis();

        // register 相关逻辑。。。。。。。。。
        final UserVo userVo = userController.register(telephone, password);

        final long endTimeStamp = System.currentTimeMillis();
        long responseTime = endTimeStamp - startTimeStamp;

        final RequestInfo requestInfo = new RequestInfo("register", responseTime, startTimeStamp);
        metricsCollector.recordRequest(requestInfo);

        // 返回UserVo数据
        return userVo;
    }
}
