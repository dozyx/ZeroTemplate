package cn.dozyx;

//import org.junit.Test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
public class GenericTest {

    @Test
    public void foo() {
        Stack<Fruit> fruitStack = new Stack<>();
        fruitStack.push(new Fruit());
        fruitStack.push(new Apple());
//        fruitStack.pushAll(new ArrayList<Apple>());
        fruitStack.pushAll2(new ArrayList<Apple>());

        Collection<Object> dst = new ArrayList<>();
//        fruitStack.popAll(dst);
        fruitStack.popAll2(dst);
        fooVar();
    }

    private static <T> void fooVar(List<T>... args) {
        dangerous(new ArrayList<>());
    }

    @Test
    public void main() {
        String[] attributes = pickTwo("Good", "Fast", "Cheap");
    }

    // UNSAFE - Exposes a reference to its generic parameter array!
    static <T> T[] toArray(T... args) {
        return args;
    }

    static <T> T[] pickTwo(T a, T b, T c) {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0:
                return toArray(a, b);
            case 1:
                return toArray(a, c);
            case 2:
                return toArray(b, c);
        }
        throw new AssertionError(); // Can't get here
    }


    // Mixing generics and varargs can violate type safety!
    @SafeVarargs
    private static void dangerous(List<String>... stringLists) {
//        List<Integer> intList = List.of(42);
        Object[] objects = stringLists;
//        objects[0] = intList; // Heap pollution
        String s = stringLists[0].get(0); // ClassCastException

//        new List<String>[]{};
    }


    private static void swap(List<?> list, int i, int j) {
//        list.set(i, list.set(j, list.get(i)));// 编译错误
        swapHelper(list, i, j);
    }

    // Private helper method for wildcard capture
    private static <E> void swapHelper(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }


    // Uses raw types - unacceptable! (Item 26)
    // 会有警告
    public static Set union(Set s1, Set s2) {
        Set result = new HashSet(s1);
        result.addAll(s2);
        return result;
    }


    private static class Fruit {

    }

    private static class Apple extends Fruit {

    }

    /**
     * 将 {@link StackNonGeneric} 改造成泛型
     * 改造方式有两种：
     * 1. elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY] 强转
     * 2. elements 保留声明为 Object[]，但 pop 方法需要进行对获取的元素进行强转
     */
    private static class Stack<E> {
        private E[] elements;
        private int size = 0;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;

        public Stack() {
//            elements = new Object[DEFAULT_INITIAL_CAPACITY];
            // 方式一：对数组进行强转
            elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
        }

        public void push(E e) {
            ensureCapacity();
            elements[size++] = e;
        }

        public void pushAll(Iterable<E> src) {

        }

        public void pushAll2(Iterable<? extends E> src) {

        }

        public void popAll(Collection<E> dst) {
            while (!isEmpty()) {
                dst.add(pop());
            }
        }

        public void popAll2(Collection<? super E> dst) {
        }

        public E pop() {
            if (size == 0)
                throw new EmptyStackException();
            E result = elements[--size];
            elements[size] = null; // Eliminate obsolete reference
            return result;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        private void ensureCapacity() {
            if (elements.length == size)
                elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    // 一个不使用泛型实现的栈
    // Object-based collection - a prime candidate for generics
    public class StackNonGeneric {
        private Object[] elements;
        private int size = 0;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;

        public StackNonGeneric() {
            elements = new Object[DEFAULT_INITIAL_CAPACITY];
        }

        public void push(Object e) {
            ensureCapacity();
            elements[size++] = e;
        }

        public Object pop() {
            if (size == 0)
                throw new EmptyStackException();
            Object result = elements[--size];
            elements[size] = null; // Eliminate obsolete reference
            return result;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        private void ensureCapacity() {
            if (elements.length == size)
                elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }


    public interface IExtractor<T> {
        void doSomething(T source);
    }

    public static class Extractor implements IExtractor<Source> {
        @Override
        public void doSomething(Source source) {
        }
    }

    public static class ExtractorA implements IExtractor<SourceA> {
        private Extractor extractor = new Extractor();
        @Override
        public void doSomething(SourceA source) {
            Source source1 = new Source();
            extractor.doSomething(source1);
        }
    }

    public static class ExtractorB implements IExtractor<SourceB> {
        private Extractor extractor = new Extractor();
        @Override
        public void doSomething(SourceB source) {
            Source source1 = new Source();
            extractor.doSomething(source1);
        }
    }


    /*public static class ExtractorA1 extends Extractor implements IExtractor<SourceA> {
        @Override
        public void doSomething(SourceA source) {
            Source source1 = new Source();
        }
    }*/

    public static class ExtractorB1 implements IExtractor<SourceB> {
        private Extractor extractor = new Extractor();
        @Override
        public void doSomething(SourceB source) {
            Source source1 = new Source();
        }
    }

    public static class Source {
    }

    public static class SourceA {
    }

    public static class SourceB {
    }

}
