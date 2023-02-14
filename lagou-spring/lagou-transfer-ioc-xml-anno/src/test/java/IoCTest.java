import com.lagou.edu.dao.AccountDao;
import com.lagou.edu.service.TransferService;
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

    @Test
    public void testLazyInit() {
        // 启动容器（容器初始化）
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        // getBean获取bean对象使用
        final Object lazyResult = applicationContext.getBean("lazyResult");
//        System.out.println(lazyResult);
        applicationContext.close();
    }

    @Test
    public void testFactoryBean() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        final Object companyFactoryBean = applicationContext.getBean("&companyFactoryBean");
        System.out.println(companyFactoryBean);
    }

    @Test
    public void testAOPXml() throws Exception {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        final TransferService tra = context.getBean(TransferService.class);
        tra.transfer("6029621011000", "6029621011001", 2);
    }

    @Test
    public void testAOPAnno() throws Exception {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        final TransferService tra = context.getBean(TransferService.class);
        tra.transfer("6029621011000", "6029621011001", 2);
    }
}
