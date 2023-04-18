import com.geek.design.springDemo.FXNewsProvider;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class NewsProviderTest {

    @Test
    public void test() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
        final FXNewsProvider djNewsProvider = (FXNewsProvider) context.getBean("djNewsProvider");
        djNewsProvider.getAndPersistNews();
    }

}
