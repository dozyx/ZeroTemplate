package com.zerofate.java.annotation.reflect;

// 创建一个使用了自定义注解的类
// This is the annotation being applied to a class
@TypeHeader(developer = "Bob Bee",
        lastModified = "2013-02-12",
        teamMembers = { "Ann", "Dan", "Fran" },
        meaningOfLife = 42)

public class SetCustomAnnotation {
    // Class contents go here
}