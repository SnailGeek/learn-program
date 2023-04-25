import com.snail.news.MockNewsPersister;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class NewsTest {
    @Test
    public void test() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans-news.xml");
        final MockNewsPersister mockPersister = (MockNewsPersister) context.getBean("mockPersister");
        mockPersister.persistNews();
        mockPersister.persistNews();
    }
}
