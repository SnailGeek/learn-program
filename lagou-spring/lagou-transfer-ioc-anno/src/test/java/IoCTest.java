import com.lagou.edu.SpringConfig;
import com.lagou.edu.dao.AccountDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IoCTest {
    @Test
    public void testIoC() {
        // 通过读取classPath下的xml文件来启动容器
        final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        AccountDao accountDao = (AccountDao) applicationContext.getBean("accountDao");
        System.out.println("accountDao" + accountDao);


    }
}
