package cn.dozyx.zerofate.java


/**
 * @author dozeboy
 * @date 2018/7/8
 */

fun <T> printHashCode(t: T) {
    println(t?.hashCode())
    SingleInstance.foo()
}

fun main(args: Array<String>) {
    printHashCode(null)
    Person.foo(listOf("1", "2"))
}

interface Processor<T> {
    fun process(): T
}

fun foo2() {
    val nothing:Nothing? = null
    nothing.hashCode()
}

class NoResultProcessor : Processor<Unit> {
    override fun process() {
        //...
        var a = "111"
        var ins = object : A() {
            override fun foo() {
                a + "222"
            }
        }
        a + "333"

        var b: String? = null
    }
}

open class A() {
    init {
        println("init")
    }

    open fun foo() {}

    companion object {
        fun foo() {
            println("foo")
        }
    }
}