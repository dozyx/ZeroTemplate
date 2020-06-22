package cn.dozyx.core.debug;

import com.blankj.utilcode.util.ReflectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于提供模拟数据
 * Create by dozyx on 2019/8/9
 **/
public class MockUtils {
    private static boolean bool = false;

    public static <T> T generateInstance(Class<T> type) {
        ReflectUtils reflectUtils = ReflectUtils.reflect(type).newInstance();
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == String.class) {
                reflectUtils.field(field.getName(), "测试");
            } else if (field.getType() == Integer.TYPE) {
                reflectUtils.field(field.getName(), 99);
            } else if (field.getType() == Long.TYPE) {
                reflectUtils.field(field.getName(), 9999);
            } else if (field.getType() == Boolean.TYPE) {
                reflectUtils.field(field.getName(), bool);
                bool = !bool;
            } else {
                if (!field.getType().isPrimitive()) {
                    reflectUtils.field(field.getName(), generateInstance(field.getType()));
                }
            }
        }
        return reflectUtils.get();
    }

    public static <T> List<T> generateList(Class<T> type) {
        return generateList(type, 100);
    }

    public static <T> List<T> generateList(Class<T> type, int count) {
        List<T> data = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            data.add(generateInstance(type));
        }
        return data;
    }

}
