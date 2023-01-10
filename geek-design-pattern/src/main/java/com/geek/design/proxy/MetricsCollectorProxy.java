package com.geek.design.proxy;

import design.metrics.original.MetricsCollector;
import design.metrics.original.RedisMetricsStorage;
import design.metrics.original.RequestInfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @program: UserControllerDynamic
 * @description:
 * @author: wangf-q
 * @date: 2023-01-09 14:27
 **/
public class MetricsCollectorProxy {
    private MetricsCollector metricsCollector;

    public MetricsCollectorProxy() {
        this.metricsCollector = new MetricsCollector(new RedisMetricsStorage());
    }

    public Object createProxy(Object proxiedObject) {
        final Class<?>[] interfaces = proxiedObject.getClass().getInterfaces();
        final DynamicProxyHandler handler = new DynamicProxyHandler(proxiedObject);
        return Proxy.newProxyInstance(proxiedObject.getClass().getClassLoader(), interfaces, handler);
    }

    private class DynamicProxyHandler implements InvocationHandler {

        private Object proxiedObject;

        public DynamicProxyHandler(Object proxiedObject) {
            this.proxiedObject = proxiedObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            long startTimeStamp = System.currentTimeMillis();
            final Object result = method.invoke(proxiedObject, args);
            long endTimeStamp = System.currentTimeMillis();
            long responseTime = endTimeStamp - startTimeStamp;
            final String apiName = proxiedObject.getClass().getName() + ":" + method.getName();
            final RequestInfo requestInfo = new RequestInfo(apiName, responseTime, startTimeStamp);
            metricsCollector.recordRequest(requestInfo);
            return result;
        }
    }

    public static void main(String[] args) {
        //使用
        MetricsCollectorProxy proxy = new MetricsCollectorProxy();
        final IUserController userController = (IUserController) proxy.createProxy(new UserController());
    }
}
