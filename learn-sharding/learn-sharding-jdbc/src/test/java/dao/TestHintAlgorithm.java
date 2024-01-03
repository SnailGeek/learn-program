package dao;

import com.snail.learn.RunBoot;
import com.snail.learn.entity.City;
import com.snail.learn.repository.CitylRepository;
import org.apache.shardingsphere.api.hint.HintManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunBoot.class)
public class TestHintAlgorithm {
    @Resource
    private CitylRepository citylRepository;

    @Test
    public void test1() {
        HintManager hintManager = HintManager.getInstance();
        hintManager.setDatabaseShardingValue(1L); // 强制路由到ds${xx%2}
        List<City> list = citylRepository.findAll();
        list.forEach(city -> System.out.println(city.getId() + " " + city.getName() + " " + city.getProvince()));
    }

    @Test
    public void testFind() {
        List<City> list = citylRepository.findAll();
        list.forEach(city -> System.out.println(city.getId() + " " + city.getName() + " " + city.getProvince()));
    }
}
