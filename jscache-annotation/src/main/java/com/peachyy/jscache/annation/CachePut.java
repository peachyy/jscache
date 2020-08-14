package com.peachyy.jscache.annation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
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
@Repeatable(value = CachePuts.class)
public @interface CachePut {
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
     * cache condition support SpEL
     * 参数满足条件后 执行此条件表达式
     * @return
     */
    String argCondition() default "";
    /**
     * 返回值条件 为true则执行缓存结果  使用#result变量 也就是判断返回值
     * @return
     */
    String returnCondition() default "";

    /**
     * allow null vlaue
     * @return
     */
    boolean allowNullValue() default false;
}
