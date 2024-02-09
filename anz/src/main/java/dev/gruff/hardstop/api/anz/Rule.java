package dev.gruff.hardstop.api.anz;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Rule {

    String title() default "";

    Class onFail() default Object.class;
    Class onPass() default Object.class;

    String id();
}
