import com.snail.annotation.ClassLevelAnnotation;
import com.snail.annotation.GenericTargetObject;
import com.snail.annotation.GenericTargetObjectNoClassLevel;
import com.snail.annotation.MethodLevelAnnotation;
import com.snail.filter.Foo;
import org.junit.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.aop.support.Pointcuts;
import org.springframework.aop.support.annotation.AnnotationClassFilter;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
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

        AnnotationMatchingPointcut methodPointcut = AnnotationMatchingPointcut.forMethodAnnotation(MethodLevelAnnotation.class);
        boolean methodMatch = methodPointcut.getMethodMatcher().matches(GenericTargetObject.class.getMethod("gMehtod1"), GenericTargetObject.class);
        assertTrue(methodMatch);

        AnnotationMatchingPointcut pointcut1 = new AnnotationMatchingPointcut(ClassLevelAnnotation.class, MethodLevelAnnotation.class);
        boolean matches1 = pointcut1.getMethodMatcher().matches(GenericTargetObject.class.getMethod("gMehtod2"), GenericTargetObject.class);
        assertTrue(matches1);
    }

    @Test
    public void testComposable() {
        ClassFilter classFilter1 = new AnnotationClassFilter(ClassLevelAnnotation.class);
        MethodMatcher methodMatcher1 = new AnnotationMethodMatcher(MethodLevelAnnotation.class);
        MethodMatcher methodMatcher2 = new AnnotationMethodMatcher(MethodLevelAnnotation.class);
        ClassFilter classFilter2 = new AnnotationClassFilter(ClassLevelAnnotation.class);

        ComposablePointcut pointcut1 = new ComposablePointcut(classFilter1, methodMatcher1);
        ComposablePointcut pointcut2 = new ComposablePointcut(classFilter2, methodMatcher2);

        ComposablePointcut unitedPoincut = pointcut1.union(pointcut2);
        ComposablePointcut intersectionPoincut = pointcut1.intersection(pointcut2);
        assertEquals(pointcut1, intersectionPoincut);

        //可以使用工具类
        Pointcut unioned = Pointcuts.union(pointcut1, pointcut2);
        Pointcut intersection = Pointcuts.intersection(pointcut1, pointcut2);

        //Pointcut根据ClassFilter和MethodMatcher划分为两部分，一部分是为了宠用这些定义，另外一部分可以为了组合，下面可以看到这两个目的
        ComposablePointcut pointcut3 = pointcut2.union(classFilter1).intersection(methodMatcher1);


    }
}
