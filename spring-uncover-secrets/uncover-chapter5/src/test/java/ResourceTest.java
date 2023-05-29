import com.snail.resourceLoader.Validator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;

import java.util.Locale;

import static org.junit.Assert.*;

public class ResourceTest {
    @Test
    public void testApplicationResourceLoader() {
        final ResourceLoader resourceLoader = new ClassPathXmlApplicationContext();
//        final ResourceLoader resourceLoader = new FileSystemXmlApplicationContext();

        final Resource fileResource = resourceLoader.getResource("/Users/zero/tmp/README");
        assertTrue(fileResource instanceof ClassPathResource);
        assertFalse(fileResource.exists());

        final Resource urlResource = resourceLoader.getResource("http://spring21.cn");
        assertTrue(urlResource instanceof UrlResource);
    }

    @Test
    public void testMessageSource() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        final String fileMenuName = context.getMessage("menu.file", new Object[]{"F"}, Locale.US);
        final String editMenuName = context.getMessage("menu.edit", null, Locale.US);
        assertEquals("File(F)", fileMenuName);
        assertEquals("Edit", editMenuName);
    }

    @Test
    public void testValidate() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        final Validator validator = (Validator) context.getBean("validator");
        validator.validate();

    }
}
