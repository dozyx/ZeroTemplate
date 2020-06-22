package cn.dozyx.template

import io.reactivex.Observable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import org.junit.Test
import kotlin.concurrent.thread

/**
 * @author dozyx
 * @date 2019-12-09
 */
class KotlinTest {

    @Test
    fun foo1() {
        Observable.just(1).subscribe({
            return@subscribe
        }, {

        })
    }

    @Test
    fun foo2(): String? {
        val meta1: String? = null
        val meta2: String? = null

//        meta1 ?: return meta2
//        meta2 ?: return meta1

        return meta1?.run {
            return meta2?.run {
                // do something
                return ""
            }
        } ?: meta2

        //do something
//        return ""
    }

    @Test
    fun testNull() {
        val str: String? = null
        str?.apply {
            print(this)
        } ?: print("null")
    }

    @Test
    fun testCoroutine1() {
        GlobalScope.launch {
            //启动一个协程
            // 生命周期为整个应用程序的生命周期
//            printThread()
            delay(1000L)// delay 并不阻塞线程，它只是使线程在经过一段时间延迟后恢复
            println("World!")
        }
        thread {
            // 启动的是一个线程
//            printThread()
//            delay(1000L) // 不能使用 delay，delay是特殊的挂起函数
            Thread.sleep(1000L)
            println("World!")
        }
        printThread()
        println("Hello,")
        Thread.sleep(2000L)
    }

    fun printThread() {
        println(Thread.currentThread())
    }
}