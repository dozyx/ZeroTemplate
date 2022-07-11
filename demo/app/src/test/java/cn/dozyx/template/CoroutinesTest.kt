package cn.dozyx.template

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

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
}