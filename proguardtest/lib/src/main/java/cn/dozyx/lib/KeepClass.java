package cn.dozyx.lib;

import androidx.annotation.Keep;

/**
 * 加了 Keep 的类不会被混淆，包括它的成员也不会被混淆。即使不加混淆规则也会生效。
 * 子类不会继承 Keep
 * 内部类还是会被混淆，不管是不是 static
 */
@Keep
public class KeepClass {
    private Status status = Status.SHOW;
    private NestedClass obj1 = new NestedClass();
    private NestedClass2 obj2 = new NestedClass2();

    public static void publicMethod() {

    }

    private static void privateMethod() {

    }

    public enum Status {
        SHOW, HIDE
    }

    public class NestedClass {

    }

    public static class NestedClass2 {

    }


}
