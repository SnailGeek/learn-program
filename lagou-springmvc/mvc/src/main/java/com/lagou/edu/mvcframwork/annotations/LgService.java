package com.lagou.edu.mvcframwork.annotations;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LgService {
    String value() default "";

}
