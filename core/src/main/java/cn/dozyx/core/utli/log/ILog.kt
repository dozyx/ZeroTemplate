package cn.dozyx.core.utli.log

import org.jetbrains.annotations.NonNls

/**
 * @author timon
 * @date 2018/11/23
 */

interface ILog {
    fun v(@NonNls message: String, vararg args: Any)

    fun v(t: Throwable, @NonNls message: String, vararg args: Any)

    fun v(t: Throwable)

    fun d(@NonNls message: String, vararg args: Any)

    fun d(t: Throwable, @NonNls message: String, vararg args: Any)

    fun d(t: Throwable)

    fun d(any: Any?)

    fun i(@NonNls message: String, vararg args: Any)

    fun i(t: Throwable, @NonNls message: String, vararg args: Any)

    fun i(t: Throwable)

    fun w(@NonNls message: String, vararg args: Any)

    fun w(t: Throwable, @NonNls message: String, vararg args: Any)

    fun w(t: Throwable)

    fun e(@NonNls message: String, vararg args: Any)

    fun e(t: Throwable, @NonNls message: String, vararg args: Any)

    fun e(t: Throwable)

    fun wtf(@NonNls message: String, vararg args: Any)

    fun wtf(t: Throwable, @NonNls message: String, vararg args: Any)

    fun wtf(t: Throwable)

    fun json(json: String)

    fun xml(xml: String)
}