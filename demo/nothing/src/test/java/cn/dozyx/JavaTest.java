package cn.dozyx;


import static cn.dozyx.LogUtils.print;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.blankj.utilcode.util.FileIOUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.dozyx.core.log.Sampler;
import cn.dozyx.core.utli.gson.IntDefaultZeroAdapter;
import cn.dozyx.zerofate.java.GenericTest;
import cn.dozyx.zerofate.java.Student1;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;
import okhttp3.HttpUrl;

/**
 * 测试 java 相关代码
 * 验证同一个技术点的多项内容，分成 testXxxCaseX() 来写，不要通过注释
 *
 * @author dozeboy
 * @date 2017/12/11
 */

public class JavaTest {

    @Test
    public void testCountDownLatch() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        print("testCountDownLatch start");
        new Thread(() -> {
            sleep(5);
            latch.countDown();
        }).start();
        print("latch count111: " + latch.getCount());
        latch.await(1, TimeUnit.SECONDS);
        latch.countDown();
        print("latch count222: " + latch.getCount());
        latch.await(2, TimeUnit.SECONDS);// countDown() 执行后再调用 await 会立即结束
        print("latch count333: " + latch.getCount());
        latch.await();
        print("latch count444: " + latch.getCount());
    }

    @Test
    public void testNaN(){
//        int a = 1 / 0;// ArithmeticException
        print(new Float(0.0 / 0.0).isNaN());// true
        print(new Float(1.0 / 0.0).isNaN());// false
    }

    @Test
    public void testSpecialChar(){
        String title = "\uD835\uDC73\uD835\uDC86\uD835\uDC95'\uD835\uDC94 \uD835\uDC6E\uD835\uDC86\uD835\uDC95 \uD835\uDC73\uD835\uDC90\uD835\uDC96\uD835\uDC85 - \uD835\uDC6A\uD835\uDC89\uD835\uDC82-\uD835\uDC6A\uD835\uDC89\uD835\uDC82 (\uD835\uDC69\uD835\uDC82\uD835\uDC8D\uD835\uDC8D\uD835\uDC93\uD835\uDC90\uD835\uDC90\uD835\uDC8E - \uD835\uDC73\uD835\uDC82\uD835\uDC95\uD835\uDC8A\uD835\uDC8F \uD835\uDC7A\uD835\uDC95\uD835\uDC9A\uD835\uDC8D\uD835\uDC86)";
//        String title = "\uD835\uDC73\uD835\uDC86\uD835\uDC95'";
//        String title = "中国'";
//        String title = "😄'";
        print(title);
        print(title.length());
        print(title.codePointCount(0, title.length()));
//        print(title.substring(0, 2));
//        print(title.substring(0, 3));
    }

    @Test
    public void testNumberFormat() {
        print(String.format(new Locale("ar", "AR"), "number: %d", 1));
        print(String.format(Locale.US, "number: %d", 1));
    }

    @Test
    public void testCrashStack() {
        // 在构造函数里发生异常
        CrashConstructor crashConstructor = new CrashConstructor(); // 堆栈错误会显示 <init>
        crashConstructor.foo();
    }

    private static class CrashConstructor {
        public CrashConstructor() {
//            String str = null;
//            str.length();
            init();
        }

        private void init() {
            String str = null;
            str.length();
        }

        public void foo() {
            print("foo execute");
        }
    }

    @Test
    public void testAndOr() {
        print(true && false || false && true);
    }

    @Test
    public void testEnum() {
        print(Operation.valueOf("PLUS"));// 返回字符串对应的枚举常量，但 print 输出会调用它的 toString 方法
        print(Operation.PLUS.name());
        print(Operation.PLUS.ordinal());
        print(Operation.PLUS);
        Operation operation = Operation.PLUS;
        switch (operation){

        }
    }

    private enum Operation {
        PLUS {
            @Override
            public double apply(double x, double y) {
                return x + y;
            }
        },
        Multi {
            @Override
            public double apply(double x, double y) {
                return x * y;
            }
        };

        public abstract double apply(double x, double y);

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " XXX";
        }
    }

    @Test
    public void testInitialize() {
        Person person = new Student();
//        Student person = new Student();
//        person.sayName();
        print(person.name);
        print(((Student) person).name);
    }

    private static class Person {
        public String name = "Person";

        public void sayName() {
            print(name);
        }
    }


    private static class Student extends JavaTest.Person {
        public String name = "Student";
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        ClassB classB = new ClassB();
        classB.name = "哈哈";
        classB.age = 18;
        Object clone = classB.clone();
        print(clone == classB);
        print(clone);
    }

    @Test
    public void testClass() {
        printClass(new ArrayList<>());
    }

    private void printClass(List<String> list) {
        print(list.getClass());
    }

    @Test
    public void testTryWithResource() throws Exception {
        try (MyCloseable closeable = new MyCloseable()) {
            closeable.crash();
        } /*catch(FileNotFoundException e){

        }*/
        // 如果自动 close 抛了异常，是不会被 catch 的
    }

    @Test
    public void testTryFinally() throws Exception {
        MyCloseable closeable = new MyCloseable();
        try {
            closeable.crash();
        } catch (Exception e) {
            print("catch");
        } finally {
            closeable.close();// 这里抛出的异常不会被 catch
        }
    }

    private static class MyCloseable implements AutoCloseable {

        public void crash() {
            throw new IllegalStateException("crash invoke");
        }

        @Override
        public void close() throws Exception {
            throw new RuntimeException("crash close");
        }
    }

    @Test
    public void testTryCatchReturn() {
        int num = tryCatchReturn(1, 2);
        print("num: " + num);
    }

    private int tryCatchReturn(int add1, int add2) {
        try {
            print("try");
            return add(add1, add2);
        } catch (Exception e) {
            print("exception");
            return -1;
        } finally {
            print("finally");
            // finally 块中添加 return 会有告警
            // 最终使用了 finally 的 return，try 中的运算会执行，但没有作为 return 的值
            return getFinally();
        }
    }

    private int add(int add1, int add2) {
        print("add");
        return add1 + add2;
    }

    private int getFinally() {
        print("getFinally");
        return -2;
    }

    @Test
    public void testStringBuilder() {
        String str = "123";
        StringBuilder builder = new StringBuilder(str);
//        print(builder.delete(0, builder.length() - 1));
        print(builder.append("456", 0, 2));
        print(builder.append("456", 2, 3));
//        builder.setLength(0);
        print(builder);
    }

    @Test
    public void testStatic() {
        Singleton instance = Singleton.INSTANCE;
        print(instance.a);
    }

    private static class Singleton {
        private static final Singleton INSTANCE = new Singleton();
        private static final MyObject CONSTANT = new MyObject();
        private MyObject a = CONSTANT;

        public Singleton() {
            print("a: " + a);
        }
    }

    private static class MyObject {
        public MyObject() {
            print("init MyObject");
        }

        public void testStatic() {
            Singleton.INSTANCE.foo();
        }

        private static class Singleton {
            public static final Singleton INSTANCE = new Singleton();
            public static final String CONSTANT = "static";

            public Singleton() {
                print("init: " + CONSTANT);
            }

            public void foo() {
                print(CONSTANT);
            }
        }
    }

    @Test
    public void testCeil() {
        print(Math.ceil(0.1));
        print(Math.ceil(0.5));
        print(Math.ceil(0.6));
    }

    @Test
    public void testWhile() {
        int i = 0;
        while (i < 0) {
            print("i: " + i);
            i++;
        }

        int j = 0;
        do {
            print("j: " + j);
            j++;
        } while (j < 0);
    }

    @Test
    public void testSize() {
        print(Integer.MAX_VALUE / 1024 / 1024F);
    }

    @Test
    public void testPrintStackTraceInUnitTest() {
        try {
            String origin = null;
            origin.length();
        } catch (Exception e) {
            e.printStackTrace();
            // 会把错误打印出来，但也会继续往下执行，也就是不会影响单元测试结果
        }
        print("pass success");
    }

    @Test
    public void testStringDecode() throws UnsupportedEncodingException {
        String origin = "\\302\\267";
        print(origin);
        String asciiString = "\\x27";
        print(URLDecoder.decode(asciiString, "utf-16"));
    }

    @Test
    public void testHttpUrlConnection() throws IOException {
        URL url = new URL("https://www.youtube.com/results?pbj=1&search_query=Youtube");
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
    }

    @Test
    public void testSample() {
        int hitCount = 0;
        int times = 100000;
        for (int i = 0; i < times; i++) {
            Sampler sampler = new Sampler();
            if (sampler.enable()) {
                hitCount++;
            }
        }
        print("1/32: " + 1 / 32F);
        print("采样率: " + hitCount / (float) times);
    }

    public void testHttpUrl() {
        String url = "https://www.youtube.com/home";
        HttpUrl httpUrl = HttpUrl.parse(url).newBuilder().host("m.youtube.comm").build();
        print(httpUrl.toString());
    }

    @Test
    public void testJavassist() throws NotFoundException, CannotCompileException, IOException {
        ClassPool classPool = ClassPool.getDefault();
//        classPool.appendClassPath("class/");
//        classPool.appendClassPath("~/Desktop/Java/");
        String classname = "cn.dozyx.JavassistClass";
        URL url = classPool.find(classname);
        print(url);
        // 修改后输出的 class 在 build/intermediates/javac/baiduDebugUnitTest/classes 目录
        // 不过上面的目录似乎没办法直接写入或者会被 IDE 覆盖，所以在另一个目录 src/test/java/cn/dozyx/class/ 生成了 class

        CtClass ctClass = classPool.get(classname);
        CtConstructor[] constructors = ctClass.getDeclaredConstructors();
        for (CtConstructor constructor : constructors) {
            print("遍历: " + constructor);
        }
//        ctClass.setSuperclass(classPool.get("cn.dozyx.JavaTest"));
        CtConstructor constructor = ctClass.getDeclaredConstructor(
                classPool.get(new String[]{"java.lang.String"}));
        print(constructor);
//        constructor.insertBefore("sayName();");// 在方法开头插入
//        constructor.insertAfter("sayName();");// 在方法末尾插入
//        constructor.insertBefore("$1 = \"modifyName\";");// 修改局部变量
        int startPos = constructor.getMethodInfo().getLineNumber(0);
        print("start pos: " + startPos);
//        int line = constructor.insertAt(startPos + 2, "$3 = \"fieldName\";");// 第一个参数 lineNum
//        表示的是 class 的行数，似乎没办法直接对方法内声明的局部变量进行操作
        int line = constructor.insertAt(startPos + 2, "$2 = \"fieldName\";");//
//        print("insert line: " + line);

//        constructor.setBody(null);
//        ctClass.writeFile();// 好像直接调用这个方法会被 IDE 的覆盖？
        ctClass.writeFile("src/test/java/cn/dozyx/class/");
    }

    @Test
    public void testClassInit() {
        String clz = InitClass1.class.getSimpleName();// 不会导致 static 内容初始化
//        new InitClass1();
        print("testClassInit");
    }

    @Test
    public void testExtends() {

    }

    private static class InitClass1 {
        public static InitClass2 clz2 = new InitClass2();

        static {
            print("InitClass1 static");
        }

        private InitClass1() {
            print("InitClass2 constructor");
        }
    }

    private static class InitClass2 {
        static {
            print("InitClass2 static");
        }

        private InitClass2() {
            print("InitClass2 constructor");
        }
    }

    @Test
    public void testOutOfBoundsException() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
