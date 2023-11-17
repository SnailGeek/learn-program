import com.snail.AutoDeliverApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest(classes = AutoDeliverApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EurekaMetaMapTest {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Test
    public void testMetadataMap() {
        List<ServiceInstance> instances = discoveryClient.getInstances("learn-cloud-resume");
        ServiceInstance serviceInstance = instances.get(0);
        System.out.println(serviceInstance);
    }
}
