package com.geek.design.proxy;

import design.metrics.original.MetricsCollector;
import design.metrics.original.RedisMetricsStorage;
import design.metrics.original.RequestInfo;

/**
 * @program: UserControllerProxyInherit
 * @description:
 * @author: wangf-q
 * @date: 2023-01-09 14:17
 **/
public class UserControllerProxyInherit extends UserController {

    private MetricsCollector metricsCollector;

    public UserControllerProxyInherit() {
        this.metricsCollector = new MetricsCollector(new RedisMetricsStorage());
    }

    @Override
    public UserVo login(String telephone, String password) {
        long startTimeStamp = System.currentTimeMillis();

        final UserVo userVo = super.login(telephone, password);

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
        final UserVo userVo = super.register(telephone, password);

        final long endTimeStamp = System.currentTimeMillis();
        long responseTime = endTimeStamp - startTimeStamp;

        final RequestInfo requestInfo = new RequestInfo("register", responseTime, startTimeStamp);
        metricsCollector.recordRequest(requestInfo);

        // 返回UserVo数据
        return userVo;
    }
}
