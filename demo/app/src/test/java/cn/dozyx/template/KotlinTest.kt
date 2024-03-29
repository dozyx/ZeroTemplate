package cn.dozyx.template

import io.reactivex.Observable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Test
import javax.inject.Inject
import kotlin.concurrent.thread
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @author dozyx
 * @date 2019-12-09
 */
class KotlinTest {

    @Test
    fun testSplit() {
        val str = "http://www.baidu.com?sqp=222"
//        val str = "http://www.baidu.com"
        val split = str.split("?")
//        print(split.firstOrNull())
        print(str.substringBefore("?"))
    }

    @set:Inject
    internal var injectField: String?=null

    val token:String by Saver()


    class Saver : ReadOnlyProperty<KotlinTest, String> {
        override fun getValue(thisRef: KotlinTest, property: KProperty<*>): String {
            return ""
        }

    }

    @Test
    fun testNullCast() {
        val nullString:String = null as String
    }

    @Test
    fun invokeReified() {
        var input:Any = 0
        input = "1"
        val result = testReified(input)
    }

    private inline fun <reified T> testReified(input: T): Boolean {
        return input is String
    }

    /**
     * 参数是一个函数类型
     */
    fun measureTime(action: () -> Unit) {
        print(">>>>  ")
        action.invoke()
        print("<<<<  ")
    }

    fun main() {
        measureTime { foo() }
    }

    @Test
    fun testOperator() {

    }

    val lazyString: String? by lazy { "Hello" }
    val lazyString2: String? by lazy { getString() }

    private fun getString() = "Hello"

    @Test
    fun testVarArg() {
        foo1(null)
    }

    private fun foo1(vararg texts: String?) {
        print("foo vararg $texts")
    }

    @Test
    fun testNothing() {
        returnNothing()
        doNothing(null)
    }

    private fun doNothing(nothing: Nothing?) {

    }

    private fun returnNothing(): Nothing {
//        return null // 会报错，因为返回值不是 Nothing?
        throw NullPointerException()
    }

    @Test
    fun testToByteArray() {
        val str = "文件名"
        print(str.lastIndexOf(str))
        print(str.substring(0, str.lastIndexOf(str)))
        print(str.toByteArray().size)

    }

    @Test
    fun testBit() {
        val number = 1
        val status = 0
        print(status and 1 and number.inv())

    }

    @Test
    fun testNullCheck() {
        var list: ArrayList<Int>? = null
        list = ArrayList()
        list.add(1)
//        print(list?.isNotEmpty() != true)
        print(list.isNullOrEmpty())
    }

    @Test
    fun testInfix() {
        val ab = A() to B()
        ab.first
        ab.second
    }

    infix fun A.to(b: B): Pair<A, B> = Pair(this, b)

    class A
    class B

    @Test
    fun testBy() {

    }

    @Test
    fun testForEach() {
        arrayListOf<Int>(1, 2, 3).forEach { i: Int ->
            if (i == 2) {
                return@forEach
            }
            print(i)
        }
    }

    /**
     * https://kotlinlang.org/docs/reference/generics.html
     */
    @Test
    fun testGenerics() {
        val box: Box<Int> = Box<Int>(1)
        // 简写
        val box2 = Box(1)

        // kotlin 的泛型没有通配符类型（wildcard types），取而代之的是声明类型和类型预测（declaration-site variance and type projections）

    }

    private fun functionV2(i: Int) {

    }

    @Deprecated("", ReplaceWith("functionV2(i)"))
    private fun functionV1() {

    }

    class Box<T>(t: T) {
        var value = t
    }

    @Test
    fun foo1() {
        Observable.just(1).subscribe({
            return@subscribe
        }, {

        })
    }
/*

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
*/

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

    @Test
    fun foo() {
        ClassB().foo()
    }

    internal open class ClassA {
        @Throws(Exception::class)
        open fun foo() {
        }
    }

    internal class ClassB : ClassA() {
        override fun foo() {
            super.foo()
        }
    }
}
