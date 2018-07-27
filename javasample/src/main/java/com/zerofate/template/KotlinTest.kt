package com.zerofate.template

/**
 * @author dozeboy
 * @date 2018/7/8
 */

fun main(args: Array<String>) {
    A.foo()
}

class A{
    init {
        println("init")
    }
    companion object {
        fun foo(){
            println("foo")
        }
    }
}