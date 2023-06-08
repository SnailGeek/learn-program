package com.snail.proxy;

import com.snail.cglib.RequestCtrlCallback;
import com.snail.cglib.Requestable;
import com.snail.control.TargetCaller;
import com.snail.control.TargetObject;
import junit.framework.TestCase;
import net.sf.cglib.proxy.Enhancer;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.junit.Test;
import org.springframework.aop.aspectj.AspectJWeaverMessageHandler;
import org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import org.springframework.aop.support.ControlFlowPointcut;

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

    @Test
    public void testControlFlow() {
        final ControlFlowPointcut controlFlowPointcut = new ControlFlowPointcut(TargetCaller.class);
//        Advice advice = ;
        final TargetObject target = new TargetObject();
        final AspectJAwareAdvisorAutoProxyCreator creator = new AspectJAwareAdvisorAutoProxyCreator();
        


    }
}