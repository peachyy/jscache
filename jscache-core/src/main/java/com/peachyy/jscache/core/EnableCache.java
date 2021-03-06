package com.peachyy.jscache.core;

import com.peachyy.jscache.core.advisor.CacheAdvisorSelector;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Xs.Tao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CacheAdvisorSelector.class)
public @interface EnableCache {
    /**
     * Alias for the {@link #basePackages()}
     * @return
     */
    String[] value() default "";

    /**
     * AOP的packages包名 不设置表示 不限制
     * @return
     */
    String[] basePackages() default "";

    String serializer() default "fastJson";

    int order() default Ordered.LOWEST_PRECEDENCE;

    AdviceMode mode() default AdviceMode.PROXY;

    boolean proxyTargetClass() default false;
}