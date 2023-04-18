import com.geek.design.news.FXNewsProvider;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class NewsProviderTest {

    @Test
    public void testClassPath() {
        final ApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
        // ClassPathXmlApplicationContext默认就是classpath路径下面，因此可以不用写classpath:，如果使用绝对路径需要加上file:
        // final ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        // 如果使用绝对路径需要加上file:
        // final ApplicationContext context = new ClassPathXmlApplicationContext("file:/Users/zero/JavaProgram/learn-program/spring-uncover-secrets/uncover-chapter2/src/main/resources/beans.xml");
        final FXNewsProvider djNewsProvider = (FXNewsProvider) context.getBean("djNewsProvider");
        djNewsProvider.getAndPersistNews();
    }

    @Test
    public void testSystemPath() {
        final ApplicationContext context = new FileSystemXmlApplicationContext("classpath:beans.xml");
        // final ApplicationContext context = new FileSystemXmlApplicationContext("file:/Users/zero/JavaProgram/learn-program/spring-uncover-secrets/uncover-chapter2/src/main/resources/beans.xml");
        // final ApplicationContext context = new FileSystemXmlApplicationContext("/src/main/resources/beans.xml");
        final FXNewsProvider djNewsProvider = (FXNewsProvider) context.getBean("djNewsProvider");
        djNewsProvider.getAndPersistNews();
    }
}
