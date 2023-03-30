package com.example.ci.annotion;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PathParser {
    boolean required() default true;
}
