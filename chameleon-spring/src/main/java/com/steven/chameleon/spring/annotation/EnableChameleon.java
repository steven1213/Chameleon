package com.steven.chameleon.spring.annotation;

import com.steven.chameleon.spring.config.ChameleonAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ChameleonAutoConfiguration.class)
public @interface EnableChameleon {

    /**
     * 是否启用Web功能
     */
    boolean enableWeb() default true;

    /**
     * 是否启用AOP功能
     */
    boolean enableAop() default true;

    /**
     * 包扫描路径
     */
    String[] basePackages() default {};
}
