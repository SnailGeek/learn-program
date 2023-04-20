import com.snail.beanTag.MockBusinessObject;
import com.snail.beanTag.MockBusinessObjectIndex;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanTagTest {
    @Test
    public void testType() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
        final MockBusinessObject mockBO = (MockBusinessObject) context.getBean("mockBO");
        System.out.println(mockBO);
    }

    @Test
    public void testIndex() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans-index.xml");
        final MockBusinessObjectIndex mockBO = (MockBusinessObjectIndex) context.getBean("mockBO");
        System.out.println(mockBO);
    }
}
