package com.zerofate.template

/**
 * @author dozeboy
 * @date 2018/7/8
 */

fun main(args: Array<String>) {
    var a = "111"
    var ins = object : A() {
        override fun foo() {
            a + "222"
        }
    }
    a + "333"

    var b :String? = null
}

open class A() {
    init {
        println("init")
    }

    open fun foo(){}

    companion object {
        fun foo() {
            println("foo")
        }
    }
}