import com.snail.targetSource.AlternativeTargetSource;
import com.snail.weave.ICounter;
import com.snail.weave.ITask;
import com.snail.weave.TaskExecutionContext;
import org.junit.Test;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.target.HotSwappableTargetSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

public class ProxyFactoryBeanTest {
    @Test
    public void testIntroduction() {
        final ApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
        final Object introductionInterceptor = context.getBean("introductionInterceptor");
        final Object proxy1 = context.getBean("introducedTask");
        final Object proxy2 = context.getBean("introducedTask");
        System.out.println(((ICounter) proxy1).getCounter());
        System.out.println(((ICounter) proxy1).getCounter());
        System.out.println(((ICounter) proxy2).getCounter());
    }

    @Test
    public void testSwapTargetSource() throws Exception {
        final ApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
        final Object proxy = context.getBean("taskProxy");
        final Object initTarget = ((Advised) proxy).getTargetSource().getTarget();
        final HotSwappableTargetSource hotSwapTargetSource = (HotSwappableTargetSource) context.getBean("hotSwapTargetSource");
        final Object oldTarget = hotSwapTargetSource.swap(new ITask() {
            @Override
            public void excute(TaskExecutionContext context) {
                System.out.println("new task");
            }
        });
        final Object newTarget = ((Advised) proxy).getTargetSource().getTarget();
        assertSame(initTarget, oldTarget);
        ((ITask) oldTarget).excute(null);
        assertNotSame(initTarget, newTarget);
        ((ITask) newTarget).excute(null);

    }

    @Test
    public void testCustomTargetSource() {
        ITask task1 = new ITask() {
            @Override
            public void excute(TaskExecutionContext context) {
                System.out.println("execute in task1");
            }
        };

        ITask task2 = new ITask() {
            @Override
            public void excute(TaskExecutionContext context) {
                System.out.println("execute int task2");
            }
        };

        final ProxyFactory proxy = new ProxyFactory();
        proxy.setTargetSource(new AlternativeTargetSource(task1, task2));
        final ITask obj = (ITask) proxy.getProxy();
        obj.excute(null);
        obj.excute(null);
        obj.excute(null);
        obj.excute(null);
        obj.excute(null);

    }
}
