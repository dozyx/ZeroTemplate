package com.dozex.butterknife_compiler;

import com.dozex.butterknife_annotations.BindView;
import com.dozex.butterknife_annotations.OnClick;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

// AutoService 自动生成 resources/META-INF/services 目录下注解注解处理器的文件。如果不使用 AutoService，也可以自己手动创建。
@AutoService(Processor.class)
public class MyProcessor extends AbstractProcessor {
    private ProcessingEnvironment mProcessingEnvironment;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mProcessingEnvironment = processingEnvironment;
        messager = mProcessingEnvironment.getMessager();
        log("processor init");
    }

    private void log(String msg){
        messager.printMessage(Diagnostic.Kind.WARNING, msg);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        // TypeElement 表示一个类或 interface
        // RoundEnvironment 用来查询本轮注解处理的相关信息

        for (TypeElement annotation : annotations) {
            log("annotation: " + annotation.getSimpleName().toString());
        }
        for (Element rootElement : roundEnvironment.getRootElements()) {
            log("rootElement: " + rootElement.getSimpleName().toString());
        }

        for (TypeElement typeElement : getTypeElementsToProcess(roundEnvironment.getRootElements(), annotations)) {

            // 包名
            String packageName = mProcessingEnvironment.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
            // 类名
            String typeName = typeElement.getSimpleName().toString();
            // 根据包名和类名创建一个 ClassName 对象，用来表示注解所在的类
            ClassName className = ClassName.get(packageName, typeName);

            // 创建一个表示将要生成的类的 ClassName 对象
            ClassName generatedClassName = ClassName
                    .get(packageName, typeName + "Binder");

            // define the wrapper class
            // public 访问
            TypeSpec.Builder classBuilder = TypeSpec.classBuilder(generatedClassName)
                    .addModifiers(Modifier.PUBLIC);
            //.addAnnotation(Keep.class);

            // add constructor
            // 添加构造函数，将 activity 作为参数。函数中调用了 bindViews 和 bindOnClicks 两个方法
            classBuilder.addMethod(MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(className, "activity")
                    .addStatement("$N($N)",
                            "bindViews",
                            "activity")
                    .addStatement("$N($N)",
                            "bindOnClicks",
                            "activity")
                    .build());

            // add method that maps the views with id
            // 添加 bindViews 方法，参数为 activity
            MethodSpec.Builder bindViewsMethodBuilder = MethodSpec
                    .methodBuilder("bindViews")
                    .addModifiers(Modifier.PRIVATE)
                    .returns(void.class)
                    .addParameter(className, "activity");

            // 解析 BindView 注解，添加 bindViews 方法里的 findViewById 语句
            for (VariableElement variableElement : ElementFilter.fieldsIn(typeElement.getEnclosedElements())) {
                BindView bindView = variableElement.getAnnotation(BindView.class);
                if (bindView != null) {
                    bindViewsMethodBuilder.addStatement("$N.$N = ($T) $N.findViewById($L)",
                            "activity",
                            variableElement.getSimpleName(),
                            variableElement,
                            "activity",
                            bindView.value());
                }
            }
            classBuilder.addMethod(bindViewsMethodBuilder.build());

            // add method that attaches the onClickListeners
            ClassName androidOnClickListenerClassName = ClassName.get(
                    "android.view",
                    "View",
                    "OnClickListener");

            ClassName androidViewClassName = ClassName.get(
                    "android.view",
                    "View");

            // 添加 bindOnClicks 方法来绑定点击事件
            MethodSpec.Builder bindOnClicksMethodBuilder = MethodSpec
                    .methodBuilder("bindOnClicks")
                    .addModifiers(Modifier.PRIVATE)
                    .returns(void.class)
                    .addParameter(className, "activity", Modifier.FINAL);

            for (ExecutableElement executableElement : ElementFilter.methodsIn(typeElement.getEnclosedElements())) {
                OnClick onClick = executableElement.getAnnotation(OnClick.class);
                if (onClick != null) {
                    TypeSpec OnClickListenerClass = TypeSpec.anonymousClassBuilder("")
                            .addSuperinterface(androidOnClickListenerClassName)
                            .addMethod(MethodSpec.methodBuilder("onClick")
                                    .addModifiers(Modifier.PUBLIC)
                                    .addParameter(androidViewClassName, "view")
                                    .addStatement("$N.$N($N)",
                                            "activity",
                                            executableElement.getSimpleName(),
                                            "view")
                                    .returns(void.class)
                                    .build())
                            .build();
                    bindOnClicksMethodBuilder.addStatement("$N.findViewById($L).setOnClickListener($L)",
                            "activity",
                            onClick.value(),
                            OnClickListenerClass);
                }
            }
            classBuilder.addMethod(bindOnClicksMethodBuilder.build());

            // write the defines class to a java file
            try {
                JavaFile.builder(packageName,
                        classBuilder.build())
                        .build()
                        .writeTo(mProcessingEnvironment.getFiler());
            } catch (IOException e) {
                mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, e.toString(), typeElement);
            }

        }

        return true;
    }

    private Set<TypeElement> getTypeElementsToProcess(Set<? extends Element> elements,
            Set<? extends TypeElement> supportedAnnotations) {
        Set<TypeElement> typeElements = new HashSet<>();
        for (Element element : elements) {
            if (element instanceof TypeElement) {
                boolean found = false;
                for (Element subElement : element.getEnclosedElements()) {
                    for (AnnotationMirror mirror : subElement.getAnnotationMirrors()) {
                        for (Element annotation : supportedAnnotations) {
                            if (mirror.getAnnotationType().asElement().equals(annotation)) {
                                typeElements.add((TypeElement) element);
                                found = true;
                                break;
                            }
                        }
                        if (found) break;
                    }
                    if (found) break;
                }
            }
        }
        return typeElements;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        // 该处理器用来处理 BindView 和 OnClick 两个注解
        return new HashSet<String>() {{
            add(BindView.class.getCanonicalName());
            add(OnClick.class.getCanonicalName());
        }};
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
