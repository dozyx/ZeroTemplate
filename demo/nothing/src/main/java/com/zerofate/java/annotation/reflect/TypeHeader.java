package com.zerofate.java.annotation.reflect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 创建一个注解
 * RetentionPolicy.RUNTIME 在运行时可以通过反射读取注解
 *
 * @author dozeboy
 * @date 2019-06-01
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeHeader {
    // Default value specified for developer attribute
    String developer() default "Unknown";
    String lastModified();
    String [] teamMembers();
    int meaningOfLife();
}
