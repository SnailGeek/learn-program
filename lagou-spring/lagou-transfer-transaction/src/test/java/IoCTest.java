import com.lagou.edu.dao.AccountDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IoCTest {
    @Test
    public void testIoC() {
        // 通过读取classPath下的xml文件来启动容器
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        AccountDao accountDao = (AccountDao) applicationContext.getBean("accountDao");
        System.out.println("accountDao" + accountDao);

        AccountDao accountDao1 = (AccountDao) applicationContext.getBean("accountDao");
        System.out.println("accountDao1" + accountDao1);

        final Object connectionUtils = applicationContext.getBean("connectionUtils");
        System.out.println(connectionUtils);

        applicationContext.close();
    }
}
