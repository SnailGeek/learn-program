import com.snail.property.DateFoo;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class PropertyTest {
    @Test
    public void testCustomEditor() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans-property.xml");
        final DateFoo dateFoo = (DateFoo) context.getBean("dateFoo");
        final Date date = dateFoo.getDate();
        System.out.println(date);

    }
}
