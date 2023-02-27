package cn.dozyx

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

class CoroutinesTest {
    @Test
    fun testGlobalScope() {
        GlobalScope.launch {
//            println("launch ${Thread.currentThread().name}")
//            println("launch")
        }
        Thread.sleep(200)
    }
}