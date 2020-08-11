package com.peachyy.xcache.core;

import com.peachyy.xcache.core.advisor.CacheAdvisorSelector;

import org.springframework.context.annotation.Import;

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

    String serializer() default "";
}