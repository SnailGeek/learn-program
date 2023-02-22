package com.lagou.edu.mvcframwork.annotations;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LgAutowired {
    String value() default "";

}
