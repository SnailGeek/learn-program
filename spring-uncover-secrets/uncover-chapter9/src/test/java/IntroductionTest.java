import com.snail.introduction.*;
import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.support.DelegatePerTargetObjectIntroductionInterceptor;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

public class IntroductionTest {
    @Test
    public void testDelegating() {
        IDeveloper developer = new Developer();
        ITester delegate = new Tester();
        DelegatingIntroductionInterceptor interceptor = new DelegatingIntroductionInterceptor(delegate);
        AspectJProxyFactory factory = new AspectJProxyFactory(developer);
        factory.addAdvice(interceptor);
        ITester tester = factory.getProxy();
        tester.testSoftware();
        IDeveloper developer1 = factory.getProxy();
        developer1.developSoftware();
    }

    @Test
    public void testCustomInterceptor() {
        IDeveloper developer = new Developer();
        TesterFeatureIntroductionInterceptor interceptor = new TesterFeatureIntroductionInterceptor();
        AspectJProxyFactory factory = new AspectJProxyFactory(developer);
        factory.addAdvice(interceptor);
        interceptor.setBusyAsTester(true);
        IDeveloper developer2 = factory.getProxy();
        developer2.developSoftware();
    }
}
