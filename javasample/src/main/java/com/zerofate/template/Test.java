package com.zerofate.template;


import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 测试 java 相关代码
 * 约定：将测试代码放在单独方法中，main 中只执行该方法，这样就可以在测试下一个项目时保留上一次的代码
 *
 * @author dozeboy
 * @date 2017/12/11
 */

public class Test {
    public static void main(String[] args) throws ParseException {
        List<Fruit> fruits = new ArrayList<>();
        for (Fruit fruit : fruits) {
            System.out.println(fruit.getName());
        }
        String str = "0";
        String[] strings = str.split(",");
        for (String s:strings){
            System.out.println(s);
        }
    }


    public static class Student extends User {
        public static final String NAME = "jijiji";

        static {
            System.out.println("static");
        }
    }

    private void foo() {
        System.out.print(new A().str);
    }

    private class A {
        private String str;
    }

    private static void testThreadPool() {
        ExecutorService executorService = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());
        for (int i = 0; i < 10; i++) {
            int num = i;
            executorService.execute(() -> {

                System.out.println(new Date() + " & i == " + num + " & thread == "
                        + Thread.currentThread());
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static String format(String num, int reserved, int divide) {
        BigDecimal b1 = new BigDecimal(num);
        BigDecimal b2 = new BigDecimal(divide);
        String format;
        if (reserved == 0) {
            format = "#";
        } else {
            StringBuilder builder = new StringBuilder("#.#");
            for (int i = 1; i < reserved; i++) {
                builder.append("#");
            }
            format = builder.toString();
        }
        return new DecimalFormat(format).format(
                b1.divide(b2, reserved, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public static String formatHundredth(String str) {
        if (str == null || str.length() == 0) {
            return "0.00";
        }
        Double tempInput = Double.parseDouble(str);
        BigDecimal b1 = new BigDecimal(tempInput.toString());
        BigDecimal b2 = new BigDecimal("100");
        DecimalFormat decimalFormat = new DecimalFormat("#");
        return decimalFormat.format(b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    private static void testGson() {
        User user = new User();
        user.setName("张三");
        System.out.println("缺少 field：" + new Gson().toJson(user));

        user.setPhone("110");
        System.out.println("缺少对象 field：" + new Gson().toJson(user));
        User.Address address = new User.Address();

        address.setProvince("江苏");
        address.setCity("常州");
        user.setAddress(address);
        System.out.println("完整：" + new Gson().toJson(user));

        String jsonStr1 = "{\n"
                + "    \"name\": \"张三\",\n"
                + "    \"phone\": \"110\",\n"
                + "    \"age\": 20,\n"
                + "    \"address\": {\n"
                + "        \"province\": \"江苏\",\n"
                + "        \"city\": \"常州\"\n"
                + "    }\n"
                + "}";
        System.out.println("jsonStr 包含多余字段：" + new Gson().fromJson(jsonStr1, User.class));
        String jsonStr2 = "{\n"
                + "    \"name\": \"张三\",\n"
                + "    \"address\": {\n"
                + "        \"province\": \"江苏\",\n"
                + "        \"city\": \"常州\"\n"
                + "    }\n"
                + "}";
        System.out.println("jsonStr 缺少字段：" + new Gson().fromJson(jsonStr2, User.class));

        String jsonStr3 = "{\"data\": {\n"
                + "    \"name\": \"张三\",\n"
                + "    \"phone\": \"110\",\n"
                + "    \"address\": {\n"
                + "        \"province\": \"江苏\",\n"
                + "        \"city\": \"常州\"\n"
                + "    }\n"
                + "}}";
        System.out.println("jsonStr 增加一层data：" + new Gson().fromJson(jsonStr3, User.class));
    }

    public static class G2<S> extends GenericTest<S> {

    }

    public static byte[] HexString2bytes(String hexString) {
        hexString = hexString.toUpperCase();
        byte[] b = new byte[hexString.length() / 2];
        char[] hexStringByte = hexString.toCharArray();
        for (int i = 0; i < hexString.length() / 2; i++) {
            byte byteTemp = (byte) ((toByte(hexStringByte[i * 2]) << 4) | toByte(
                    hexStringByte[i * 2 + 1]));
            b[i] = byteTemp;
        }
        return b;
    }

    private static byte toByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    private static class Person {
        String name;
        String age;
        Phone phone;

        public Person() {
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getAge() {
            return age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        static class Phone {
            String num;
            String type;
        }

    }

    private static void testCalculate() {
        // float 计算
        BigDecimal bigDecimal1 = new BigDecimal(0.58f);
        BigDecimal bigDecimal2 = new BigDecimal(0.01f);
        System.out.println(bigDecimal1 + "\n" + bigDecimal2 + "\n" + bigDecimal1.add(bigDecimal2));
        System.out.println((int) ((0.58f + 0.01f) * 100));

        // double 计算
        BigDecimal bigDecimal3 = new BigDecimal(0.58);
        BigDecimal bigDecimal4 = new BigDecimal(0.01);
        System.out.println(bigDecimal3 + "\n" + bigDecimal4 + "\n" + bigDecimal3.add(bigDecimal4));
        System.out.println((int) ((0.58 + 0.01) * 100));
    }

    public static String removeSecond(String date) {

        final int colonCount = date.length() - date.replace(":", "").length();
        if (colonCount > 1) {
            return date.substring(0, date.lastIndexOf(":"));
        }
        return date;
    }

    public static boolean isLetterOrNumberWithLengthLimit(String text, int min, int max) {
        String reg = "^[a-zA-Z0-9]{" + min + "," + max + "}$";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(text);
        return mat.matches();
    }

}
