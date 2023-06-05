import com.snail.event.spring.MethodExecutionEventPublisher;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EventTest {
    @Test
    public void testPublishEvent() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:event-beans.xml");
        final MethodExecutionEventPublisher publisher = (MethodExecutionEventPublisher) context.getBean("evtPublisher");
        publisher.methodMonitor();
    }
}
