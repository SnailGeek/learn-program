import com.snail.autowire.Foo;
import com.snail.autowire.FooConstructor;
import com.snail.beanTag.MockBusinessObject;
import com.snail.beanTag.MockBusinessObjectIndex;
import com.snail.news.FXNewsProvider;
import com.snail.news.SpecificFXNewsProvider;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
}
