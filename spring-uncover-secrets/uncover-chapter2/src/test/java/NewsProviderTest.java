import com.geek.design.news.DowJonesNewsListener;
import com.geek.design.news.DowJonesNewsPersister;
import com.geek.design.news.FXNewsProvider;
import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.*;
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

    @Test
    public void testBeanFactory() {
        final DefaultListableBeanFactory beaRegistry = new DefaultListableBeanFactory();
        final BeanFactory beanFactory = bindViaCode(beaRegistry);
        final FXNewsProvider provider = (FXNewsProvider) beanFactory.getBean("djNewsProvider");
        provider.getAndPersistNews();
    }

    /**
     * 通过编码的方式注入
     */
    private BeanFactory bindViaCode(BeanDefinitionRegistry registry) {
        final AbstractBeanDefinition newsProvider = new RootBeanDefinition();
        //调用这个构造方法，需要有FXNewsProvider需要一个无参构造方法，这种方法后面在通过构造方法注入djNewsListener和djNewsPersister会注入失败
        // final AbstractBeanDefinition newsProvider = new RootBeanDefinition(FXNewsProvider.class, FXNewsProvider::new);
        newsProvider.setBeanClass(FXNewsProvider.class);

        final AbstractBeanDefinition newsListener = new RootBeanDefinition(DowJonesNewsListener.class, DowJonesNewsListener::new);
        final AbstractBeanDefinition newsPersister = new RootBeanDefinition(DowJonesNewsPersister.class, DowJonesNewsPersister::new);
        registry.registerBeanDefinition("djNewsProvider", newsProvider);
        registry.registerBeanDefinition("djNewsListener", newsListener);
        registry.registerBeanDefinition("djNewsPersister", newsPersister);

        // 通过构造的方式注入
        final ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addIndexedArgumentValue(0, newsListener);
        argumentValues.addIndexedArgumentValue(1, newsPersister);
        newsProvider.setConstructorArgumentValues(argumentValues);

        // 或者通过Setter方法注入
        // final MutablePropertyValues propertyValues = new MutablePropertyValues();
        // propertyValues.addPropertyValue(new PropertyValue("newsListener", newsListener));
        // propertyValues.addPropertyValue(new PropertyValue("newsPersister", newsPersister));
        // newsProvider.setPropertyValues(propertyValues);

        return (BeanFactory) registry;
    }

    @Test
    public void testBeanFactoryFromProperties() {
        DefaultListableBeanFactory beanRegitry = new DefaultListableBeanFactory();
        final BeanFactory beanFactory = bindViaProperties(beanRegitry);
        final FXNewsProvider provider = (FXNewsProvider) beanFactory.getBean("djNewsProvider");
        provider.getAndPersistNews();
    }

    private BeanFactory bindViaProperties(BeanDefinitionRegistry registry) {
        final PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(registry);
        reader.loadBeanDefinitions("classpath:beans.properties");
        return (BeanFactory) registry;
    }
}
