package com.zerofate.template


/**
 * @author dozeboy
 * @date 2018/7/8
 */

fun <T> printHashCode(t: T) {
    println(t?.hashCode())
}

fun main(args: Array<String>) {
    printHashCode(null)
    Person.foo(listOf("1", "2"))
}

interface Processor<T> {
    fun process(): T
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