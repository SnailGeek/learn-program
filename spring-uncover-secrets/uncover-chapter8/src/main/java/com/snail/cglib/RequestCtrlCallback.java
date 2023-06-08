package com.snail.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.joda.time.TimeOfDay;

import java.lang.reflect.Method;

public class RequestCtrlCallback implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (method.getName().equals("request")) {
            TimeOfDay startTime = new TimeOfDay(0, 0, 0);
            TimeOfDay endTime = new TimeOfDay(5, 59, 59);
            final TimeOfDay currentTime = new TimeOfDay();
            if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
                return null;
            }
            System.out.println("proxy process");
            return methodProxy.invokeSuper(o, objects);
        }
        return null;
    }
}
