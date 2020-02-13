package com.gupaoedu.service.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Limited {

    /**
     * 最大限制数量
     *
     * @return
     */
    int value();
}
