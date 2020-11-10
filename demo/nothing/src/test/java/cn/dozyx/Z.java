package cn.dozyx;

public class Z {
    public Z() {
        LogUtils.print(getClass() + " & " + getClass().getClassLoader());
    }
}
