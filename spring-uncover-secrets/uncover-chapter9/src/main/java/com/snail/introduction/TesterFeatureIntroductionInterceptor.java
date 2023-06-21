package com.snail.introduction;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

public class TesterFeatureIntroductionInterceptor extends DelegatingIntroductionInterceptor implements ITester {
    private static final long serialVersionUID = -1111111111111L;
    private boolean busyAsTester;

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        if (isBusyAsTester() && mi.getMethod().getName().contains("developSoftware")) {
            throw new RuntimeException("你想累死我吗？");
        }
        return super.invoke(mi);
    }

    @Override
    public boolean isBusyAsTester() {
        return busyAsTester;
    }

    @Override
    public void testSoftware() {
        System.out.println("I will ensuer the qaulity");
    }

    public void setBusyAsTester(boolean busyAsTester) {
        this.busyAsTester = busyAsTester;
    }
}
