package cn.dozyx.core.utli.log

import timber.log.Timber

/**
 * @author dozeboy
 * @date 2018/7/26
 */

class RemoteLogTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {

    }
}