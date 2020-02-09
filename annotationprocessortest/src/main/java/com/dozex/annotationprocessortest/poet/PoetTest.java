package com.dozex.annotationprocessortest.poet;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;

import javax.lang.model.element.Modifier;


/**
 * @author dozyx
 * @date 2020-02-04
 */
public class PoetTest {
    public static void main(String[] args) {
        // 构建方法
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();

        // 构建类
        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWord")
                .addModifiers(Modifier.PUBLIC,Modifier.FINAL)
                .addMethod(main)
                .build();

        // 构建文件
        JavaFile javaFile = JavaFile.builder("com.example.helloword", helloWorld).build();
        try {
//            javaFile.writeTo(System.out);
            javaFile.writeTo(new File("~/Desktop"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
