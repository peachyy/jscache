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
@Repeatable(value = CacheEvicts.class)
public @interface CacheEvict {
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
     * 参数满足条件后 才会执行此标记
     * @return
     */
    String argCondition() default "";


}
