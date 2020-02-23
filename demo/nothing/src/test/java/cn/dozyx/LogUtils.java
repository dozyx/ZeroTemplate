package cn.dozyx;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author dozyx
 * @date 2020-02-03
 */
public class LogUtils {
    public static void print(byte[] bytes){
        print(Arrays.toString(bytes));
    }

    public static void print(Object msg) {
        if (msg == null){
            msg = "is null";
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        DateFormat format = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
        System.out.println(
                format.format(Calendar.getInstance().getTime())
                        + " -> " /*+ stackTrace[3].getMethodName()*/ + " "
                        + msg.toString());
    }
}
