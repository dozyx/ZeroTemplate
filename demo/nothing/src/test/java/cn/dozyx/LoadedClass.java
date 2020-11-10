package cn.dozyx;

public class LoadedClass extends BaseLoadedClass {
    public LoadedClass(String name) {
        super(name);
        new ShareLoadedClass(name);
        LoadedClassUtil.printLoader(name);
    }
}
