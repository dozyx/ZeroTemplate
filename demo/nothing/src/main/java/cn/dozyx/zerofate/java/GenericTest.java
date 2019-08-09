package cn.dozyx.zerofate.java;

import java.util.List;

/**
 * @author dozeboy
 * @date 2018/3/9
 */

public class GenericTest<T> {

    public T get(T data) {
        return data;
    }


    private static class Type {

    }

    private static class TypeChild extends Type {

    }

    private abstract static class A {
        public abstract <T extends Type> T foo();
    }

    private static class B extends A {
        @Override
        public <T extends Type> T foo() {
            return (T) new TypeChild();
        }
    }
}
