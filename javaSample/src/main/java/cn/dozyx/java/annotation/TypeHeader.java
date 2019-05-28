package cn.dozyx.java.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// This is the annotation to be processed
// Default for Target is all Java Elements
// Change retention policy to RUNTIME (default is CLASS)
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeHeader {
    // Default value specified for developer attribute
    String developer() default "Unknown";

    String lastModified();

    String[] teamMembers();

    int meaningOfLife();
}