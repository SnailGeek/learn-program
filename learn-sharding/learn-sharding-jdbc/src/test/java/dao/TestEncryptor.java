package dao;

import com.snail.learn.RunBoot;
import com.snail.learn.entity.CUser;
import com.snail.learn.repository.CUserlRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunBoot.class)
public class TestEncryptor {
    @Resource
    private CUserlRepository cUserlRepository;

    @Test
    public void test1() {
        CUser cUser = new CUser();
        cUser.setName("lagou");
        cUser.setPwd("abc");
        cUserlRepository.save(cUser);
    }

    @Test
    public void testFind() {
        List<CUser> list = cUserlRepository.findByPwd("abc");
        list.forEach(cUser -> System.out.println(cUser.getId() + " " + cUser.getName() + " " + cUser.getPwd()));
    }
}
