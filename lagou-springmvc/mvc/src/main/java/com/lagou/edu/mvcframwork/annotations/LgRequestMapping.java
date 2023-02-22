package com.lagou.edu.mvcframwork.annotations;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LgRequestMapping {
    String value() default "";

}
