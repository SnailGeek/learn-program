package com.lagou.autoconfigbean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        TestBean4.class,
        ImportBeanByImportBeanDefinitionRegistrar.class
})
public class Myconfig {

}
