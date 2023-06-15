import com.snail.advice.AppConfig;
import com.snail.advice.AroudAdviceService;
import com.snail.advice.AroundAdviceConfig;
import com.snail.advice.MyService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AdviceTest {
    @Test
    public void testAdvice() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        MyService myService = context.getBean(MyService.class);
        myService.doSomething();
    }

    @Test
    public void testAroudAdvice() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AroundAdviceConfig.class);
        AroudAdviceService myService = context.getBean(AroudAdviceService.class);
        myService.demo();
    }

    @Test
    public void testAroudAdvice2() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AroundAdviceConfig.class);
        AroudAdviceService myService = context.getBean(AroudAdviceService.class);
        System.out.println(myService.demo2());
    }
}
