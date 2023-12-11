package cn.dozyx.template

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.concurrent.thread

class CoroutinesTest {
    @Test
    fun testGlobalScope() {
        CoroutineScope(Dispatchers.IO).launch {
            println("launch Dispatchers.IO ${Thread.currentThread().name}")
        }
        CoroutineScope(Dispatchers.Default).launch {
            println("launch Dispatchers.Default ${Thread.currentThread().name}")
        }
        Thread.sleep(200)
    }

    // 代码中一共启动了两个协程
    @Test
    fun test1() = runBlocking {
        println(Thread.currentThread().name)

        launch {
            println(Thread.currentThread().name)
            delay(100L)
        }
        Thread.sleep(1000L)
    }
}