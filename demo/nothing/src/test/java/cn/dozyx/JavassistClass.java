package cn.dozyx;

public class JavassistClass {
    private String name;
    private String newName;

    public JavassistClass() {
        this("default");
    }

    public JavassistClass(String name) {
        String tmpName = "tmp" + name;
        this.name = tmpName;
        foo(tmpName);
        newName = new String("New" + name);
    }

    public void foo(String name) {
        System.out.println("name = " + name);
    }

    public void sayName() {
        System.out.println(name);
    }
}
