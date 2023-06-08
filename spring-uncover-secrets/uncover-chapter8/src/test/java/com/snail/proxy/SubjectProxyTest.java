package com.snail.proxy;

import com.snail.cglib.RequestCtrlCallback;
import com.snail.cglib.Requestable;
import junit.framework.TestCase;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

import java.lang.reflect.Proxy;

public class SubjectProxyTest extends TestCase {

    @Test
    public void testRequest() {
        ISubject subject = new SubjectImpl();
        final ISubject subjectProxy = new ServiceControlSubjectProxy(subject);
        subjectProxy.request();
    }

    @Test
    public void testDynamicRequest() {
        final ISubject subject = (ISubject) Proxy.newProxyInstance(ISubject.class.getClassLoader(),
                new Class[]{ISubject.class},
                new RequestCtrlInvocationHandler(new SubjectImpl()));
        subject.request();
        final IRequestable requestable = (IRequestable) Proxy.newProxyInstance(IRequestable.class.getClassLoader(),
                new Class[]{IRequestable.class},
                new RequestCtrlInvocationHandler(new RequestableImpl()));
        requestable.request();
    }

    @Test
    public void testDynamicRequest2() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Requestable.class);
        enhancer.setCallback(new RequestCtrlCallback());

        final Requestable requestable = (Requestable) enhancer.create();
        requestable.request();
    }
}