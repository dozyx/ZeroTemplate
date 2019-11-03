package cn.dozyx;

import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Create by dozyx on 2019/9/19
 **/
public class LeetCodeTest {

    @Test
    public void testLongestPalindrome() {
        print(longestPalindrome("acbab"));
    }

    private String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        int subLen = s.length();
        while (subLen > 1) {
            int maxStart = s.length() - subLen;
            for (int start = 0; start <= maxStart; start++) {
                String substring = s.substring(start, start + subLen);
                if (isPalindrome(substring)) {
                    return substring;
                }
            }
            subLen--;
        }
        return s.substring(0, 1);
    }

    private boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        int middle = s.length() / 2;
        boolean isOdd = s.length() % 2 == 0;
        return isOdd ? s.substring(0, middle).equals(s.substring(middle)) : s.substring(0,
                middle).equals(s.substring(middle + 1));
    }


    private void print(Object msg) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        DateFormat format = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
        System.out.println(
                format.format(Calendar.getInstance().getTime())
                        + " -> " /*+ stackTrace[3].getMethodName()*/ + " "
                        + msg.toString());
    }
}
