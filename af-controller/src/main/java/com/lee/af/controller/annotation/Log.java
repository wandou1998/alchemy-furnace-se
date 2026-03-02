package com.lee.af.controller.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /** 模块名称 */
    String  title()  default "";
}
