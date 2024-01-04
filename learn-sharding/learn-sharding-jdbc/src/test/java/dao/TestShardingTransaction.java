package dao;

import com.snail.learn.RunBoot;
import com.snail.learn.entity.Position;
import com.snail.learn.entity.PositionDetail;
import com.snail.learn.repository.PositionDetailRepository;
import com.snail.learn.repository.PositionRepository;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunBoot.class)
public class TestShardingTransaction {
    @Resource
    private PositionDetailRepository positionDetailRepository;
    @Resource
    private PositionRepository positionRepository;

    @Test
    @Transactional
    @ShardingTransactionType(TransactionType.XA)
    public void test1() {
//        TransactionTypeHolder.set(TransactionType.XA);
        for (int i = 1; i <= 3; i++) {
            Position position = new Position();
            position.setName("root" + i);
            position.setSalary("10000000");
            position.setCity("beijing");
            positionRepository.save(position);

            if (i == 3) {
                throw new RuntimeException("人为异常");
            }

            PositionDetail positionDetail = new PositionDetail();
            positionDetail.setPid(position.getId());
            positionDetail.setDescription("this is root" + i);
            positionDetailRepository.save(positionDetail);
        }
    }
}
