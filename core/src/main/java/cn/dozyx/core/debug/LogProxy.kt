package cn.dozyx.core.debug

import timber.log.Timber
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.*

/**
 * 简单地对所有接口方法做下打印
 */
@Suppress("UNCHECKED_CAST")
fun <T> getLogProxy(clz: Class<T>): T {
    return Proxy.newProxyInstance(clz.classLoader, arrayOf<Class<*>>(clz),
            object : InvocationHandler {
                override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any? {
                    Timber.d("LogProxyHandler.invoke method : ${method.name}")
                    val returnType = method.returnType
                    if (returnType == Integer.TYPE) {
                        return 0
                    } else if (returnType == java.lang.Boolean.TYPE) {
                        return false
                    }
                    return null
                }
            }) as T
}
