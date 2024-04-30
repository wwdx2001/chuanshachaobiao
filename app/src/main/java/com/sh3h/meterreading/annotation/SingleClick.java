package com.sh3h.meterreading.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiaochao.dev@gamil.com
 * @date 2020/4/30 11:15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SingleClick {
    /* 点击间隔时间 */
    long value() default 1200;
}