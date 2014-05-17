package com.page5of4.codon;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageHandler {
    AutomaticallySubscribe autoSubscribe() default AutomaticallySubscribe.DEFAULT;
}
