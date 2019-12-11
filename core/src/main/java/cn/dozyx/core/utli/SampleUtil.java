package cn.dozyx.core.utli;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dozyx
 * @date 2019-10-30
 */
public final class SampleUtil {

    public static List<String> getStrings() {
        return getStrings(50);
    }

    public static List<String> getStrings(int count) {
        return getStrings(count, 0);
    }

    public static List<String> getStrings(int count, int offset) {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            datas.add("sample: " + (i + offset));
        }
        return datas;
    }
}
