package cn.dozyx;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Create by timon on 2019/12/17
 **/
public class AlgorithmTest {

    @Test
    public void test() {
        print(replaceSpace(new StringBuffer(" ")));
    }

    public String replaceSpace(StringBuffer str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        String replaceStr = "%20";
        int length = str.length();
        for (int i = length - 1; i >= 0; i--) {
            char c = str.charAt(i);
            if (c == ' ') {
                str.replace(i, i + 1, replaceStr);
            }
        }
        return str.toString();
    }

    private static void print(Object msg) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        DateFormat format = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
        System.out.println(
                format.format(Calendar.getInstance().getTime())
                        + " -> " /*+ stackTrace[3].getMethodName()*/ + " "
                        + msg.toString());
    }


}
