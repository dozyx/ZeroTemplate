package cn.dozyx.template;

import android.view.GestureDetector;
import android.view.View;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 用于编写 Java 代码，再粘贴到 kt 文件实现自动转换，以方便查看 kotlin 语法
 **/
public class JavaToKotlin {

  public static void foo(){

  }
    private static final JavaToKotlin INSTANCE = new JavaToKotlin();
    public <T extends View> T getView(int id){
        return null;
    }

    public <T extends View> T getView(Class<T> clz){
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getLogInstance(Class<T> clz){
        return (T) Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{clz},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)
                            throws Throwable {
                        int i = 0;
                        return new Object();
                    }
                });
    }

    public static void invoke(){
        getLogInstance(GestureDetector.OnGestureListener.class);
    }

}
