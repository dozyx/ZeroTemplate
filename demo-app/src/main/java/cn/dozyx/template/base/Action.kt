package cn.dozyx.template.base

/**
 * @author dozeboy
 * @date 2019-06-29
 */
abstract class Action : Runnable {
    val name: String

    constructor(name: String) {
        this.name = name
    }
}