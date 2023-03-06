import com.lagou.edu.dao.ResumeDao;
import com.lagou.edu.pojo.Resume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;
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

    @Test
    public void testFindONe() {
        Resume resume = new Resume();
        resume.setId(1L);
        resume.setName("张三");
        final Example<Resume> ex = Example.of(resume);
        final Resume resume1 = resumeDao.findOne(ex).get();
        System.out.println(resume1);
    }

    @Test
    public void testSave() {
        final Resume resume = new Resume();
        resume.setId(4L);
        resume.setName("赵六");
        resume.setAddress("大连");
        resume.setPhone("11111111");
        final Resume save = resumeDao.save(resume);
        System.out.println(save);
    }

    @Test
    public void testDelete() {
        resumeDao.deleteById(4L);
    }

    @Test
    public void testFindAll() {
        final List<Resume> all = resumeDao.findAll();
        System.out.println(all);
    }

    /*
     *
     * 引入jpql语句查询
     *
     *  */
    @Test
    public void testxxx() {
        Resume resumes = resumeDao.findByJpql(1L);
        System.out.println(resumes);
    }

    @Test
    public void testMulitParams() {
        final List<Resume> resumes = resumeDao.findByJpqlParams(1L, "张三");
        System.out.println(resumes);
    }

    /*
     * 方式三：引入原生的sql语句
     * */


    @Test
    public void testSql() {
        final Resume bySql = resumeDao.findBySql("张%", "北%");
        System.out.println(bySql);
    }

    /*
     * 方式四：接口中自定义方法
     * */
    @Test
    public void testMethodName() {
        final List<Resume> byNameLike = resumeDao.findByNameLikeAndAddress("张%", "北京");
        System.out.println(byNameLike);
    }

    /*
     * 方式5：动态查询 service传入dao层的条件不确定，把service拿到条件封装成一个对象传递给dao层，这个对象就叫做
     * */

    @Test
    public void testSpecification() {
        final Specification<Resume> specification = new Specification<>() {
            @Override
            public Predicate toPredicate(Root<Resume> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 获得name属性
                final Path<Object> name = root.get("name");

                final Predicate predicate = criteriaBuilder.equal(name, "张三");
                return predicate;
            }
        };
        final Optional<Resume> resume = resumeDao.findOne(specification);
        final Resume resume1 = resume.get();
        System.out.println(resume1);
    }

    @Test
    public void testSpecificationMultiCondition() {
        final Specification<Resume> specification = new Specification<>() {
            @Override
            public Predicate toPredicate(Root<Resume> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 获得name属性
                final Path<Object> name = root.get("name");
                final Predicate predicate = criteriaBuilder.equal(name, "张三");
                final Path<Object> address = root.get("address");
                final Predicate predicate1 = criteriaBuilder.like(address.as(String.class), "北%");
                final Predicate predicate2 = criteriaBuilder.and(predicate, predicate1);
                return predicate2;
            }
        };
        final Optional<Resume> resume = resumeDao.findOne(specification);
        final Resume resume1 = resume.get();
        System.out.println(resume1);
    }

    @Test
    public void findAllSort() {
        final Sort sort = new Sort(Sort.Direction.DESC, "id");
        final List<Resume> all = resumeDao.findAll(sort);
        System.out.println(all);
    }

    @Test
    public void findAllPage() {
        final Pageable pageRequest = PageRequest.of(1, 2);
        final Page<Resume> all = resumeDao.findAll(pageRequest);
        all.get().forEach(r -> System.out.println(r));
    }
}
