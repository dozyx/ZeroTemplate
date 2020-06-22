package cn.dozyx.template.justfortest;

public class A {
    private static final int a[] = {1, 2, 3};

    public static int[] getArray() {
        return a;
    }

    public static void main(String[] args) {
        int[] array = A.getArray();
    }
}

