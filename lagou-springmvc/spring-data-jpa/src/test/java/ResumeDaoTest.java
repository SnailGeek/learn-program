import com.lagou.edu.dao.ResumeDao;
import com.lagou.edu.pojo.Resume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ApplicationContext.xml"})
public class ResumeDaoTest {

    @Autowired
    private ResumeDao resumeDao;

    @Test
    public void testFindById() {
        final Optional<Resume> optional = resumeDao.findById(1L);
        final Resume resume = optional.get();
        System.out.println(resume);
    }
}
