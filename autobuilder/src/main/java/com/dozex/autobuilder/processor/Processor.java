package com.dozex.autobuilder.processor;

import com.dozex.autobuilder.annotation.Builder;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * https://juejin.im/post/5a0ae00451882535cd4a5a29
 * @author dozyx
 * @date 2020-02-04
 */
public class Processor extends AbstractProcessor {
    private ProcessingEnvironment processingEnvironment;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.processingEnvironment = processingEnvironment;
        processingEnvironment.getMessager().printMessage(Diagnostic.Kind.WARNING, "builder processor init");
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Builder.class)) {
            String packageName = processingEnvironment.getElementUtils().getPackageOf(
                    element).getQualifiedName().toString();
            String elementName = element.getSimpleName().toString();
            ClassName className = ClassName.get(packageName,
                    String.format("%sBuilder", elementName));

            TypeSpec typeSpec = createTypeSpec(element, className, elementName);

            JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();
            try {
                javaFile.writeTo(processingEnvironment.getFiler());
            } catch (IOException e) {

            }
        }
        return true;
    }

    private TypeSpec createTypeSpec(Element element, ClassName className, String elementName) {
        Set<Element> fieldElements = getFields(element);
        List<FieldSpec> fieldSpecs = new ArrayList<>(fieldElements.size());
        List<MethodSpec> setterSpecs = new ArrayList<>(fieldElements.size());
        for (Element field : fieldElements) {
            // 添加 filed 声明语句和 setter 方法
            TypeName fieldType = TypeName.get(field.asType());
            String fieldName = field.getSimpleName().toString();
            FieldSpec fieldSpec = FieldSpec.builder(fieldType, fieldName, Modifier.PRIVATE).build();
            fieldSpecs.add(fieldSpec);
            MethodSpec setterSpec = MethodSpec
                    .methodBuilder(fieldName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(className)
                    .addParameter(fieldType, fieldName)
                    .addStatement("this.$N = $N", fieldName, fieldName)
                    .addStatement("return this")
                    .build();
            setterSpecs.add(setterSpec);
        }

        // build method
        // 添加 build 方法
        TypeName elementType = TypeName.get(element.asType());
        String instanceName = toCamelCase(elementName);
        MethodSpec.Builder buildMethodBuilder = MethodSpec
                .methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .returns(elementType)
                .addStatement("$1T $2N = new $1T()", elementType, instanceName);
        for (FieldSpec fieldSpec : fieldSpecs) {
            buildMethodBuilder.addStatement("$1N.$2N = $2N", instanceName, fieldSpec);
        }
        buildMethodBuilder.addStatement("return $N", instanceName);
        MethodSpec buildMethod = buildMethodBuilder.build();

        return TypeSpec
                .classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addFields(fieldSpecs)
                .addMethods(setterSpecs)
                .addMethod(buildMethod)
                .build();
    }

    private String toCamelCase(String s) {
        // 第一个字母小写
        if (s == null || s.length() < 1) return s;
        char firstChar = s.charAt(0);
        char newFirst = Character.toLowerCase(firstChar);
        return s.replace(firstChar, newFirst);
    }

    private Set<Element> getFields(Element element) {
        Set<Element> fields = new LinkedHashSet<>();
        for (Element enclosedElement : element.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.FIELD) {
                fields.add(enclosedElement);
            }
        }
        return fields;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {

        return Collections.singleton(Builder.class.getCanonicalName());
    }
}
