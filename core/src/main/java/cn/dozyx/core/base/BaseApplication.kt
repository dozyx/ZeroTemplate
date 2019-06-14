package cn.dozyx.core.base

import android.app.Application

/**
 * @author dozeboy
 * @date 2019/1/6
 */

open class BaseApplication : Application() {


    companion object {
        var INSTANCE = this
    }
}