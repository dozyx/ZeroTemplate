package cn.dozyx;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Create by dozyx on 2019/12/17
 **/
public class AlgorithmTest {

    @Test
    public void findFirstSubStringIndex() {
        String A = "abcdeabcdef";
        String B = "abcddbaaabbdabcabcdeabcdefdeabcdeabcdeabcdfabcdeabcdea";
        int nextStartIndex = -1;// 记录 B 中 A 的第一个字符出现的位置
        for (int i = 0; i < B.length() - A.length() + 1; i++) {
            if (B.charAt(i) == A.charAt(0)) {
                // 确定起始字符，遍历后续字符是否相同
                for (int j = 1; j < A.length(); j++) {
                    if (B.charAt(i + j) == A.charAt(0) && nextStartIndex <= i) {
                        // 记录下一个 A.charAt(0) 发生的位置
                        nextStartIndex = i + j;
                    }
                    if (B.charAt(i + j) != A.charAt(j)) {
                        // 对应位置的字符不匹配
                        if (nextStartIndex > i){
                            i = nextStartIndex -1;// 之所以 -1 是因为 for 里面会 i++。暂时没想到什么好的方法把这个减 1 去掉
                        }
                        break;
                    }
                    if (j == A.length() - 1) {
                        // 成功遍历到最后一个字符，说明这是第一个子串
                        print("found: " + i);
                        return;
                    }
                }
            }
        }
        print(B.indexOf(A));


        // 如果给你两条线程，你会怎么安排这两个线程的工作？
        // 为什么要两个线程？两个线程同时寻找？

    }

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
