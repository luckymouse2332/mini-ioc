package com.app.mouse.annocations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Check {
    String value() default "";
    boolean isNotEmpty() default true;
}
