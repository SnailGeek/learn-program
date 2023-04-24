import com.snail.factory.Foo;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
}
