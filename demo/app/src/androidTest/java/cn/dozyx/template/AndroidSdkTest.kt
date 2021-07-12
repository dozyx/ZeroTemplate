package cn.dozyx.template

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber

@RunWith(AndroidJUnit4::class)
class AndroidSdkTest {

    @Test
    fun testGetString() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // getText 拿到的是原始的定义在 strings.xml 里的字符串
        val formatText = context.resources.getText(R.string.format_test)
        Timber.d("AndroidSdkTest.testGetString $formatText")

        // getString 没有传入参数的话，调用的就是 getText 方法，返回原始的字符串
        val formatString1 = context.resources.getString(R.string.format_test)
        Timber.d("AndroidSdkTest.testGetString $formatString1")

        val formatString2 = context.resources.getString(R.string.format_test, 1)
        Timber.d("AndroidSdkTest.testGetString $formatString2")

    }
}