package com.zerofate.template.purejava;

/**
 * Created by zero on 2017/8/15.
 */

public class JavaTest {

    public static void main(String[] args) {
        Child child = new Child();
        Parent parent1 = new Parent();
        Object parent2 = null;
        try {
            parent2 = child.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        System.out.println(child instanceof Test);
        System.out.println(parent1 instanceof Test);
        System.out.println(parent2 instanceof Test);

    }

    private static class Parent implements Test {

    }

    private interface Test {

    }

    private static class Child extends Parent implements Cloneable{
        @Override
        public Object clone() throws CloneNotSupportedException {
            return new Child();
        }
    }

    private static void findSameNums(int[] source, int[] target) {
        if (source == null || source.length == 0 || target == null || target.length == 0) {
            return;
        }
        int i = 0;
        int j = 0;
        while (i < source.length && j < target.length) {
            if (target[j] == source[i]) {
                System.out.println(source[i] + " ");
                i++;
                j++;
            } else if (target[j] < source[i]) {
                j++;
            } else {
                i++;
            }
        }
    }

}
