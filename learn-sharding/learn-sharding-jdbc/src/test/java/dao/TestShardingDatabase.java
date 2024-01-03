package dao;

import com.snail.learn.RunBoot;
import com.snail.learn.entity.BOrder;
import com.snail.learn.entity.City;
import com.snail.learn.entity.Position;
import com.snail.learn.entity.PositionDetail;
import com.snail.learn.repository.BOrderRepository;
import com.snail.learn.repository.CitylRepository;
import com.snail.learn.repository.PositionDetailRepository;
import com.snail.learn.repository.PositionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.border.Border;
import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunBoot.class)
public class TestShardingDatabase {

    @Resource
    private PositionRepository positionRepository;
    @Resource
    private PositionDetailRepository positionDetailRepository;
    @Resource
    private CitylRepository citylRepository;

    @Test
    public void testAdd() {
        for (int i = 1; i <= 20; i++) {
            Position position = new Position();
//            position.setId(i);
            position.setName("lagou" + i);
            position.setSalary("100000000");
            position.setCity("beijing");
            positionRepository.save(position);
        }
    }

    @Test
    public void testAdd2() {
        for (int i = 1; i <= 20; i++) {
            Position position = new Position();
            position.setName("lagou" + i);
            position.setSalary("100000000");
            position.setCity("beijing");
            positionRepository.save(position);

            PositionDetail positionDetail = new PositionDetail();
            positionDetail.setPid(position.getId());
            positionDetail.setDescription("this is message" + i);
            positionDetailRepository.save(positionDetail);
        }
    }

    @Test
    public void testFind() {
        Object object = positionRepository.findPositionById(947117061216141313L);
        Object[] postion = (Object[]) object;
        System.out.println(postion[0] + "  " + postion[1] + "  " + postion[2] + "  " + postion[3] + "  " + postion[4]);
    }

    @Test
    public void boradCast() {
        City city = new City();
        city.setName("beijing");
        city.setProvince("beijing");
        citylRepository.save(city);
    }

    @Resource
    private BOrderRepository bOrderRepository;

    @Test
    @Repeat(100)
    public void testOrder() {
        Random random = new Random();
        int companyId = random.nextInt(10);
        BOrder order = new BOrder();
        order.setIsDel(false);
        order.setCompanyId(companyId);
        order.setPositionId(13212L);
        order.setUserId(123);
        order.setPublishUserId(134);
        order.setResumeType(1);
        order.setStatus("AUTO");
        order.setCreateTime(new Date());
        order.setOperateTime(new Date());
        bOrderRepository.save(order);
    }
}
