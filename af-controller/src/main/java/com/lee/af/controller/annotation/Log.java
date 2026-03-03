package com.lee.af.controller.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * target : 指定注解的作用位置
 * Retenion : 指定注解的生命周期
 * Docemented : 指定注解是否被包含在javadoc中
 *
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /** 模块名称 */
    String  title()  default "";
}
