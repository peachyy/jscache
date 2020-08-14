package com.peachyy.jscache.annation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Xs.Tao
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cacheable {
    /**
     * key prifix
     * @return
     */
    String prefix();

    /**
     * key name support SpEL
     * @return
     */
    String key();

    /**
     * expire ttl
     * @return
     */
    long ttl() default -1;

    /**
     * cache paramCondition support SpEL
     * 入参条件  为true则执行此注解
     * @return
     */
    String argCondition() default "";

    /**
     * 返回值条件 为true则执行缓存结果 使用#result变量 也就是判断返回值
     * @return
     */
    String returnCondition() default "";

    /**
     * allow null vlaue
     * @return
     */
    boolean allowNullValue() default false;


}
