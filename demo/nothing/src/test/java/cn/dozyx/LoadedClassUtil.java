package cn.dozyx;

public class LoadedClassUtil {
    public static void printLoader(String name) {
        LogUtils.print(name + " & " + LoadedClassUtil.class.getSimpleName() + " & " + LoadedClassUtil.class.getClassLoader());
    }
}
