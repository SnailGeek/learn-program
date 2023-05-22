import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
}
