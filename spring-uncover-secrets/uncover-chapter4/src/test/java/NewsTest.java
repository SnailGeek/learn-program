import com.snail.news.DowJonesNewsListener;
import com.snail.news.FXNewsProvider;
import com.snail.news.MockNewsPersister;
import com.snail.news.MockNewsPersisterByObjectCreating;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

import static org.junit.Assert.*;

public class NewsTest {
    @Test
    public void test() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans-news.xml");
        final MockNewsPersister mockPersister = (MockNewsPersister) context.getBean("mockPersister");
        mockPersister.persistNews();
        mockPersister.persistNews();
    }

    @Test
    public void testMock2() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans-news.xml");
        final MockNewsPersisterByObjectCreating mockPersister = (MockNewsPersisterByObjectCreating) context.getBean("mockPersisterByCreating");
        mockPersister.persistNews();
        mockPersister.persistNews();
    }

    @Test
    public void testReplacer() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans-news.xml");
        final FXNewsProvider provider = (FXNewsProvider) context.getBean("newsProvider");
        provider.getAndPersistNews();
    }

    @Test
    public void testCustomPostProcessor() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans-news.xml");
    }

    @Test
    public void testDefaultResourceLoader() {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        final Resource fakeFileResource = resourceLoader.getResource("/Users/zero/tmp/README");
        assertTrue(fakeFileResource instanceof ClassPathResource);
        assertFalse(fakeFileResource.exists());

        final Resource urlResource1 = resourceLoader.getResource("file:/Users/zero/tmp/README");
        assertTrue(urlResource1 instanceof UrlResource);

        final Resource urlResource2 = resourceLoader.getResource("http://www.spring21.cn");
        assertTrue(urlResource2 instanceof UrlResource);

        try {
            fakeFileResource.getFile();
            fail("no such file with path[" + fakeFileResource.getFilename() + "] exists in classpath");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            urlResource1.getFile();
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testFileSystemResourceLoader() {
        final ResourceLoader resourceLoader = new FileSystemResourceLoader();
        final Resource fileResource = resourceLoader.getResource("/Users/zero/tmp/README");
        assertTrue(fileResource instanceof FileSystemResource);
        assertTrue(fileResource.exists());

        final Resource urlResource = resourceLoader.getResource("file:/Users/zero/tmp/README");
        assertTrue(urlResource instanceof UrlResource);
    }

    @Test
    public void testResourceTypesWithPathMatchingResourcePatternResolver() {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        Resource fileResource = resourceResolver.getResource("/Users/zero/tmp/README");
        assertTrue(fileResource instanceof ClassPathResource);
        assertFalse(fileResource.exists());

        resourceResolver = new PathMatchingResourcePatternResolver(new FileSystemResourceLoader());
        fileResource = resourceResolver.getResource("/Users/zero/tmp/README");
        assertTrue(fileResource instanceof FileSystemResource);
        assertTrue(fileResource.exists());
    }
}
