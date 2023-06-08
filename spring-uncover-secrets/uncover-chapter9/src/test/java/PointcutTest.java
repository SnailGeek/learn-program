import com.snail.annotation.ClassLevelAnnotation;
import com.snail.annotation.GenericTargetObject;
import com.snail.annotation.GenericTargetObjectNoClassLevel;
import com.snail.annotation.MethodLevelAnnotation;
import org.junit.Test;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

import static org.junit.Assert.assertTrue;

public class PointcutTest {
    @Test
    public void testNameMatch() {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("matches");
        pointcut.setMappedNames(new String[]{"matches", "isRuntime"});
        pointcut.setMappedNames(new String[]{"match*", "*matches", "mat*es"});
    }

    @Test
    public void testJdkRegExp() {
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPattern(".*match.*");
        pointcut.setPatterns(new String[]{".*match.*", ".*matches"});
    }

    @Test
    public void testAnnotation() throws NoSuchMethodException {
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(ClassLevelAnnotation.class);
        boolean matches = pointcut.getClassFilter().matches(GenericTargetObject.class);
        assertTrue(matches);
        boolean matches2 = pointcut.getMethodMatcher().matches(GenericTargetObject.class.getMethod("gMehtod2"), GenericTargetObject.class);
        assertTrue(matches2);

        AnnotationMatchingPointcut methodPointcut =  AnnotationMatchingPointcut.forMethodAnnotation(MethodLevelAnnotation.class);
        boolean methodMatch = methodPointcut.getMethodMatcher().matches(GenericTargetObject.class.getMethod("gMehtod1"), GenericTargetObject.class);
        assertTrue(methodMatch);

        AnnotationMatchingPointcut pointcut1 = new AnnotationMatchingPointcut(ClassLevelAnnotation.class, MethodLevelAnnotation.class);
        boolean matches1 = pointcut1.getMethodMatcher().matches(GenericTargetObject.class.getMethod("gMehtod2"), GenericTargetObject.class);
        assertTrue(matches1);
    }

    @Test
    public void test() throws NoSuchMethodException {
        AnnotationMatchingPointcut pointcut2 = new AnnotationMatchingPointcut(ClassLevelAnnotation.class, MethodLevelAnnotation.class);
        boolean matches3 = pointcut2.getMethodMatcher().matches(GenericTargetObjectNoClassLevel.class.getMethod("gMehtod1"), GenericTargetObjectNoClassLevel.class);
        assertTrue(matches3);
    }
}
