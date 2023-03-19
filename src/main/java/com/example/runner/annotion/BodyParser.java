package com.example.runner.annotion;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BodyParser {
    String value() default "<jenkins><install plugin=\"{pluginId}\"/></jenkins>";
    boolean required() default true;
}
