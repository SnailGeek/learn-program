import com.snail.news.FXNewsProvider;
import com.snail.property.DateFoo;
import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class PropertyTest {
    @Test
    public void testCustomEditor() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans-property.xml");
        final DateFoo dateFoo = (DateFoo) context.getBean("dateFoo");
        final Date date = dateFoo.getDate();
        System.out.println(date);
    }

    @Test
    public void testBeanWrapper() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        final Object provider = Class.forName("com.snail.news.FXNewsProvider").newInstance();
        final Object listener = Class.forName("com.snail.news.DowJonesNewsListener").newInstance();
        final Object persister = Class.forName("com.snail.news.DowJonesNewsPersister").newInstance();

        final BeanWrapper newsProvider = new BeanWrapperImpl(provider);
        newsProvider.setPropertyValue("djNewsListener", listener);
        newsProvider.setPropertyValue("djNewsPersister", persister);

        assertTrue(newsProvider.getWrappedInstance() instanceof FXNewsProvider);
        assertSame(provider,newsProvider.getWrappedInstance());
        assertSame(listener,newsProvider.getPropertyValue("djNewsListener"));
        assertSame(persister,newsProvider.getPropertyValue("djNewsPersister"));
    }
}
