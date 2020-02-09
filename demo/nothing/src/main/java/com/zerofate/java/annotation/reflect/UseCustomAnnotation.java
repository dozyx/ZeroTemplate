package com.zerofate.java.annotation.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * @author dozeboy
 * @date 2019-06-01
 */
public class UseCustomAnnotation {
    public static void main(String[] args) {
        Class<SetCustomAnnotation> annotationClass = SetCustomAnnotation.class;
        readAnnotation(annotationClass);

    }

    private static void readAnnotation(AnnotatedElement element) {
        // 在运行时通过反射读取注解里的信息
        try {
            System.out.println("Annotation element values: \n");
            if (element.isAnnotationPresent(TypeHeader.class)) {
                Annotation singleAnnotation = element.getAnnotation(TypeHeader.class);
                TypeHeader header = (TypeHeader) singleAnnotation;
                System.out.println("Developer: " + header.developer());
                System.out.println("Last Modified: " + header.lastModified());
                System.out.print("Team members: ");
                for (String member : header.teamMembers())
                    System.out.print(member + ", ");
                System.out.print("\n");
                System.out.println("Meaning of Life: "+ header.meaningOfLife());

            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
