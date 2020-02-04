package com.dozex.annotationprocessortest;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * @author dozyx
 * @date 2020-02-04
 */
public class Processor extends AbstractProcessor {
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        // 在这个方法里初始化 helper/utility 类
        Filer filer = processingEnvironment.getFiler();// Filer 用来生成文件
        Messager messager = processingEnvironment.getMessager();// Messager 用来打印错误、警告等
        Elements elementUtils = processingEnvironment.getElementUtils();// Elements 对程序的元素进行操作
        Types typeUtils = processingEnvironment.getTypeUtils();// 操作 types
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // 唯一一个抽象方法
        // 对注解进行处理
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        // 返回该处理器支持的注解
        return super.getSupportedAnnotationTypes();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        // 返回处理器支持的最新源码版本
        return super.getSupportedSourceVersion();
    }
}
