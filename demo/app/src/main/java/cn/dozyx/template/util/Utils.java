package cn.dozyx.template.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zero on 2017/8/28.
 */

public class Utils {
    // HH:mm:ss
    public static String formatDateTime(long time) {
        return new SimpleDateFormat("HH:mm:ss").format(new Date(time));
    }
}
