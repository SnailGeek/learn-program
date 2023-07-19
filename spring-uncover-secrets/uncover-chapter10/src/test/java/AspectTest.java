import com.snail.advice.ITask;
import com.snail.aspect.Foo;
import com.snail.aspect.PerformanceTraceAspect;
import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AspectTest {
    @Test
    public void testAspect() {
        AspectJProxyFactory weaver = new AspectJProxyFactory();
        weaver.setProxyTargetClass(true);
        weaver.setTarget(new Foo());
        weaver.addAspect(PerformanceTraceAspect.class);
        final Foo foo = (Foo) weaver.getProxy();
        foo.method1();
        foo.method2();
    }

    @Test
    public void testAutoProxy() {
        final ApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
        final Object target = context.getBean("target");
        ((Foo) target).method1();
        ((Foo) target).method2();
    }

    @Test
    public void testMultiAdvice() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
        final ITask task = (ITask) context.getBean("mockTask");
        task.execute("save task");
    }
}
