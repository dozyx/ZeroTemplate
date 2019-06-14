package com.zerofate.template.justfortest

/**
 * @author dozeboy
 * @date 2018/7/30
 */
fun main(args: Array<String>) {
    val numbers = mapOf(0 to "zero", 1 to "one")
    println(numbers.mapValues { it.value.toUpperCase() })
}