package edu.yuriikoval1997.prometheusdemo.aspects;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface YuriiCounted {

    String[] labels();
}
