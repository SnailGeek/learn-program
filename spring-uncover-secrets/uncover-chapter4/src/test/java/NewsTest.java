import com.snail.news.DowJonesNewsListener;
import com.snail.news.FXNewsProvider;
import com.snail.news.MockNewsPersister;
import com.snail.news.MockNewsPersisterByObjectCreating;
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

    @Test
    public void testMock2() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans-news.xml");
        final MockNewsPersisterByObjectCreating mockPersister = (MockNewsPersisterByObjectCreating) context.getBean("mockPersisterByCreating");
        mockPersister.persistNews();
        mockPersister.persistNews();
    }

    @Test
    public void testReplacer() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans-news.xml");
        final FXNewsProvider provider = (FXNewsProvider) context.getBean("newsProvider");
        provider.getAndPersistNews();
    }

    @Test
    public void testCustomPostProcessor() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans-news.xml");
    }

}
