package cn.dozyx;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create by dozyx on 2019/12/17
 **/
public class AlgorithmTest {

    volatile boolean isPrint1 = true;
    volatile boolean isPrint2 = false;
    volatile boolean isPrint3 = false;
    volatile int maxCount = 2;
    volatile AtomicInteger count = new AtomicInteger();

    @Test
    public void printNumberMultiThread() {
        // 三个线程依次打印 1、2、3
        new Thread(() -> {
            while (count.get() != maxCount) {
                if (isPrint1) {
                    isPrint1 = false;
                    print(1);
                    isPrint2 = true;

                }
            }
        }).start();
        new Thread(() -> {
            while (count.get() != maxCount) {
                if (isPrint2) {
                    isPrint2 = false;
                    print(2);
                    isPrint3 = true;
                }
            }
        }).start();
        new Thread(() -> {
            while (count.get() != maxCount) {
                if (isPrint3) {
                    isPrint3 = false;
                    print(3);
                    count.incrementAndGet();
                    isPrint1 = true;
                }
            }
        }).start();
        sleep(1);
    }

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
                        if (nextStartIndex > i) {
                            i = nextStartIndex - 1;// 之所以 -1 是因为 for 里面会 i++。暂时没想到什么好的方法把这个减 1 去掉
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

    private void sleep(int timeInSeconds) {
        try {
            Thread.sleep(timeInSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
