package dao;

import com.snail.learn.RunBoot;
import com.snail.learn.entity.City;
import com.snail.learn.repository.CitylRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunBoot.class)
public class TestMasterSlave {
    @Resource
    private CitylRepository citylRepository;

    @Test
    public void testAdd() {
        City city = new City();
        city.setName("shanghai");
        city.setProvince("shanghai");
        citylRepository.save(city);
    }

    @Test
    public void testFind() {
        List<City> list = citylRepository.findAll();
        list.forEach(city -> System.out.println(city.getId() + " " + city.getName() + " " + city.getProvince()));
    }
}