//        list.get(-1);

        int[] array = new int[2];
        int i = array[-1];
    }

    @Test
    public void testIterator() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == 2) {
//                list.remove(i);// 这种移除虽然没有异常，但是其实逻辑已经有问题，因为移除之后，后面的 index 会改变，导致下一个元素没有参与到遍历中
            }
        }
        for (Integer integer : list) {
            if (integer == 2) {
                //迭代过程中移除，抛出异常
//                list.remove(integer);
            }
        }
        sleep(1);
    }

    @Test
    public void testFile() {
        File file = new File("1");
        print(Long.MAX_VALUE);
        print(Integer.MAX_VALUE);
        print("9223372036854775807".substring(0, 13));
    }

    @Test
    public void testFilePath() {
//        File file = new File("../../test.txt");
        File file = new File(".", "test.txt");
        print(file.getPath());// 可能是一个相对的路径
        print(file.getAbsolutePath());// .. 完整路径，.. 会被保留
        try {
            print(file.getCanonicalPath());// 返回的是标准的路径，.. 之类的符号会被替换为完整路径
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNull() {
        Object nullObj = null;
        System.out.println("this is " + nullObj);
    }

    @Test
    public void testForLoop() {
        ArrayList<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(2);
        data.add(3);
        data.add(4);
        data.add(5);
        boolean hasRemoved = false;
        for (int i = 0; i < data.size(); i++) {
            print("for " + i);
            if (!hasRemoved) {
                data.remove(1);
                hasRemoved = true;
            }
//            print(data.get(i));
//            if (data.get(i) == 1){
//                data.remove(i);
//            }
        }
        print("size: " + data.size());
        for (Integer i : data) {
            if (i == 2) {
                data.remove(i);
            }
        }
        ArrayList<Object> list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            print(list);
        }
    }

    @Test
    public void testConstantPool() {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern());
        System.out.println(str1.intern() == str1);
        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern());
        System.out.println(str2.intern() == str2);
    }

    @Test
    public void testTableForSize() {
//        print(tableSizeFor(0));
        print(1 << 31);
        print(0x80000000);
    }

    private static final int MAXIMUM_CAPACITY = 1 << 30;

    private static int tableSizeFor(int cap) {
        // cap 是初始容量
        // 返回值作为下一次 resize 的阈值
        int n = cap;
        print(Integer.toBinaryString(n));
        n |= n >>> 1;
        print(Integer.toBinaryString(n));
        n |= n >>> 2;
        print(Integer.toBinaryString(n));
        n |= n >>> 4;
        print(Integer.toBinaryString(n));
        n |= n >>> 8;
        print(Integer.toBinaryString(n));
        n |= n >>> 16;
        print(Integer.toBinaryString(n));
        // MAXIMUM_CAPACITY 等于 1 << 30
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    @Test
    public void testGc() {
        Object object = new Object();
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        WeakReference<Object> reference = new WeakReference<Object>(object, queue);
        object = null;
        System.gc();
        sleep(1);
        print(queue.poll());
    }

    @Test
    public void testConcurrentModificationException() {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            map.put(i, i);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
                    while (iterator.hasNext()) {
//                        print(iterator.next());
                        // 如果 iterator 记录的元素修改次数与新的修改次数不一致，抛出异常
                        Integer key = iterator.next().getKey();
                        map.remove(key);
                        print("remove " + key);
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (int i = 0; i < 1000; i++) {
//                        map.put(1000 + i, 1000 + i);
                    }
                }
            }
        }).start();
        sleep(1);
    }

    @Test
    public void testStack() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        while (!stack.empty()) {
            print(stack.pop());
        }
    }

    @Test
    public void testListNode() {
        ListNode node = new ListNode(1);
        node.next = new ListNode(2);
        node.next.next = new ListNode(3);
        printListNode(node);

        // 反转
        Stack<ListNode> stack = new Stack<>();
        ListNode current = node;
        do {
            stack.push(current);
            current.next = null;
            current = current.next;
        } while (current != null);

        ListNode newHead = stack.pop();
        current = newHead;
        while (!stack.empty()) {
            current.next = stack.pop();
            current = current.next;
        }
        printListNode(newHead);
    }

    private void printListNode(ListNode node) {
        ListNode current = node;
        while (current != null) {
            print(current.val);
            current = current.next;
        }
    }

    private class ListNode {
        private ListNode next;
        private int val;

        public ListNode(int val) {
            this.val = val;
        }
    }

    private static final Object LOCK = new Object();

    @Test
    public void testConcurrentHashMap() throws InterruptedException {
        Executor executor = Executors.newCachedThreadPool();
        final CountDownLatch latch = new CountDownLatch(10000);
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, AtomicInteger> map2 = new ConcurrentHashMap<>();
        map.put("key", 1);
        map2.put("key2", new AtomicInteger(1));
        long start = System.currentTimeMillis();

//        print("time1");
        for (int i = 0; i < 10000; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    // 只保证了单个方法调用是线程安全的
                    /*try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    synchronized (LOCK) {
                        // 采用 synchronized 确保多个操作的原子性
                        map.put("key", map.get("key") + 1);
                    }
//                    map2.get("key2").getAndIncrement();// 采用 CAS 确保多个操作的原子性
                    latch.countDown();
                }
            });
        }
        latch.await();
        // 第一次测试时间（操作是直接 new Thread 执行）：采用 synchronized 执行时间大概是 600ms（本机环境），CAS 大概 500
        // ms，但偶尔也有接近
        // 600ms 的。
        // 第二次测试时间（使用线程池）：synchronized 大概 40+ms，CAS 大概是 30+ms。（线程池的提升比我想象的大得多。。。）
//        print("time2");
        // 测试过程中发现一个问题：没有使用 CountDownLatch，子线程任务也能完整执行。进一步观察发现，猜测可能是 execute 方法本身的执行导致主线程产生了耗时。
        //     如果增加 task 的执行时间，那么就出现了部分 task 没有顺利执行问题，需要使用 CountDownLatch 来确保每个 task 都执行完才结束主线程。
        print("elapse time " + (System.currentTimeMillis() - start));
        print(map.get("key"));
        print(map2.get("key2"));
    }

    @Test
    public void testMainThread() {
        Executors.newFixedThreadPool(1).execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1);
                    print("execute thread");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    public void testCustomString() {
        String str = "111";
        print(str.length());
    }

    @Test
    public void testString() {
        String maxStr =
                "1111111234567890123456123456789012345678901234567890123456789012345678901234567890123451234567890123456789012345678901234567890123456789012345678901234512345678901234567890123456789012345678901234567890123456789012345123456789012345678901234567890123456789012345678901234567890123451234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901212345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789014567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123478901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234578901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234545678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012342345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890v123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890v12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
        // 字面量最大长度 65534
        // 字面量存在于栈内存，受限于Java代码编译完成的字节码文件CONSTANT_Utf8_info结构中的u2
        // length。u2代表两个字节，即长度最大为16位二进制表示，因此最大长度为65535。
        // 实际运行最大为 65534，这是 Java 编译器的 bug，在 kotlin 中 65535 可以运行。
        // 总结：Latin字符，受Javac限制，最多65534个；非Latin字符最终对应字节个数差距较大，最多字节数为65535个；若运行时方法区设置较小(比如：嵌入式设备)
        // ，也会受方法区大小限制。
        // 参考：https://juejin.im/post/5e0f6ed16fb9a047f2682e74
        print(maxStr.length());
        // 堆内存中：new String(bytes); 受String内部value[] 数组，此数组受虚拟机指令 newarray [int]，因此数组理论上最大个数为
        // Integer.MAX_VALUE。有一些虚拟机可能保留一下头信息，因此实际上最大个数小于 Integer
        // .MAX_VALUE。即堆中String理论上最长为Integer
        // .MAX_VALUE，实际受虚拟机限制小于Integer.MAX_VALUE，并且若堆内存较小也受堆内存大小限制。
    }

    @Test
    public void testStringRef() {
        String str1 = new String("111");
        String str2 = new String("111");
        print(str1 == str2);
    }

    @Test
    public void testSubString() {
        String str = "111.apk";
        print(str.substring(str.length() - 4, str.length()));
        String str2 = "PREF=gl=AZ&f4=4000000&hl=es";
        print(Arrays.toString(str2.split("=", 2)));
    }

    @Test
    public void testChar() {
        print(Character.SIZE);
        print("中".getBytes(StandardCharsets.UTF_16));
        print("🙂".getBytes(StandardCharsets.UTF_16));
        print("中".length());

        // 字符数 != 字符串长度例子
        print("🙂".length());// 一个 emoji 字符占两个长度
        print("123".length());// Java9若发现整个字符串中只有ascii码字符，则会使用byte来存，不使用char
        // 存储，这样就会节省一半字符，此时字符串长度
        // 也!= 字符数
    }


    @Test
    public void testLinkedList() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        print(list.poll());
    }


    @Test
    public void testAtomic() {
        AtomicInteger integer = new AtomicInteger();
        print(integer.get());// 默认值为 0
        print(integer.compareAndSet(1, 1));// 如果当前值与 expect 值一直，则设置为更新值，并返回 true
        print(integer.get());
        print(integer.compareAndSet(0, 2));
        print(integer.get());
    }

    @Test
    public void testQueue() {
        // 先进先出，元素添加到尾部
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        // peek 获取队列的 head 元素，但不会 remove
        print(queue.peek());
        print(queue.size());
        // 在 LinkedList 的 offer 实现里，调用的就是 add 方法
        //
        print(queue.offer(10));
        print(queue.size());

        // 有界队列
        LinkedBlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(1);
        blockingQueue.add(0);
        // add、put、offer 都可以用来添加元素，但在队列已满的情况下执行的策略不同
        // add 如果队列已满，将直接抛出异常。LinkedBlockingQueue 实现里调用的是 offer 方法，offer 返回 false 则抛出异常
        // put 如果队列已满，将阻塞线程，LinkedBlockingQueue 通过 ReentrantLock 实现
        // offer 如果队列已满，将返回 false，但不会抛出异常
//        print(blockingQueue.add(1));
        print("put before");
        /*try {
            blockingQueue.put(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        print("put after");
        print(blockingQueue.offer(3));
        sleep(1);
    }

    @Test
    public void testNullIndex() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add(null);
        list.add(null);
        print(list.indexOf(null));// 如果元素中有 null，将返回第一个 null 的索引
        print(list.size());
    }

    private static final String PARAM = "[a-zA-Z][a-zA-Z0-9_-]*";
    private static final Pattern PARAM_URL_REGEX = Pattern.compile("\\{(" + PARAM + ")\\}");

    @Test
    public void testRegex() {
        Matcher queryParamMatcher = Pattern.compile("^values-([a-z]*).*").matcher("values-ro");
        print(queryParamMatcher.matches());
        print(queryParamMatcher.group(0));
        print(queryParamMatcher.group(1));
        print(queryParamMatcher.groupCount());
    }

    @Test
    public void testRegex2() {
        String patternStr = "ExoPlayerWithExtractor[ ]?(2.11.8)";
        print("ExoPlayerWithExtractor  2.11.8".matches(patternStr));
        String patternString = "data = '\\x22'";
        String string = "data = '\\x22'";
        Pattern pattern = Pattern.compile(patternString);
        print(pattern.matcher(string).find());
    }

    @Test
    public void testRegex3() {
        String string = "ab1cab2cab3c";
        Pattern pattern = Pattern.compile("ab\\d");
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            print(matcher.group(0));
        }
    }

    @Test
    public void testRegex4() {
        String string = "videos";
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            print(matcher.group());
        }
    }

    @Test
    public void testRegex5() {
        String string = "https://www.facebook.com/discomolvi11";
//        String string = "https://www.facebook.com/discomolvi11/?a=1";
//        String string = "https://www.facebook.com/discomolvi11/?a=1";
        URI uri = URI.create(string);
        String pathAndQuery = uri.getPath();
        if (uri.getQuery() != null) {
            pathAndQuery += "?" + uri.getQuery();
        }
        print("path & query: " + pathAndQuery);
        Pattern pattern = Pattern.compile("/(\\w+){10}/?(\\?.*)?");
        Matcher matcher = pattern.matcher(pathAndQuery);
        print("is match: " + matcher.matches());
        while (matcher.find()) {
            print(matcher.group());
        }
    }

    @Test
    public void testRegex6() {
        String html = FileIOUtils.readFile2String("./regextest.txt");
        Pattern pattern = Pattern.compile("<script type=\"application/json\" data-content-len=\"\\d+\" data-sjs>(\\{.+\\})</script>");
//        Pattern pattern = Pattern.compile("data-sjs>(\\{\"require\":\\[\\[\"ScheduledServerJSWithCSS\",.*RelayPrefetchedStreamCache.*)</script>");
//        Pattern pattern = Pattern.compile("\"dash_manifest\":\"(.*?MPD.*?)\",");
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            Matcher matcher = pattern.matcher(html);
            if (matcher.find()) {
                String data = matcher.group(1);
                print("group count: " + matcher.groupCount() + " & time: " + (System.currentTimeMillis() - start));
            }
        }
    }

    @Test
    public void testSocket() {
        new SocketServerThread().start();
        new SocketClientThread().start();
        sleep(2);
    }

    private static class SocketClientThread extends Thread {
        private Socket client;
        private PrintWriter writer;
        private BufferedReader reader;

        @Override
        public void run() {
            try {
                while (client == null) {
                    client = new Socket("localhost", 9876);
                    writer = new PrintWriter(
                            new BufferedWriter(
                                    new OutputStreamWriter(client.getOutputStream())),
                            true);
                    reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 10; i++) {
                print(i);
                writer.println("socket客户端：" + i);
            }
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    print(line);
                }
            } catch (Exception e) {
                print(e);
            }
        }
    }

    private static class SocketServerThread extends Thread {

        private ServerSocket serverSocket;

        private SocketServerThread() {
            try {
                serverSocket = new ServerSocket(9876);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            // 监听接入的 Socket 连接
            Socket client;
            try {
                client = serverSocket.accept();
                //接收客户端信息
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(client.getInputStream()));
                //向客户端发送信息
                PrintWriter writer = new PrintWriter(
                        new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),
                        true);
                writer.println("欢迎来到聊天室！");
                while (true) {
                    // 不断地响应该 client 的请求
                    String str = reader.readLine();
                    if (str == null) {
                        break;//断开
                    }
                    int i = new Random().nextInt();
                    writer.println(str + ": " + i);
                }
                writer.close();
                reader.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testDeadLock() throws Exception {
        // https://blog.csdn.net/Andy_96/article/details/82812538?utm_source=blogxgwz7
        String lockA = "lockA";
        String lockB = "lockB";
        DeadLockSample t1 = new DeadLockSample("Thread1", lockA, lockB);
        DeadLockSample t2 = new DeadLockSample("Thread2", lockB, lockA);
        t1.start();
        t2.start();
        // 两个线程有一些先获得 first 的锁，比如 t1
        // 需要注意，synchronized 的是对象锁，t1 的 first 是 lockA，t2 的 first 是 lockB
        // 所以在 synchronized (first) 的地方，t1 和 t2 都会获得各自的锁
        // 但继续执行，因为下一个锁被互相已经锁住，没办法获得，所以形成死锁
        t1.join();
        t2.join();
    }

    private class DeadLockSample extends Thread {
        private final String first;
        private final String second;

        private DeadLockSample(String name, String first, String second) {
            super(name);
            this.first = first;
            this.second = second;
        }

        public void run() {
            synchronized (first) {
                print(this.getName() + " obtained: " + first);
                try {
                    Thread.sleep(1000L);
                    synchronized (second) {
                        print(this.getName() + " obtained: " + second);
                    }
                } catch (InterruptedException e) {
                    // Do nothing
                }
            }
        }
    }


    private static volatile int race = 0;

    /**
     * 请用 debug 运行，否则会进入死循环。因为 run 运行时，idea 会多启动一个线程。
     */
    @Test
    public void testVolatile() {
        // 局部变量是线程私有的，并不会共享，所以不存在并发问题
        // volatile 保证可见性和禁止指令重排序，但不保证原子性
        Thread[] threads = new Thread[20];
        for (int i = 0; i < 20; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        increaseRace();
                    }
                }
            });
            threads[i].start();
        }
        while (Thread.activeCount() > 1) {
            Thread.yield();
            print(race);
        }
    }

    private boolean isRunning;

    @Test
    public void testVolatile2() {
        // isRunning 不使用 volatile 会死循环
        isRunning = true;
        new Thread(() -> {
            while (isRunning) {

            }
            print("子线程结束");
        }).start();
        sleep(1);
        isRunning = false;
        sleep(3);
        print("主线程结束");
    }

    private static void increaseRace() {
        race++;
    }

    @Test
    public void testArray() {
        int[][] array = {{1, 2, 3}, {4, 5}};
        print(array.length);
        print(array[0].length);
        print(array[1].length);
        print(array[1][1]);

        int[][] array2 = new int[3][2];// 三行两列
        print(array2[2][1]);
    }

    @Test
    public void testReplaceClassLoader() {
        print(this);
        printClassLoaders(this);
    }

    private static void printClassLoaders(Object object) {
        ClassLoader loader = object.getClass().getClassLoader();
        if (loader == null) {
            print("loader is null");
            return;
        }
        do {
            print("object: " + loader);
        } while ((loader = loader.getParent()) != null);
    }

    @Test
    public void testClassLoader2() throws Exception {
        MyClassLoader loader = new MyClassLoader();
//        Thread.currentThread().setContextClassLoader(loader);
        Constructor<?> constructor = loader.loadClass("cn.dozyx.LoadedClass").getConstructor(
                String.class);
        Object customLoaderObj = constructor.newInstance("custom loader");
        LoadedClass loadedClass = new LoadedClass("new instance");
        print("class loader 是否相同：" + (customLoaderObj.getClass().getClassLoader() == loadedClass.getClass().getClassLoader()));
        print("LoadedClass.class.isInstance(loadedClass): " + LoadedClass.class.isInstance(loadedClass));
        print("customLoaderObj.getClass().isInstance(loadedClass): " + customLoaderObj.getClass().isInstance(loadedClass));
        print("BaseLoadedClass.class.isInstance(loadedClass): " + BaseLoadedClass.class.isInstance(loadedClass));
    }

    @Test
    public void testClassLoader() throws Exception {


        MyClassLoader myLoader = new MyClassLoader();
        Object obj = myLoader.loadClass("cn.dozyx.JavaTest").newInstance();
        print(obj.getClass());
        //加载类的类加载器和类本身确立其在 Java 虚拟机中的唯一性
        print(obj instanceof JavaTest);
        print(getClass().getName());
        print(obj.getClass().getName());
        print(getClass().getClassLoader());
        print(obj.getClass().getClassLoader());

        Parent parent1 = new Child();

        Object child1 = myLoader.loadClass("cn.dozyx.Child").newInstance();
        print(getClass().getClassLoader() + " & " + child1.getClass().getClassLoader());
//        Parent parent2 = (Parent) child1;// 不同 classloader 加载的类无法向上转型

        Object child2 = Class.forName("cn.dozyx.Child").newInstance();
        print(getClass().getClassLoader() + " & " + child2.getClass().getClassLoader());
        Parent parent2 = (Parent) child2;
    }

    static {
        String s = new String();
//        System.out.println(s.getClass().getClassLoader());
    }

    public static class MyClassLoader extends ClassLoader {
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
                System.out.println(e.getStackTrace());
                throw new ClassNotFoundException();
            }
        }
    }

    ;


    @Test
    public void testSubstring() {
        print("2.66".substring(0, 2));
    }

    @Test
    public void testStringFormat() {
        print(String.format(Locale.getDefault(), "%.1f", 0.04));
    }

    @Test
    public void testFormatArg() {
        logDebug(getData(0));
        logDebug("logDebug", getData(1));
    }

    private void logDebug(String msg) {
        print("logDebug: " + msg);
    }

    private void logDebug(String message, Object... args) {
        print("logDebug args: " + message);
    }

    private String getData(int flag) {
        print("getData: " + flag);
        return "flag: " + flag;
    }

    @Test
    public void testBidDecimal() {
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
    public void testThreadIntercept() throws InterruptedException {
        // https://mp.weixin.qq.com/s/V981Wrk1RLrZroeYO12geg
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                print("thread start");
                for (int i = 0; i < 500000; i++) {

                }
                print("thread end");
            }
        });
        thread.start();
