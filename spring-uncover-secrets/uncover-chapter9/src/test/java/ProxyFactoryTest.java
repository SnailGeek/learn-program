import com.snail.advice.PerformanceMethodInterceptor;
import com.snail.introduction.Developer;
import com.snail.introduction.IDeveloper;
import com.snail.introduction.ITester;
import com.snail.introduction.TesterFeatureIntroductionInterceptor;
import com.snail.weave.Executable;
import com.snail.weave.ITask;
import com.snail.weave.MockTask;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultIntroductionAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

public class ProxyFactoryTest {
    @Test
    public void testInterfaceProxy() {
        MockTask task = new MockTask();
        ProxyFactory weaver = new ProxyFactory(task);
        // 如果没有其他行为属性的干预，可以不用设置接口
//        weaver.setInterfaces(new Class[]{ITask.class});
        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
        advisor.setMappedName("execute");
        advisor.setAdvice(new PerformanceMethodInterceptor());
        // 设置基于类的生成代理
//        weaver.setOptimize(true);
        weaver.setProxyTargetClass(true);
        weaver.addAdvisor(advisor);
        final ITask proxy = (ITask) weaver.getProxy();
        proxy.excute(null);
        System.out.println(proxy.getClass());

    }

    @Test
    public void testClass() {
        Executable executable = new Executable();
        ProxyFactory weaver = new ProxyFactory(executable);
        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
        advisor.setMappedName("execute");
        advisor.setAdvice(new PerformanceMethodInterceptor());
        weaver.addAdvisor(advisor);
        final Executable proxy = (Executable) weaver.getProxy();
        proxy.execute();
        System.out.println(proxy.getClass());
    }

    @Test
    public void testIntroduction() {
        ProxyFactory weaver = new ProxyFactory(new Developer());
        weaver.setInterfaces(new Class[]{IDeveloper.class, ITester.class});
        TesterFeatureIntroductionInterceptor advice = new TesterFeatureIntroductionInterceptor();
//        weaver.addAdvice(advice);

        DefaultIntroductionAdvisor advisor = new DefaultIntroductionAdvisor(advice, advice);
        weaver.addAdvisor(advisor);

        final Object proxy = weaver.getProxy();
        final ITester tester = (ITester) proxy;
        tester.testSoftware();

        final IDeveloper developer = (IDeveloper) proxy;
        developer.developSoftware();
    }

    @Test
    public void testIntroductionBaseClass() {
        ProxyFactory weaver = new ProxyFactory(new Developer());
        // 强制使用基于类代理
        weaver.setProxyTargetClass(true);
        weaver.setInterfaces(new Class[]{ITester.class});
        TesterFeatureIntroductionInterceptor advice = new TesterFeatureIntroductionInterceptor();
        weaver.addAdvice(advice);


        final Object proxy = weaver.getProxy();
        ((ITester) proxy).testSoftware();
        //因为基于类代理，因此可以使用Developer类来强转
        ((Developer) proxy).developSoftware();
    }
}
