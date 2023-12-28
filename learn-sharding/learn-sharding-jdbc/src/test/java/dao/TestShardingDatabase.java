package dao;

import com.snail.learn.RunBoot;
import com.snail.learn.entity.Position;
import com.snail.learn.entity.PositionDetail;
import com.snail.learn.repository.PositionDetailRepository;
import com.snail.learn.repository.PositionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunBoot.class)
public class TestShardingDatabase {

    @Resource
    private PositionRepository positionRepository;
    @Resource
    private PositionDetailRepository positionDetailRepository;

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
}
