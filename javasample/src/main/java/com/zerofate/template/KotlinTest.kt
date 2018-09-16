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
    Person.foo(listOf("1","2"))
}

interface Processor<T> {
    fun process(): T
}

class NoResultProcessor : Processor<Unit> {
    override fun process() {
        //...
    }
}