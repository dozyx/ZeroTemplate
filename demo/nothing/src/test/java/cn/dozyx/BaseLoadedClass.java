package cn.dozyx;

public abstract class BaseLoadedClass {
    private String name;

    public BaseLoadedClass(String name) {
        this.name = name;
        LogUtils.print(name + " & " + getClass().getSimpleName() + " & " + getClass().getClassLoader());
    }
}
