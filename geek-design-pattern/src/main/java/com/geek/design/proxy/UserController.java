package com.geek.design.proxy;

import design.metrics.original.MetricsCollector;
import design.metrics.original.RequestInfo;

/**
 * @program: UserController
 * @description:
 * @author: wangf-q
 * @date: 2023-01-09 14:05
 **/
public class UserController implements IUserController {
    private MetricsCollector metricsCollector;

    @Override
    public UserVo login(String telephone, String password) {
        long startTimeStamp = System.currentTimeMillis();

        // login 相关逻辑。。。。。。。。。

        final long endTimeStamp = System.currentTimeMillis();
        long responseTime = endTimeStamp - startTimeStamp;

        final RequestInfo requestInfo = new RequestInfo("login", responseTime, startTimeStamp);
        metricsCollector.recordRequest(requestInfo);

        // 返回UserVo数据
        return null;
    }

    @Override
    public UserVo register(String telephone, String password) {
        long startTimeStamp = System.currentTimeMillis();

        // register 相关逻辑。。。。。。。。。

        final long endTimeStamp = System.currentTimeMillis();
        long responseTime = endTimeStamp - startTimeStamp;

        final RequestInfo requestInfo = new RequestInfo("register", responseTime, startTimeStamp);
        metricsCollector.recordRequest(requestInfo);

        // 返回UserVo数据
        return null;
    }
}
