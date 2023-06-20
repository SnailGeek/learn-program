import com.snail.introduction.Developer;
import com.snail.introduction.IDeveloper;
import com.snail.introduction.ITester;
import com.snail.introduction.Tester;
import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

public class IntroductionTest {
    @Test
    public void testDelegating() {
        IDeveloper developer = new Developer();
        ITester delegate = new Tester();
        DelegatingIntroductionInterceptor interceptor = new DelegatingIntroductionInterceptor(delegate);
        AspectJProxyFactory factory = new AspectJProxyFactory(developer);
        factory.addAdvice(interceptor);
//        ITester tester = (ITester) weaver.weave(developer).with(interceptor).getProxy();
        ITester tester = factory.getProxy();
        tester.testSoftware();
        IDeveloper developer1 = factory.getProxy();
        developer1.developSoftware();
//        tester.testSoftware();
    }
}
