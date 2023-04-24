import com.snail.factory.Foo;
import com.snail.factory.NextDayDateDisplayer;
import com.snail.factory.NextDayDateFactoryBean;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

import static org.junit.Assert.assertTrue;

public class FactoryTest {
    @Test
    public void testStaticFactory() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans-factory.xml");
        final Foo foo = (Foo) context.getBean("foo");
        foo.printInstance();
    }

    @Test
    public void testNonStaticFactory() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans-factory.xml");
        final Foo foo = (Foo) context.getBean("foo");
        foo.printInstance();
    }

    @Test
    public void testFactoryBean() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans-factory.xml");
        final NextDayDateDisplayer nextDayDateDisplayer = (NextDayDateDisplayer) context.getBean("nextDayDateDisplayer");
        nextDayDateDisplayer.printDay();

        final Object nextDayDate = context.getBean("nextDayDate");
        assertTrue(nextDayDate instanceof Date);

        final Object factoryBean = context.getBean("&nextDayDate");
        assertTrue(factoryBean instanceof NextDayDateFactoryBean);

        final Object object = ((NextDayDateFactoryBean) factoryBean).getObject();
        assertTrue(object instanceof Date);
    }

}
