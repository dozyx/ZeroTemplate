package com.zerofate.template

/**
 * @author dozeboy
 * @date 2018/7/8
 */
val kotlinLogo = """| //
     .|//
                   .|/ \"""

fun main(args: Array<String>) {
    val user: User = User(0, "搜索", "少时诵诗书")
    user.validateBeforeSave()
}