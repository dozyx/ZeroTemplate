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

    private static final SimpleDateFormat LOG_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS",
            Locale.getDefault());

    public static void print(byte[] bytes) {
        print(Arrays.toString(bytes));
    }

    public static void print(Object msg) {
        if (msg == null) {
            msg = "is null";
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        System.out.println(formatTime(Calendar.getInstance().getTime().getTime())
                + " -> " /*+ stackTrace[3].getMethodName()*/ + " "
                + msg.toString() + " & thread: " + Thread.currentThread());
    }

    public static String formatTime(long timeInMill) {
        return LOG_TIME_FORMAT.format(timeInMill);
    }

}
