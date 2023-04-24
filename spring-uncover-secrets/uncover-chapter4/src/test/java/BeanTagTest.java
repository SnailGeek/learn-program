import com.snail.autowire.Foo;
import com.snail.autowire.FooConstructor;
import com.snail.beanTag.MockBusinessObject;
import com.snail.beanTag.MockBusinessObjectIndex;
import com.snail.news.DowJonesNewsListener;
import com.snail.news.FXNewsProvider;
import com.snail.news.SpecificFXNewsProvider;
import com.snail.scope.ThreadScope;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

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

    @Test
    public void testAutowired() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans-index.xml");
        final Foo fooBean = (Foo) context.getBean("fooBean");
        fooBean.getEmphasisAttribute();
    }

    @Test
    public void testAutowiredConstructor() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans-index.xml");
        final FooConstructor fooBean = (FooConstructor) context.getBean("fooConstructorBean");
        fooBean.printBar();
    }

    @Test
    public void testParent() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
        final FXNewsProvider parentProvider = (FXNewsProvider) context.getBean("newsProvider");
        parentProvider.printPropertyBean();
        final SpecificFXNewsProvider provider = (SpecificFXNewsProvider) context.getBean("specificNewsProvider");
        provider.printPropertyBean();
    }

    @Test
    public void testParentAbstract() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans-parent-abstract.xml");
        final FXNewsProvider parentProvider = (FXNewsProvider) context.getBean("newsProvider");
        parentProvider.printPropertyBean();
        final SpecificFXNewsProvider provider = (SpecificFXNewsProvider) context.getBean("specificNewsProvider");
        provider.printPropertyBean();
    }

    @Test
    public void testCustomScope() throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext() {
            @Override
            protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
                ThreadScope threadScope = new ThreadScope();
                beanFactory.registerScope("thread", threadScope);
                super.postProcessBeanFactory(beanFactory);
            }
        };
        context.setConfigLocation("classpath:beans-scope.xml");
        context.refresh();

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                final DowJonesNewsListener listener = (DowJonesNewsListener) context.getBean("djNewsListener");
                System.out.println("thread-2-listener: " + listener);
            }).start();
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Test
    public void testCustomScope2() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions("classpath:beans-scope.xml");
        ThreadScope threadScope = new ThreadScope();
        factory.registerScope("thread", threadScope);

        final DowJonesNewsListener djNewsListener = (DowJonesNewsListener) factory.getBean("djNewsListener");
        System.out.println("thread-1-djNewsListener: " + djNewsListener);
        final DowJonesNewsListener djNewsListener2 = (DowJonesNewsListener) factory.getBean("djNewsListener");
        System.out.println("thread-1-djNewsListener2: " + djNewsListener2);
        new Thread(() -> {
            final DowJonesNewsListener djNewsListener3 = (DowJonesNewsListener) factory.getBean("djNewsListener");
            System.out.println("thread-2-djNewsListener3: " + djNewsListener3);
        }).start();
    }
}
