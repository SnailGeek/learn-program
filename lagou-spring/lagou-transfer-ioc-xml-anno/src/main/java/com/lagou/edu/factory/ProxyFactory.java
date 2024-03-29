package com.lagou.edu.factory;

import com.lagou.edu.pojo.Account;
import com.lagou.edu.utils.TransactionManager;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component("proxyFactory")
public class ProxyFactory {
    @Autowired
    private TransactionManager transactionManager;

    public Object getJDKProxy(Object obj) {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),
                (o, method, objects) -> {
                    Object result = null;
                    try {
                        // 开启事物
                        transactionManager.beginTransaction();
                        result = method.invoke(obj, objects);

                        // 提交事务
                        transactionManager.commit();
                    } catch (Exception e) {
                        // 回滚事务
                        transactionManager.rollback();
                        e.printStackTrace();
                        // 抛出异常便于Servlet异常捕获
                        throw e;
                    }
                    return result;
                });
    }
}
