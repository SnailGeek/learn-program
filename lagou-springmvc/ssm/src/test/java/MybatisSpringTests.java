import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:application*.xml"})
public class MybatisSpringTests {
    @Autowired
    private AccountService accountService;

    @Test
    public void testAccount() throws Exception {
        final List<Account> accounts = accountService.queryAccountList();
        for (Account account : accounts) {
            System.out.println(account);
        }

    }
}
