package cn.dozyx;


import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.dozyx.core.utli.gson.IntDefaultZeroAdapter;
import cn.dozyx.zerofate.java.GenericTest;
import cn.dozyx.zerofate.java.Person;

/**
 * 测试 java 相关代码
 *
 * @author dozeboy
 * @date 2017/12/11
 */

public class JavaTest {


    @Test
    public void testUriBuilder(){

    }

    @Test
    public void testClassLoader() throws Exception {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException();
                }
            }
        };

        Object obj = myLoader.loadClass("cn.dozyx.JavaTest").newInstance();
        print(obj.getClass());
        //加载类的类加载器和类本身确立其在 Java 虚拟机中的唯一性
        print(obj instanceof RxJavaTest);
    }

    @Test
    public  void testSubstring() {
        print("2.66".substring(0, 2));
    }

    @Test
    public  void testStringFormat() {
        print(String.format(Locale.getDefault(), "%.1f", 0.04));
    }

    @Test
    public  void testBidDecimal() {
        print(BigDecimal.valueOf(1.000).stripTrailingZeros());
        print(new BigDecimal(1.01).toString());
    }

    @Test
    public void testByteToString() {
        String str = "123";
        print(Arrays.toString(str.getBytes()));
        byte[] bytes = new byte[]{48, 66, 127};
        print(new String(bytes));
        print(Charset.defaultCharset());
    }

    @Test
    public void testArrayCopy() {
        byte[] bytes = {1, 2, 3, 4};
        byte[] dest = new byte[4];
        System.arraycopy(bytes, 1, dest, 1, 3);
        print(Arrays.toString(dest));
    }

    @Test
    public void testSerializable() throws IOException {
        SerializableUser user = new SerializableUser(0, "张三", false);
        print(user);
        ObjectOutputStream outputStream = null;
        outputStream = new ObjectOutputStream(
                new FileOutputStream("cache.txt"));
        outputStream.writeObject(user);
        // 如果 object 不是  Serializable 对象，会抛出 NotSerializableException 异常
        outputStream.close();
    }

    @Test
    public void testDeserialization() throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = null;
        inputStream = new ObjectInputStream(
                new FileInputStream("cache.txt"));
        SerializableUser user2 = (SerializableUser) inputStream.readObject();
        print(user2);
        inputStream.close();
    }

    @Test
    public void testGetInterfaces() {
        // 如果是一个 class，getInterfaces 返回的是该 class 实现的 interface；如果是 interface，返回的是 interface 继承的
        // interface
        // 注意：getInterfaces 不会返回超类实现的接口
        print(Arrays.toString(ListImpl.class.getInterfaces()));
        print(Arrays.toString(ListImpl2.class.getInterfaces()));
        print(Arrays.toString(ListImpl3.class.getInterfaces()));
    }

    @Test
    public void testThreadJoin() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                print("thread2 start");
                sleep(1);
                print("thread2 end");
            }
        });
        thread1.start();
        thread1.join();
        // 当前线程被阻塞
        print("hahaha");
        sleep(2);
    }

    private static final Object lock = new Object();

    @Test
    public void testThreadWait() throws InterruptedException {
        synchronized (lock) {
            print("wait");
            lock.wait(1000);
        }
        // 当前线程被阻塞
        print("hahaha");
        sleep(2);
    }


    @Test
    public void testLiveData() {
        // liveData 是一个具有生命周期感知的 data 持有类
        MutableLiveData<Integer> liveData = new MutableLiveData<>();
        liveData.observe(new LifecycleOwner() {
            @NonNull
            @Override
            public Lifecycle getLifecycle() {
                return new Lifecycle() {
                    @Override
                    public void addObserver(@NonNull LifecycleObserver observer) {

                    }

                    @Override
                    public void removeObserver(@NonNull LifecycleObserver observer) {

                    }

                    @NonNull
                    @Override
                    public State getCurrentState() {
                        return State.CREATED;
                    }
                };
            }
        }, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                print(integer);
            }
        });
        liveData.setValue(1);

    }

    @Test
    public void testDynamicProxy() {
        IWorker worker = (IWorker) Proxy.newProxyInstance(IWorker.class.getClassLoader(),
                new Class[]{IWorker.class},
                (proxy, method, args) -> {
                    print(proxy.getClass() + " " + method.getName());
                    print(proxy.getClass() + " " + method.getReturnType() + " "
                            + method.getGenericReturnType());
//                    method.invoke(proxy, args);
                    print("hello");
                    return "hello " + args[0];
                });
        print(worker.sayHello("temo"));
    }

    interface IWorker {
        String sayHello(String name);
    }

    @Test
    public void testIntArray() {
        int[] numbers = new int[10];
        print(Arrays.toString(numbers));
        print(duplicate(new int[]{0, 1}, 2, new int[1]));
    }

    public boolean duplicate(int numbers[], int length, int[] duplication) {
        if (numbers == null || length <= 0) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (numbers[i] < 0 || numbers[i] > length - 1) {
                return false;
            }
            while (numbers[i] != i) {
                if (numbers[numbers[i]] == numbers[i]) {
                    duplication[0] = numbers[i];
                    return true;
                } else {
                    int temp = numbers[i];
                    numbers[i] = numbers[temp];
                    numbers[temp] = temp;
                }
            }
        }
        return false;
    }

    @Test
    public void testThreadLocal() {
        ThreadLocal<Integer> threadLocal1 = new ThreadLocal<>();
        ThreadLocal<Integer> threadLocal2 = new ThreadLocal<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal1.set(1);
                threadLocal2.set(2);
                print("两个 threadLocal 是否操作的同一个变量： " + threadLocal1.get());
                print("两个 threadLocal 是否操作的同一个变量： " + threadLocal2.get());
            }
        }).start();
        sleep(1);

    }

    @Test
    public void testReentrantLock() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();// 请求锁
                print("进入 lock1");
                try {
                    print("阻塞 lock1");
                    condition.await(); // 等待，接收到 signal 或者 interrupt。condition 关联的锁会释放
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                print("执行 lock1");
                sleep(2);
                lock.unlock(); // 释放锁
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                print("执行 lock2");
                sleep(2);
                condition.signal();
                lock.unlock();
            }
        }).start();
        sleep(5);

    }


    private void sleep(int timeInSeconds) {
        try {
            Thread.sleep(timeInSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testVector() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        Stack<Integer> stack = new Stack<>();
        stack.add(1);
        stack.push(2);
        stack.remove(1);

        Deque<Integer> deque = new ArrayDeque<>();
        deque.push(1);
        deque.push(2);
        deque.push(3);
        print(deque.poll());
        print(deque.pop());
        print(deque.pop());

    }

    @Test
    public void testException() {
        try {
            new ExceptionClass().foo();
        } catch (Exception e) {

        }
    }

    private void throwException() {
        throw new NullPointerException("我是空的");
    }

    @Test
    public void testTryCatch() {
        print(tryOperation(1));
    }

    private int tryOperation(int i) {
        try {
            i++;
            throw new NullPointerException();
//            return i;
        } catch (Exception e) {
            return 3;
        } finally {
            return 4;
        }
    }

    @Test
    public void testByte() {
        Byte b = -89;
        print(b);
        int integer = b;
        print(integer);
        b = (byte) 255;
        print(b);
        b = -1;
        print(b & 0xff);
        print((int) b);
        byte[] bytes = new byte[]{-89, 0, 34, -89, 0, 34, -87, 0, 33, -87, 0, 33};
        int[] pixels = new int[bytes.length / 3];
        for (int i = 0, j = 0; i < bytes.length; i += 3) {
            byte red = bytes[i];
            byte green = bytes[i + 1];
            byte blue = bytes[i + 2];
            pixels[j++] =
                    0xFF000000 | ((blue & 0xff) << 16) | ((green & 0xff) << 8) | (red & 0xff);
        }
        print(Arrays.toString(pixels));
    }

    public static int convertByteToInt(byte data) {

        int heightBit = (int) ((data >> 4) & 0x0F);
        int lowBit = (int) (0x0F & data);
        return heightBit * 16 + lowBit;
    }

    @Test
    public void testBuffer() {
        byte[] data = new byte[]{1, 5, 2, 3, 1, 5, 2};
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        // remaining() 返回当前位置到最大值的 byte 数目
        print("remaining()" + byteBuffer.remaining());
        print(byteBuffer.get(1));
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        print("int remaining()" + intBuffer.remaining());
        print(intBuffer.get(0));
        print(byteBuffer.get());
        print("remaining()" + byteBuffer.remaining());
        byte[] bytes = new byte[byteBuffer.remaining()];
        // get(byte[]) 内部实现调用的是 get()，会导致当前位置的移动
        byteBuffer.get(bytes);
        print("remaining()" + byteBuffer.remaining());
    }

    @Test
    public void testAnnotation() {
        Class cls = AnnotationClass.class;
        print(Arrays.toString(cls.getTypeParameters()));
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            // getAnnotation 获取指定类型的注解，如果不存在则返回 null。注意：只能反射出 retain 为 runtime 的注解
            print(method.getName() + " getAnnotation for NotNull: " + method.getAnnotation(
                    NotNull.class));
            print(method.getName() + " getAnnotation for CustomAnnotation: " + method.getAnnotation(
                    CustomAnnotation.class));
            print(method.getName() + " getAnnotations: " + Arrays.toString(
                    method.getAnnotations()));
            print(method.getName() + " getDeclaredAnnotations: " + Arrays.toString(
                    method.getDeclaredAnnotations()));
            // getParameterAnnotations 为每个参数返回一个注解数组，如果某个参数没有注解，则返回长度为 0 的数组
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (Annotation[] parameterAnnotation : parameterAnnotations) {
                print(method.getName() + " getParameterAnnotations: " + Arrays.toString(
                        parameterAnnotation));
            }
        }
    }

    private static class AnnotationClass implements AnnotationSuper {
        @Nullable
        private String text;

        @NotNull
        @Override
        public String foo1() {
            return "";
        }

        @CustomAnnotation("hello")
        private String foo2() {
            return "";
        }

        @CustomAnnotation("hello")
        private String foo3(String text1, @CustomAnnotation String text2) {
            return "";
        }
    }

    private interface AnnotationSuper {
        @CustomAnnotation
        String foo1();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
    private @interface CustomAnnotation {
        String value() default "";
    }

    @Test
    public void testReflect() {
        Class cls = ReflectClass.class;
        print(Arrays.toString(cls.getTypeParameters()));
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            // getGenericParameterTypes 返回参数的类型。如果某个参数的类型是泛型，则返回的 Type
            // 对象必须能准确地反射出代码中使用的实际类型（关于泛型这部分不是很懂，实际打印出来是显示的就是表示泛型的字母）
            print(method.getName() + " getGenericParameterTypes: " + Arrays.toString(
                    method.getGenericParameterTypes()));
            print(method.getName() + " getReturnType: " + method.getReturnType());
            print(method.getName() + " getGenericReturnType: " + method.getGenericReturnType());
        }
        try {
            ReflectClass<Object> object = new ReflectClass<>();
            Field sBoolean = cls.getDeclaredField("sBoolean");
            sBoolean.setAccessible(true);
            print(sBoolean.isAccessible() + "");
            sBoolean.setBoolean(object, false);
            print(sBoolean.isAccessible() + "");
            print(sBoolean.getBoolean(object) + "");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static class ReflectClass<T> {
        private T data;
        private static boolean sBoolean = true;

        private <K> K foo1(K type) {
            return type;
        }

        private <K> String foo2(K type) {
            return "string";
        }

        private String foo3(String text) {
            return text;
        }

    }

    public void testFoo() {

        print(-123 / 10);
    }

    @Test
    public void testGeneric() {
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<Integer> integers = new ArrayList<>();
        System.out.println(strings.getClass() == integers.getClass());
        print(Arrays.toString(strings.getClass().getTypeParameters()));
        ArrayList<? extends Number> list = new ArrayList<Integer>();
        list.get(0).byteValue();
    }


    @Test
    public void testThreadStack() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : trace) {
            print(stackTraceElement.toString());
        }
    }

    private static void print(Object msg) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        DateFormat format = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
        System.out.println(
                format.format(Calendar.getInstance().getTime())
                        + " -> " /*+ stackTrace[3].getMethodName()*/ + " "
                        + msg.toString());
    }

    @Test
    public void testShiftOperator() {
        // >> 算术右移，整体右移，包括符号位，原符号位正数补0，负数补1。右移一位相当于处于2
        // >>> 逻辑右移（无符号右移），整体右移，包括符号位，高位补 0
        // << 算术左移，低位补0，可能导致负数变为正数，或相反，但这些情况意味着发生了溢出。左移一位相当于乘以2
        // 没有逻辑左移，因为它的结果与算术左移一致。 https://www.quora
        // .com/Why-is-there-no-unsigned-left-shift-operator-in-Java
        System.out.println(Integer.toHexString(2));
        System.out.println(Integer.toHexString(-2));
        System.out.println(Integer.toHexString(2 >> 1));
        System.out.println(Integer.toHexString(-2 >> 1));
        System.out.println(Integer.toHexString(2 >>> 1));
        System.out.println(Integer.toHexString(-2 >>> 1));
        System.out.println(Integer.toHexString(2 << 1));
        System.out.println(Integer.toHexString(-2 << 1));
        System.out.println(Integer.toHexString(0x7fffffff << 1));
//        System.out.println(Integer.toHexString(0x8f000000 << 1));
        System.out.println(Integer.MIN_VALUE);
    }

    @Test
    public void testType() {

        System.out.println(Boolean.TYPE);
        System.out.println(Integer.TYPE == Integer.class);
        System.out.println(Integer.class instanceof Class);
    }

    public void testDate() {
        String payTime = "2019-06-01";
        int year = Integer.parseInt(payTime.substring(0, 4));
        int month = Integer.parseInt(payTime.substring(5, 7));
        int day = Integer.parseInt(payTime.substring(8, 10));
        Date date = new Date(year, month, day);
        System.out.println(date.getTime());
        String payTime1 = "2019-05-31";
        int year1 = Integer.parseInt(payTime1.substring(0, 4));
        int month1 = Integer.parseInt(payTime1.substring(5, 7));
        int day1 = Integer.parseInt(payTime1.substring(8, 10));
        Date date1 = new Date(year1, month1, day1);
        System.out.println(date1.getTime());
    }

    public Random rnd = new Random();

    public long random(long n) {
        //(0 ~ Integer.MAX_VALUE)  =  Integer.MAX_VALUE / 2;

        return Math.abs(rnd.nextLong()) % n;
    }

    public void randomTest() {
        for (int i = 0; i < 100; i++) {
            System.out.printf(random(2) + " ");
        }
    }


    public void testFianl() {
        Integer integer = new Integer(1);
        System.out.println(integer);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(integer);
            }
        }).start();
    }


    @TargetApi(Build.VERSION_CODES.O)
    public void testLocalDateTime() {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM")));
    }

    public String formatComma(String amount) {
        double doubleAmount;
        try {
            doubleAmount = Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            return amount;
        }
        return new DecimalFormat("#,##0.00").format(doubleAmount);
    }

    public void testStringToHex() {
        String str = "A20670100203020180000250323600064710300633522704008C7B641250500881025007";
        String interger =
                "413230363730313030323033303230313830303030323530333233363030303634373130333030363333353232373034303038433742363431323530353030383831303235303037";
        System.out.println(new BigInteger(str, 16));
        System.out.println(String.format("%040x", new BigInteger(1, str.getBytes())));
        System.out.println(String.format("%x", new BigInteger(1, str.getBytes())));
        System.out.println(String.format("%x", 10));
        System.out.println(new BigInteger(1, str.getBytes()));
        System.out.println(String.format("%040x", 10));
        System.out.println(String.format("%03d", "11111".length()));
    }

    public <T> boolean isAssignableFrom(T type) {
        return String.class.isAssignableFrom(type.getClass());
    }

    public void parseStringDate() throws ParseException {
        String date = "2018-10-08";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(format.parse(date));
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));

        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-(\\d{2})");
        Matcher matcher = pattern.matcher(date);
        if (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }

    public void testTime() {
        Calendar now = Calendar.getInstance();
        System.out.println(formatDate(now.getTime()));

        now.add(Calendar.WEEK_OF_YEAR, -1);
        System.out.println(formatDate(now.getTime()));

        now = Calendar.getInstance();
        now.set(Calendar.WEEK_OF_MONTH, 1);
        now.add(Calendar.WEEK_OF_MONTH, -1);
        System.out.println(formatDate(now.getTime()));

        now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH, 3);
        now.add(Calendar.WEEK_OF_MONTH, -1);
        System.out.println(formatDate(now.getTime()));

        now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_YEAR, 3);
        now.add(Calendar.WEEK_OF_MONTH, -1);
        System.out.println(formatDate(now.getTime()));

        now = Calendar.getInstance();
        now.add(Calendar.MONTH, -1);
        System.out.println(formatDate(now.getTime()));

    }

    public String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date);
    }


    @Test
    public void foo() {
        Date date = new Date();
        date.setTime(1571901056000L);
        print(formatDate(date));
    }

    public static String parse(String fen) {
        return new DecimalFormat("##0.00").format(Double.valueOf(fen) / 100.0);
    }

    public class A {
        public String str;
        public int a = 10;
    }

    @Test
    public void testThreadPool() {
        ExecutorService executorService = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3));
        // 最多提交任务数：maximumPoolSize + deque 大小
        for (int i = 0; i < 13; i++) {
            int flag = i;
            // 先执行了 core 任务，后续任务放入 deque，deque 后执行新加任务，有空闲线程后再执行 deque 任务
            executorService.execute(() -> {
                print(" & i == " + flag + " & thread == " + Thread.currentThread());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        sleep(5);
    }

    public String format(String num, int reserved, int divide) {
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

    public String formatHundredth(String str) {
        if (str == null || str.length() == 0) {
            return "0.00";
        }
        Double tempInput = Double.parseDouble(str);
        BigDecimal b1 = new BigDecimal(tempInput.toString());
        BigDecimal b2 = new BigDecimal("100");
        DecimalFormat decimalFormat = new DecimalFormat("#");
        return decimalFormat.format(b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    @Test
    public void testGsonIncorrect() {
//        String json = "{ \"phones\": [\"1111\"], \"data\": {\"address\":\"11\"} }";
        String json = "{ \"phones\": \"1111\", \"data\": {\"address\":\"11\"} }";// List 传成字符串
//        String json = "{ \"phones\": [\"1111\"], \"data\": [] }";// Object 传成 List
        Gson gson = new GsonBuilder().registerTypeAdapter(Data.class,
                new JsonDeserializer<Data>() {
                    @Override
                    public Data deserialize(JsonElement json, Type typeOfT,
                            JsonDeserializationContext context) throws JsonParseException {
                        print("deserialize " + json.toString() + " " + typeOfT.getTypeName());
                        if (json.isJsonObject()) {
                            print("deserialize " + json.toString());
                            return new Gson().fromJson(json, typeOfT);
                        }
                        return null;
                    }
                }).registerTypeHierarchyAdapter(List.class,
                (JsonDeserializer<List>) (json1, typeOfT, context) -> {
                    print("deserialize " + json1);
                    if (json1.isJsonArray()) {
                        return new Gson().fromJson(json1, typeOfT);
                    }
                    return Collections.EMPTY_LIST;
                }).registerTypeHierarchyAdapter(String.class, new JsonDeserializer<String>() {
            @Override
            public String deserialize(JsonElement json, Type typeOfT,
                    JsonDeserializationContext context) throws JsonParseException {
                if (json.isJsonPrimitive()) {
                    return json.getAsString();
                }
                return "hello";
            }
        }).create();
        Home user = gson.fromJson(json, Home.class);
        print(user);
    }

    private static class Home {
        private List<String> phones;
        private Data data;

        @Override
        public String toString() {
            return "Home{" +
                    "phones=" + phones +
                    ", data=" + data +
                    '}';
        }
    }

    private static class Data {
        private String address;

        @Override
        public String toString() {
            return "Data{" +
                    "address='" + address + '\'' +
                    '}';
        }
    }

    @Test
    public void testGson() {
        Gson customGson = new GsonBuilder().registerTypeAdapter(Integer.TYPE,
                new IntDefaultZeroAdapter()).create();
        String personJsonWithoutAge = "{\"name\":\"张三\"}";
        Person person = customGson.fromJson(personJsonWithoutAge, Person.class);
        System.out.println(person);
        String personJsonWrongType = "{\"name\":\"张三\",\"age\":\"\"}";
        person = customGson.fromJson(personJsonWrongType, Person.class);
        System.out.println(person);
        cn.dozyx.zerofate.java.User user = new cn.dozyx.zerofate.java.User();
        user.setName("张三");
        System.out.println("缺少 field：" + new Gson().toJson(user));

        user.setPhone("110");
        System.out.println("缺少对象 field：" + new Gson().toJson(user));
        cn.dozyx.zerofate.java.User.Address address = new cn.dozyx.zerofate.java.User.Address();

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
        System.out.println("jsonStr 包含多余字段：" + new Gson().fromJson(jsonStr1,
                cn.dozyx.zerofate.java.User.class));
        String jsonStr2 = "{\n"
                + "    \"name\": \"张三\",\n"
                + "    \"address\": {\n"
                + "        \"province\": \"江苏\",\n"
                + "        \"city\": \"常州\"\n"
                + "    }\n"
                + "}";
        System.out.println(
                "jsonStr 缺少字段：" + new Gson().fromJson(jsonStr2, cn.dozyx.zerofate.java.User.class));

        String jsonStr3 = "{\"data\": {\n"
                + "    \"name\": \"张三\",\n"
                + "    \"phone\": \"110\",\n"
                + "    \"address\": {\n"
                + "        \"province\": \"江苏\",\n"
                + "        \"city\": \"常州\"\n"
                + "    }\n"
                + "}}";
        System.out.println("jsonStr 增加一层data：" + new Gson().fromJson(jsonStr3,
                cn.dozyx.zerofate.java.User.class));

        String jsonStr4 = "{\"data\": {\n"
                + "    \"name\": \"张三\",\n"
                + "    \"phone\": \"110\",\n"
                + "    \"address\": \"\"\n"
                + "}}";
        System.out.println("对象类型错误：" + new Gson().fromJson(jsonStr4, SerializableUser.class));
    }

    public class G2<S> extends GenericTest<S> {

    }

    public byte[] HexString2bytes(String hexString) {
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

    public byte toByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public void testCalculate() {
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

    public String removeSecond(String date) {

        final int colonCount = date.length() - date.replace(":", "").length();
        if (colonCount > 1) {
            return date.substring(0, date.lastIndexOf(":"));
        }
        return date;
    }

    public boolean isLetterOrNumberWithLengthLimit(String text, int min, int max) {
        String reg = "^[a-zA-Z0-9]{" + min + "," + max + "}$";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(text);
        return mat.matches();
    }

    public static void main(String[] args) {
        int i = Integer.MAX_VALUE + 1;
        print(i);
        print(Integer.MAX_VALUE);
    }

}