//        sleep(1);
        thread.interrupt();
        // 已执行完，isInterrupted() 返回 false
        // isInterrupted() 不会影响 interrupted 状态，这点与 Thread.interrupted() 不同。如果线程不处于 alive
        // 状态，中断会被忽略，返回 false。
        print("thread.isInterrupted(): " + thread.isInterrupted());
        print("thread.isInterrupted(): " + thread.isInterrupted());

        Thread.currentThread().interrupt();
        // Thread.interrupted() 与 isInterrupted() 类似，但调用之后清除 interrupted 状态，所以第二次调用返回 false
        print("Thread.interrupted(): " + Thread.interrupted());
        print("Thread.interrupted(): " + Thread.interrupted());
        sleep(1);
    }

    @Test
    public void testThreadIntercept2() {
        Thread thread = new Thread(() -> sleep(1000));
        thread.start();
        thread.interrupt();// sleep 等状态下调用会抛出 InterruptedException
    }

    @Test
    public void testContextClassLoader() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    printClassLoaders(Class.forName("java.lang.String", false,
                            Thread.currentThread().getContextClassLoader()).newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setContextClassLoader(new MyClassLoader());
        thread.start();
        sleep(1);
    }


    @Test
    public void testThread() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            sleep(1);
            print("thread 1" + Thread.currentThread());
        }, "thread");
        thread1.start();

        Thread thread2 = new Thread(() -> {
            sleep(1);
            print("thread 2" + Thread.currentThread());
        }, "thread");
        thread2.start();
        print(thread1 + " & " + thread2 + " " + thread1.equals(thread2));
        sleep(2);
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
        // join() 阻塞，直到该线程 die
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

    class WorkerImplProxy {

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
    public void testThrows() throws Exception {
        try {
            funException();
        } catch (Exception e) {
            print(e);
        }
    }

    private void funException() throws Exception {
        String str = null;
        str.length();
    }

    @Test
    public void testException() {
//        try {
        uncheckedException();
//        }catch (Exception e){

//        }
        try {
            checkedException();
        } catch (IOException e) {

        }
        try {
            new ExceptionClass().foo();
        } catch (Exception e) {

        }
        Exception exception = new Exception();
    }

    @Test
    public void testExceptionPrint() throws Throwable {
        Exception cause = new IllegalStateException("this is illeagal state");
        Exception e = new IllegalStateException("this is illeagal state");
        e = new RuntimeException("this is runtime exception1", e);
        e = new RuntimeException("this is runtime exception2", e);
        e = new RuntimeException("this is runtime exception3", e);
//        e = new RuntimeException(e);
        // 不传 message 的话，将把 cause.toString() 打印出来
        throw e;
//        print(e);
//        print(e.getCause());
    }

    private void throwAfterHandle(Throwable e) throws Throwable {
        throw e;
    }

    private static void uncheckedException() {
        // RuntimeException 不要求抛出的方法或构造函数使用 throws
        // 运行时异常一般是由于代码逻辑问题引起的
        // 也可以 catch，但容易掩盖问题代码
        throw new NullPointerException("我是空的");
    }

    private static void checkedException() throws IOException {
        // 非运行时异常需要  catch 或 throws
        throw new IOException();
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
            print(method.getName() + " getAnnotation for CustomAnnotation: "
                    + method.getAnnotation(
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
            print("method.getDeclaringClass(): " + method.getDeclaringClass());
            // getParameterTypes 返回参数的类型
            print(method.getName() + "getParameterTypes(): " + Arrays.toString(
                    method.getParameterTypes()));
        }
        for (Method method : methods) {
            // getReturnType() 获取返回值类型，如果返回值是一个泛型，则为泛型的边界类型
            // 返回值是一个 Class
            print(method.getName() + " getReturnType: " + method.getReturnType());
        }
        for (Method method : methods) {
            // getGenericReturnType() 返回一个表示返回类型的 Type 对象
            print(method.getName() + " getGenericReturnType: " + method.getGenericReturnType());
        }
        try {
            ReflectClass<Object> object = new ReflectClass<>();
            Field sBoolean = cls.getDeclaredField("sBoolean");
            sBoolean.setAccessible(true);
            print("sBoolean.isAccessible() " + sBoolean.isAccessible());
            sBoolean.setBoolean(object, false);
            print("sBoolean.isAccessible() " + sBoolean.isAccessible());
            print("sBoolean.isAccessible() " + sBoolean.getBoolean(object));

            Field finalStringFiled = cls.getDeclaredField("finalString");
            finalStringFiled.setAccessible(true);
            print("finalString: " + finalStringFiled.get(object));
            finalStringFiled.set(object, "222");
            print("finalString: " + finalStringFiled.get(object));

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static class ReflectClass<T> {
        private T data;
        private static boolean sBoolean = true;
        private final String finalString = "111";

        private <K extends CharSequence> K foo1(K type) {
            return type;
        }

        private <K> String foo2(K type) {
            return "string";
        }

        private String foo3(String text) {
            return text;
        }

    }

    @Test
    public void testFoo() {
        int i = (int) (0.5 + 0.51);
        print("result" + i);
    }

    @Test
    public void testGeneric2() {
        new GenericSuperClass<Integer, Long>();
        new GenericClass<Integer>();
        new GenericClass<Integer>() {
        };
    }

    private static class GenericSuperClass<T, R> {
        private GenericSuperClass() {
//            print("super getClass " + getClass());
        }
    }

    private static class GenericClass<T> extends GenericSuperClass<String, T> {
        private GenericClass() {
            super();
            // getGenericSuperclass 返回直接继承的父类（包含泛型参数）。注意：所有 Class 都继承了 Object
            print(getClass().getGenericSuperclass());
            print(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
        }
    }

    @Test
    public void testGeneric3() {
        // 类型擦除与多态的冲突和解决方法 https://www.cnblogs.com/wuqinglong/p/9456193
        // .html#%E7%B1%BB%E5%9E%8B%E6%93%A6%E9%99%A4%E4%B8%8E%E5%A4%9A%E6%80%81%E7%9A%84%E5%86
        // %B2%E7%AA%81%E5%92%8C%E8%A7%A3%E5%86%B3%E6%96%B9%E6%B3%95
        DateInter dateInter = new DateInter();
        dateInter.setValue(new Date());
//        dateInter.setValue(new Object()); //编译错误
    }

    public List<Integer> list = new ArrayList<>();

    @Test
    public void testReadGeneric() throws NoSuchFieldException {
        List<String> list = new ArrayList<>();
        Type[] genericInterfaces = list.getClass().getGenericInterfaces();
        print(Arrays.toString(genericInterfaces));
        Type firstInterface = genericInterfaces[0];
        print(firstInterface instanceof ParameterizedType);
        print((Arrays.toString(((ParameterizedType) firstInterface).getActualTypeArguments())));

        Type genericSuperclass = list.getClass().getGenericSuperclass();
        print(genericSuperclass);

        Field field = JavaTest.class.getField("list");
        print(Arrays.toString(
                ((ParameterizedType) field.getGenericType()).getActualTypeArguments()));
    }

    class Pair<T> {

        private T value;

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

    class DateInter extends Pair<Date> {

        @Override
        public void setValue(Date value) {
            super.setValue(value);
        }

        @Override
        public Date getValue() {
            return super.getValue();
        }
    }

    @Test
    public void testGenericWildcard() {
        List<A> listA = new ArrayList<>();
        listA.add(new A());
        listA.add(new B());
        // listA 可以添加 A 及其子类

        List<B> listB = new ArrayList<>();
//        listA = listB;// 因为 listA 可以插入 C 实例，这样就会通过 listB 来进行插入，这是不允许的。即导致插入错误
//        listB = listA;// 假如可以的话，会导致 listB 中出现非 B 的实例，因为 listA 可以插入 A 的所有子类。即导致读取错误。
        // 无法相互赋值


    }

    private class A {
    }

    private class B extends A {
    }

    private class C extends A {
    }

    @Test
    public void testGeneric() {
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<Integer> integers = new ArrayList<>();
        System.out.println("ccc"+(strings.getClass() == integers.getClass()));
        print(Arrays.toString(strings.getClass().getTypeParameters()));

        // 上界通配符
        // List<? extends Parent> 声明一个 List，它的实例的具体类型可以为 Parent 类型或 Parent 的子类类型
        List<? extends Parent> list1 = new ArrayList<>();
        // 无法 add，因为并不知道 list1 的具体类型是哪个
        // 比如，假设具体类型是 Child，那么就只能 add Child 而不能 add Child 的父类；
        // 假设具体类型是 Parent，那么就只能 add Parent 和其子类
        // 疑问，具体类型为 Child 或者 Parent，都能 add Child 才对？不对，假如 Parent 有另一个子类 Child2，那么 add Child
        // 也不行。所以，能确定的只有读出来的是 Parent
//        list1.add(new People());
//        list1.add(new Parent());
//        list1.add(new Child());
        Parent parent = list1.get(0);
        // 使用场景
        list1(new ArrayList<>());
//        list1(new ArrayList<People>());
        list1(new ArrayList<Parent>());
        list1(new ArrayList<Child>());
        list1(new ArrayList<Child2>());

        // 下界通配符，接收指定类本身或其父类
        // List<? super Parent> 声明一个 List，它对应的实例的类型可以为 Parent 类型或 Parent 的超类
        List<? super Parent> list2 = new ArrayList<>();
        // 因为多态，所以可以 add Parent 及其子类
//        list2.add(new People());
        list2.add(new Parent());
        list2.add(new Child());
        Object object = list2.get(0);
        list2(new ArrayList<>());
        list2(new ArrayList<People>());
        list2(new ArrayList<Parent>());
//        list2(new ArrayList<Child>());

        List<Parent> list3 = new ArrayList<>();
//        list3.add(new People());
        list3.add(new Parent());
        list3.add(new Child());

        List<Child> list4 = new ArrayList<>();
//        list3.add(new People());
//        list4.add(new Parent());
        list4.add(new Child());

        list3(list3);
        list4(list3);
//        list5(list3);
    }


    private void list1(List<? extends Parent> list) {
        // 具体类型可能是 Parent 或 Parent 子类
        list = new ArrayList<>();
//        list.add(new Parent());
//        list.add(new Child());
        Parent parent = list.get(0);
        // 无法 add Parent，因为不能向下转型
        // 无法 add Child，因为 child 也不能相互转

    }

    private void list2(List<? super Parent> list) {
        // 具体类型可能是 Parent 或 Parent 父类
        list.add(new Parent());
        list.add(new Child());// Child 可以向上转型为 Parent
//        list.add(new People());
    }

    private void list3(List<?> list) {

    }

    private void list4(List list) {

    }

    private void list5(List<Object> list) {

    }

    @Test
    public void testThreadStack() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : trace) {
            print(stackTraceElement.toString());
        }
    }


    @Test
    public void testShiftOperator() {
        // >> 算术右移，整体右移，包括符号位，原符号位正数补0，负数补1。右移一位相当于除以2
        // >>> 逻辑右移（无符号右移），整体右移，包括符号位，高位补 0
        // << 算术左移，低位补0，可能导致负数变为正数，或相反，但这些情况意味着发生了溢出。左移一位相当于乘以2
        // 没有逻辑左移，因为它的结果与算术左移一致。 https://www.quora
        // .com/Why-is-there-no-unsigned-left-shift-operator-in-Java
        System.out.println(Integer.toBinaryString(2));
        System.out.println(Integer.toBinaryString(-2));
        System.out.println(Integer.toBinaryString(2 >> 1));
        System.out.println(Integer.toBinaryString(-2 >> 1));
        System.out.println(Integer.toBinaryString(2 >>> 1));
        System.out.println(Integer.toBinaryString(-2 >>> 1));
        System.out.println(Integer.toBinaryString(2 << 1));
        System.out.println(Integer.toBinaryString(-2 << 1));
        System.out.println(Integer.toBinaryString(0x7fffffff << 1));
//        System.out.println(Integer.toHexString(0x8f000000 << 1));
        System.out.println(Integer.MIN_VALUE);
    }

    @Test
    public void testType() {

        print(Boolean.TYPE);
        print(Integer.TYPE == Integer.class);
        print(Integer.class instanceof Class);
        print(new TypeTest<String>().name);
    }

    private static class TypeTest<T> {
        private T name;
    }

    @Test
    public void testDateCompare() throws ParseException {
        String date = "2020-06-24";
        print(System.currentTimeMillis() > new SimpleDateFormat("yyyy-MM-dd").parse(
                date).getTime());
    }

    @Test
    public void testDate2() throws ParseException {
        print(new Date(0));
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
        print((float) 1/2);
        print((float) (1/2));
        print((1/(float) 2));
    }

    private static class SuperClass {
        protected void foo() {
            print(this);
        }
    }

    private static class SubClass extends SuperClass {

    }

    public static String parse(String fen) {
        return new DecimalFormat("##0.00").format(Double.valueOf(fen) / 100.0);
    }

    @Test
    public void testThreadPool() throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3));
        // corePoolSize 核心线程，一直存活，除非设置了 allowCoreThreadTimeOut 为 true
        // maximumPoolSize 池中允许的最大线程，包括核心线程。maximumPoolSize 不能小于 corePoolSize
        // 最多提交任务数：maximumPoolSize + deque 大小
        for (int i = 0; i < 13; i++) {
            int flag = i;
            // 先执行了 core 任务，后续任务放入 deque，满deque 后执行新加任务，有空闲线程后再执行 deque 任务
            // 为什么要队列满了才启动非核心线程？设计如此？避免启动过多线程？如果要实现立即执行，需要修改队列？
            executorService.execute(() -> {
                print(" & i == " + flag + " & thread == " + Thread.currentThread());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            Thread.sleep(20);

        }
        sleep(5);
    }

    @Test
    public void testThreadPool2() throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(1, 10,
                10, TimeUnit.SECONDS,
                new SynchronousQueue<>());
        for (int i = 0; i < 10; i++) {
            int flag = i;
            int finalI = i;
            executorService.execute(() -> {
                print(" & i == " + flag + " & thread == " + Thread.currentThread());
                try {
                    if (finalI == 0) {
                        Thread.sleep(10000);
                    } else {
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                print(" end & i == " + flag + " & thread == " + Thread.currentThread());
            });
            Thread.sleep(20);
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
    public void testGsonTypeToken() {
        String json = "{\"code\":0,\"data\":\"haha\"}";
        // TypeToken<Response<String>>(){} 相当于一个空的匿名内部类
        Response<String> response = new Gson().fromJson(json,
                new TypeToken<Response<String>>() {
                }.getType());
        print(response.data);
    }

    private static class Response<T> {
        private int code;
        private T data;

        @Override
        public String toString() {
            return "Response{" +
                    "code=" + code +
                    ", data=" + data.toString() +
                    '}';
        }
    }


    @Test
    public void testGson1() {
        IntArrayData intArrayData = new IntArrayData();
        intArrayData.data = new int[]{1, 2, 3};
        print(new GsonBuilder().setPrettyPrinting().setLenient().create().toJson(intArrayData));
        print(new Gson().toJson(intArrayData));
        print(new Gson().fromJson("", Student1.class));
    }

    private static class IntArrayData {
        private int[] data;
    }

    @Test
    public void testEmptyGson() {
        long start = System.currentTimeMillis();
        String json = null;
        print(new Gson().fromJson(json, Person.class));
        print(System.currentTimeMillis() - start);
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
                "jsonStr 缺少字段：" + new Gson().fromJson(jsonStr2,
                        cn.dozyx.zerofate.java.User.class));

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
        System.out.println(
                bigDecimal1 + "\n" + bigDecimal2 + "\n" + bigDecimal1.add(bigDecimal2));
        System.out.println((int) ((0.58f + 0.01f) * 100));

        // double 计算
        BigDecimal bigDecimal3 = new BigDecimal(0.58);
        BigDecimal bigDecimal4 = new BigDecimal(0.01);
        System.out.println(
                bigDecimal3 + "\n" + bigDecimal4 + "\n" + bigDecimal3.add(bigDecimal4));
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
        ClassB classB = new ClassB();
        try {
            classB.foo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static class ClassA {
        public String name;
        public int age;

        public void foo() throws Exception {

        }

        protected void foo1() {

        }

        @Override
        public String toString() {
            return "ClassA{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ClassA classA = (ClassA) o;
            return age == classA.age && Objects.equals(name, classA.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
    }

    static class ClassB extends ClassA implements Cloneable {
        @Override
        public void foo() throws Exception {
            super.foo();
        }

        @Override
        public void foo1() {
            super.foo1();
        }

        @NonNull
        @Override
        public ClassB clone() throws CloneNotSupportedException {
            return (ClassB) super.clone();
        }
    }
}
